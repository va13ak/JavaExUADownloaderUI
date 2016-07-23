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
package ua.com.codefire.downloader.net;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodeFireUA <edu@codefire.com.ua>
 */
public class Downloader implements Runnable {

    private final File storeFolder;
    private ExecutorService threadPool;
    private List<DownloaderTask> downloaderTasks;
    private List<DownloaderTask> tasksToDownload;
    private List<DownloaderListener> listeners;
    private boolean stopRetrievingFiles;
    private boolean retrievingFiles;

    public Downloader(File store) {
//        String subFolderName = Paths.get(fileList.getFile().toString()).getFileName().toString().replace(".m3u", "").toString();
        String subFolderName = "";
        if (!subFolderName.isEmpty()) {
            File subFolder = new File(store, subFolderName);
            if (!subFolder.exists()) {
                subFolder.mkdir();
            }
            this.storeFolder = subFolder;
        } else {
            this.storeFolder = store;
        }

        this.downloaderTasks = new ArrayList<>();
        this.tasksToDownload = new ArrayList<>();
        
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
        threadPool = Executors.newFixedThreadPool(5);
        for (DownloaderTask downloaderTask : tasksToDownload) {
            threadPool.execute(downloaderTask);
        }
        threadPool.shutdown();
        
        while (!threadPool.isTerminated());
        
        for (DownloaderListener listener : listeners) {
            listener.downloadCompleteCurrentTasks();
        }
    }

    public List<DownloaderTask> retrieveFiles(URL filesListUrl) {
        stopRetrievingFiles = false;
        retrievingFiles = true;
        
        try (Scanner scanner = new Scanner(filesListUrl.openStream())) {
            while (scanner.hasNextLine()) {
                if (stopRetrievingFiles) {
                    retrievingFiles = false;
                    return downloaderTasks;
                }
                
                DownloaderTask downloaderTask = new DownloaderTask(Downloader.this, storeFolder, new URL(scanner.nextLine()));
                downloaderTask.prepare();

                downloaderTasks.add(downloaderTask);
            }
            
            for (DownloaderListener listener : listeners) {
                listener.downloadAllFilesPrepared();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        retrievingFiles = false;
        return downloaderTasks;
    }

    public void startRetrievingFiles(URL filesListUrl) {
        if (retrievingFiles) {
            return;
        }
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                retrieveFiles(filesListUrl);
            }
        }).start();
    }
    
    public void stopRetrievingFiles() {
        if (retrievingFiles) {
            this.stopRetrievingFiles = true;
        }
    }

    public boolean isRetrievingFiles() {
        return retrievingFiles;
    }

    public void stopDownloading() {
        if (threadPool == null) {
        } else {
            //System.out.println("trying to stop");
            threadPool.shutdownNow();
            //System.out.println("after trying to stop");
        }
    }
    
    public boolean isDownloading() {
        if (threadPool == null) {
            return false;
        } else {
            return !threadPool.isTerminated();
        }
    }
    
    public void stopDownloadingFiles() {
        
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
    
    public void downloadPrepared(DownloaderTask task) {
        for (DownloaderListener listener : listeners) {
            listener.downloadPrepared(task);
        }
    }

    public void download(List<DownloaderTask> tasksToDownload) {
        this.tasksToDownload = tasksToDownload;

        new Thread(this).start();
    }

}
