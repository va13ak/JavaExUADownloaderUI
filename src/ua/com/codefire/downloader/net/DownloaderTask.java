/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.downloader.net;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ua.com.codefire.downloader.Main;

/**
 *
 * @author Valery
 */
public class DownloaderTask implements Runnable {

    private final Downloader downloader;
    private final File storeFolder;
    private URL sourceUrl;
    private String sourceAddress;
    private URLConnection urlConnection;
    private File targetFile;
    private int bufferSize = 524288;
    private long downloaded;
    private long total;
    private long streamReadingTime = 0;
    private long currentDataChunkReadingTime = 0;
    private long totalDownloadingTime = 0;
    private int bytesRead;
    private int totalBytesRead = 0;
    private byte[] md5Bytes;
    private String md5String;

    public enum States {
        NEW, READY, PROGRESS, FINISHED
    };
    private States state;

    public DownloaderTask(Downloader downloader, File store, URL source) {
        this.downloader = downloader;
        this.storeFolder = store;
        this.sourceUrl = source;
        this.state = States.NEW;
    }

    public States getState() {
        return state;
    }

    public long getTotal() {
        return total;
    }

    public File getTarget() {
        return targetFile;
    }

    public URL getSource() {
        return sourceUrl;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public long getDownloaded() {
        return downloaded;
    }

    public long getSpeed() {
        return totalBytesRead / streamReadingTime;
    }

    public long getVelocity() {
        return bytesRead / currentDataChunkReadingTime;
    }

    public int getBytesRead() {
        return bytesRead;
    }

    private void buildMD5String() {
        StringBuilder sb = new StringBuilder();

        if (md5Bytes != null) {
            //convert the byte to hex format
            for (int i = 0; i < md5Bytes.length; i++) {
                sb.append(Integer.toString((md5Bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } else {
            sb.append("unknown");
        }

        md5String = sb.toString();
    }

    public String getMD5() {
        return md5String;
    }

    @Override
    public String toString() {
        switch (state) {
            case PROGRESS:
                return String.format("%s   [%5.2f%%]   %6d kB/s", targetFile.getName(), (double) downloaded * 100 / total, getSpeed());
            case FINISHED:
                return String.format("%s   [%d bytes]   %s", targetFile.getName(), total, md5String);
            default:
                return String.format("%s   [%d bytes]", targetFile.getName(), total);
        }
    }

    public boolean prepare() {
        if (state != States.NEW) {
            return true;
        }

        try {
            urlConnection = sourceUrl.openConnection();
            urlConnection.getContentType();

            sourceUrl = urlConnection.getURL();

            total = urlConnection.getContentLengthLong();

            sourceAddress = URLDecoder.decode(new String(sourceUrl.toString().getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
            String sourceFileName = URLDecoder.decode(new String(sourceUrl.getFile().getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");

            targetFile = new File(storeFolder, new File(sourceFileName).getName());

            state = States.READY;
            downloader.downloadPrepared(this);

            return true;

        } catch (IOException ex) {
            Logger.getLogger(DownloaderTask.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public void run() {
        if (state != States.READY) {
            return;
        }

        try {
            state = States.PROGRESS;
            downloader.downloadBegin(this);

            MessageDigest messageDigest5;
            try {
                messageDigest5 = MessageDigest.getInstance("md5");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
                messageDigest5 = null;
            }

            try (BufferedInputStream bis = new BufferedInputStream(sourceUrl.openStream());
                    FileOutputStream fos = new FileOutputStream(targetFile)) {

                byte[] buffer = new byte[bufferSize];
                md5Bytes = null;
                md5String = "";

                while (true) {

                    synchronized (this) {
                        long startTimeBlockReading = System.currentTimeMillis();
                        bytesRead = bis.read(buffer);
                        currentDataChunkReadingTime = System.currentTimeMillis() - startTimeBlockReading;
                        streamReadingTime += currentDataChunkReadingTime;
                        totalBytesRead += bytesRead;
                    }

                    if (bytesRead < 0) {
                        break;
                    }

                    fos.write(buffer, 0, bytesRead);
                    fos.flush();

                    if (messageDigest5 != null) {
                        messageDigest5.update(buffer, 0, bytesRead);
                    }

                    downloaded += bytesRead;

                    if (bytesRead > 0) {
                        downloader.downloadProgress(this);
                    }
                }
            }

            if (messageDigest5 != null) {
                md5Bytes = messageDigest5.digest();
                buildMD5String();
            }

            state = States.FINISHED;
            downloader.downloadComplete(this);

        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            state = States.READY;

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            state = States.READY;
        }

    }

}
