/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaexuadownloader;

/**
 *
 * @author Valery
 */
public class DownloadHandler implements DownloaderListener {

    @Override
    public void downloadBegin(DownloaderTask task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void downloadProgress(DownloaderTask task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void downloadComplete(DownloaderTask task) {
        System.out.printf("File downloaded:\n %s\n %s\n\n", task.getSource(), task.getTarget());
    }
}
