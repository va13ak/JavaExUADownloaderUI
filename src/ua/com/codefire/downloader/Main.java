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
package ua.com.codefire.downloader;

import ua.com.codefire.downloader.net.Downloader;
import ua.com.codefire.downloader.net.DownloadHandler;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ua.com.codefire.downloader.net.DownloaderTask;

/**
 * @author CodeFireUA <edu@codefire.com.ua>
 * @author Valery Zakharov aka va13ak
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File storeFolder = new File("store");

        if (!storeFolder.exists()) {
            storeFolder.mkdir();
        }

        String resource;

        if (args.length > 0) {
            resource = args[0];
            System.out.println("Download: " + resource);
        } else {
            resource = "http://www.ex.ua/playlist/2301371.m3u";
        }

        try {
            Downloader downloader = new Downloader(storeFolder);
            downloader.add(new DownloadHandler());
            List<DownloaderTask> tasksToDownload = downloader.retrieveFiles(new URL(resource));
            downloader.download(tasksToDownload.subList(0, Math.min(10, tasksToDownload.size()-1)));

        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
