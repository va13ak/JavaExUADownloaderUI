/*
 * Copyright (C) 2016 CodeFireUA <edu@codefire.com.ua>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package javaexuadownloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodeFireUA <edu@codefire.com.ua>
 */
public class Downloader implements Runnable {

    private final URL fileList;
    private final List<URL> files;
    private final File store;

    private List<DownloaderListener> listeners;

    public Downloader(URL fileList, File store) {
        this.fileList = fileList;

        String subFolderName = Paths.get(fileList.getFile().toString()).getFileName().toString().replace(".m3u", "").toString();
        if (!subFolderName.isEmpty()) {
            File subFolder = new File(store, subFolderName);
            if (!subFolder.exists()) {
                subFolder.mkdir();
            }
            this.store = subFolder;
        } else {
            this.store = store;
        }

        this.files = new ArrayList<>();

        this.listeners = Collections.synchronizedList(new ArrayList<DownloaderListener>());
    }

    public boolean add(DownloaderListener listener) {
        return listeners.add(listener);
    }

    public boolean remove(Object listener) {
        return listeners.remove(listener);
    }

    @Override
    public void run() {
        retrieveFiles();

        int count = 0;
        for (URL fileUrl : files) {
            //downloadUrl(fileUrl);

            DownloaderTask downloaderTask = new DownloaderTask(this, store, fileUrl);
            Thread thread = new Thread(downloaderTask);
            thread.start();

            count++;
            if (count > 5) {
                return;
            }
        }
    }

    private void retrieveFiles() {
        try (Scanner scanner = new Scanner(fileList.openStream())) {
            while (scanner.hasNextLine()) {
                URL fileUrl = new URL(scanner.nextLine());
                files.add(fileUrl);
            }
        } catch (IOException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void downloadUrl(URL sourceUrl) {
//        try {
//            URLConnection conn = sourceUrl.openConnection();
//            conn.getContentType();
//
//            URL targetUrl = conn.getURL();
//
//            long total = conn.getContentLengthLong();
//            long downloaded = 0;
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//
//            String sourcefile = new File(targetUrl.getFile()).getName();
//            String filename = new String(sourcefile.getBytes("ISO-8859-1"), "UTF-8");
//            File targetFile = new File(store, URLDecoder.decode(filename, "UTF-8"));
//
////            System.out.printf("--%s--   %s\n", dateFormat.format(new Date()), sourceUrl);
////            System.out.printf("Length: %d   Saving to: '%s'\n", total, targetFile);
//
//            int progressLength = 60;
//            String progressLine = String.format("%-" + progressLength + "s", "").replace(' ', '=');
//
//            MessageDigest md;
//            try {
//                md = MessageDigest.getInstance("md5");
//            } catch (NoSuchAlgorithmException ex) {
//                Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
//                md = null;
//            }
//
//            try (BufferedInputStream bis = new BufferedInputStream(targetUrl.openStream());
//                    FileOutputStream fos = new FileOutputStream(targetFile)) {
//                byte[] buffer = new byte[bufferSize];
//
//                long startTime = System.currentTimeMillis();
//                long prevTime = startTime;
//                int speed = 0;
//                int totalReadTime = 0;
//                for (int read; (read = bis.read(buffer)) >= 0;) {
//
//                    long readTime = System.currentTimeMillis() - prevTime;
//                    if (readTime > 0) {
//                        speed = (int) (read / readTime);
//                        totalReadTime += readTime;
//                    }
//
//                    fos.write(buffer, 0, read);
//                    fos.flush();
//                    if (md != null) {
//                        md.update(buffer, 0, read);
//                    }
//
//                    downloaded += read;
//
////                    if (read > 0) {
////                        int curLength = (int) ((progressLength * downloaded) / total);
////                        String progress = progressLine.substring(0, curLength) + (curLength < progressLength ? ">" : "");
////                        //System.out.printf("\r[%-" + progressLength + "s] %6.02f%%", progress, (double) downloaded * 100. / total);
////                        System.out.printf("\r%3d%%[%-" + progressLength + "s] %-9d %6dKb/s in %.01fs", downloaded * 100 / total, progress, downloaded, speed, (1. * (System.currentTimeMillis() - startTime) / 1000));
////                    }
////                    if (downloaded == total) {
////                        System.out.printf("\r%3d%%[%-" + progressLength + "s] %-9d %6dKb/s in %.01fs", downloaded * 100 / total, progressLine, downloaded, (int) (downloaded / totalReadTime), (1. * (System.currentTimeMillis() - startTime) / 1000));
////                    }
//                    prevTime = System.currentTimeMillis();
//                }
//
////                System.out.println();
//
//            }
//
//            
//            for (DownloaderListener listener : listeners) {
//                listener.downloadComplete(sourceUrl, targetFile);
//            }
//            
////            StringBuffer sb = new StringBuffer();
////            if (md != null) {
////                byte[] mdbytes = md.digest();
////
////                //convert the byte to hex format
////                for (int i = 0; i < mdbytes.length; i++) {
////                    sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
////                }
////            } else {
////                sb.append("UNKNOWN");
////            }
//
////            System.out.printf("%s - saved [%d/%d] MD5: %s\n",
////                    dateFormat.format(new Date()), downloaded, total, sb.toString());
//            //System.out.println("Downloaded: " + targetFile);
////            System.out.println();
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void downloadBegin(DownloaderTask task) {
        for (DownloaderListener listener : listeners) {
            listener.downloadBegin(task);
        }
    }

    public void downloadProgress(DownloaderTask task) {
        for (DownloaderListener listener : listeners) {
            listener.downloadProgress(task);
        }
    }

    public void downloadComplete(DownloaderTask task) {
        for (DownloaderListener listener : listeners) {
            listener.downloadComplete(task);
        }
    }
}
