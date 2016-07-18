/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaexuadownloader;

import java.io.File;
import java.net.URL;

/**
 *
 * @author Valery
 */
public interface DownloaderListener {

    public void downloadComplete(URL source, File target);
}
