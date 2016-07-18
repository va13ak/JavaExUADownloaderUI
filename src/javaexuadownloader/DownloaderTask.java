/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaexuadownloader;

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

/**
 *
 * @author Valery
 */
public class DownloaderTask implements Runnable {

    private final Downloader downloader;
    private final File store;
    private final URL source;
    private File target;
    private int bufferSize = 524288;
    private long downloaded;
    private long total;

    public DownloaderTask(Downloader downloader, File store, URL source) {
        this.downloader = downloader;
        this.store = store;
        this.source = source;
    }

    public long getTotal() {
        return total;
    }

    public File getTarget() {
        return target;
    }

    public URL getSource() {
        return source;
    }

    public long getDownloaded() {
        return downloaded;
    }

    @Override
    public void run() {
        try {
            URLConnection conn = source.openConnection();
            conn.getContentType();

            URL targetUrl = conn.getURL();

            total = conn.getContentLengthLong();

            String sourcefile = new File(targetUrl.getFile()).getName();
            String filename = new String(sourcefile.getBytes("ISO-8859-1"), "UTF-8");
            target = new File(store, URLDecoder.decode(filename, "UTF-8"));

            downloader.downloadBegin(this);

            MessageDigest md;
            try {
                md = MessageDigest.getInstance("md5");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
                md = null;
            }

            try (BufferedInputStream bis = new BufferedInputStream(targetUrl.openStream());
                    FileOutputStream fos = new FileOutputStream(target)) {

                byte[] buffer = new byte[bufferSize];

                for (int read; (read = bis.read(buffer)) >= 0;) {

                    fos.write(buffer, 0, read);
                    fos.flush();
                    if (md != null) {
                        md.update(buffer, 0, read);
                    }

                    downloaded += read;

                    if (read > 0) {
                        downloader.downloadProgress(this);
                    }
                }
            }

            downloader.downloadComplete(this);

        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
