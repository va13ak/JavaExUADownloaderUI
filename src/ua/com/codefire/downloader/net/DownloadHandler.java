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
public class DownloadHandler implements DownloaderListener {

    @Override
    public void downloadBegin(DownloaderTask task) {
        System.out.printf("Start downloading %s to '%s'\n\n", task.getSourceAddress(), task.getTarget());
    }

    @Override
    public void downloadProgress(DownloaderTask task) {
//        System.out.printf("%3d%% of %s to '%s' downloaded [%d/%d]\n\n", task.getDownloaded() * 100 / task.getTotal(), 
//                        task.getSource(), task.getTarget(), task.getDownloaded(), task.getTotal());
////    System.out.printf("\r%3d%%[%-" + progressLength + "s] %-9d %6dKb/s in %.01fs", downloaded * 100 / total, progress, downloaded, speed, (1. * (System.currentTimeMillis() - startTime) / 1000));
    }

    @Override
    public void downloadComplete(DownloaderTask task) {
        System.out.printf("%s saved to file '%s'\n\n", task.getSourceAddress(), task.getTarget());
    }

    @Override
    public void downloadAllFilesPrepared() {
        //
    }

    @Override
    public void downloadPrepared(DownloaderTask task) {
        System.out.printf("Ready to download %s'\n\n", task.getSourceAddress());
    }

    @Override
    public void downloadCompleteCurrentTasks() {
        System.out.println("ALL DOWNLOADS COMPLETE");
    }
}
