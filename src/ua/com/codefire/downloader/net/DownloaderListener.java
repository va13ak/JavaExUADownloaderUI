/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.downloader.net;

/**
 *
 * @author Valery
 */
public interface DownloaderListener {

    public void downloadBegin(DownloaderTask task);

    public void downloadProgress(DownloaderTask task);

    public void downloadComplete(DownloaderTask task);
    
    public void downloadPrepared(DownloaderTask task);

    public void downloadAllFilesPrepared();
    
    public void downloadCompleteCurrentTasks();

}
