/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.downloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import ua.com.codefire.downloader.net.Downloader;
import ua.com.codefire.downloader.net.DownloaderListener;
import ua.com.codefire.downloader.net.DownloaderTask;

/**
 *
 * @author Valery
 */
public class MainFrame extends javax.swing.JFrame implements DownloaderListener {

    private final DefaultListModel<DownloaderTask> dlmDownloads;
    private final DefaultListModel<DownloaderTask> dlmDownloaded;
    private final Downloader downloader;
    private final File storeFolder;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();

        jlDownloads.setModel(dlmDownloads = new DefaultListModel<>());
        jlDownloaded.setModel(dlmDownloaded = new DefaultListModel<>());

        storeFolder = new File("./store/");

        if (!storeFolder.exists()) {
            storeFolder.mkdir();
        }

        downloader = new Downloader(storeFolder);
        downloader.add(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlUrl = new javax.swing.JLabel();
        jtfUrlAddress = new javax.swing.JTextField();
        jbFetch = new javax.swing.JButton();
        jpbDownload = new javax.swing.JProgressBar();
        jbDownload = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlDownloads = new javax.swing.JList<>();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlDownloaded = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jlUrl.setText("URL:");

        jtfUrlAddress.setText("http://www.ex.ua/playlist/2301371.m3u");

        jbFetch.setText("FETCH");
        jbFetch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFetchActionPerformed(evt);
            }
        });

        jbDownload.setText("DOWNLOAD");
        jbDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDownloadActionPerformed(evt);
            }
        });

        jSplitPane2.setDividerSize(5);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jInternalFrame1.setTitle("remote files");
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(494, 150));
        jInternalFrame1.setVisible(true);

        jScrollPane2.setViewportView(jlDownloads);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(jInternalFrame1);

        jInternalFrame2.setTitle("downloaded files");
        jInternalFrame2.setPreferredSize(new java.awt.Dimension(494, 150));
        jInternalFrame2.setVisible(true);

        jScrollPane3.setViewportView(jlDownloaded);

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(jInternalFrame2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlUrl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfUrlAddress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbFetch))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpbDownload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbDownload)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlUrl)
                    .addComponent(jtfUrlAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbFetch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbDownload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpbDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbFetchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFetchActionPerformed
        if (downloader.isRetrievingFiles()) {
            downloader.stopRetrievingFiles();
            setFetchEnabled(true);

        } else {
            dlmDownloads.clear();

            try {
                downloader.startRetrievingFiles(new URL(jtfUrlAddress.getText()));
                setFetchEnabled(false);

            } catch (MalformedURLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jbFetchActionPerformed

    private void jbDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDownloadActionPerformed
        if (downloader.isDownloading()) {
            downloader.stopDownloading();

        } else {
            downloader.download(jlDownloads.getSelectedValuesList());

            setDownloadEnabled(false);
        }
    }//GEN-LAST:event_jbDownloadActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JButton jbDownload;
    private javax.swing.JButton jbFetch;
    private javax.swing.JList<DownloaderTask> jlDownloaded;
    private javax.swing.JList<DownloaderTask> jlDownloads;
    private javax.swing.JLabel jlUrl;
    private javax.swing.JProgressBar jpbDownload;
    private javax.swing.JTextField jtfUrlAddress;
    // End of variables declaration//GEN-END:variables

    @Override
    public void downloadBegin(DownloaderTask task) {

    }

    @Override
    public void downloadProgress(DownloaderTask task) {
        jlDownloads.repaint();
    }

    @Override
    public void downloadComplete(DownloaderTask task) {
        dlmDownloaded.addElement(task);
        dlmDownloads.removeElement(task);
    }

    @Override
    public void downloadAllFilesPrepared() {
        setFetchEnabled(true);
    }

    @Override
    public void downloadPrepared(DownloaderTask task) {
        dlmDownloads.addElement(task);
    }

    @Override
    public void downloadCompleteCurrentTasks() {
        setDownloadEnabled(true);
    }

    private void setDownloadEnabled(boolean state) {
        jtfUrlAddress.setEnabled(state);
        jbFetch.setEnabled(state);
        jbDownload.setText(state ? "DOWNLOAD" : "STOP");
        jlDownloads.setEnabled(state);
    }

    private void setFetchEnabled(boolean state) {
        jtfUrlAddress.setEnabled(state);
        jbDownload.setEnabled(state);
        jbFetch.setText(state ? "FETCH" : "STOP");
    }

}
