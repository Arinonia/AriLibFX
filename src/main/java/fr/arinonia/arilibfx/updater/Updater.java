package fr.arinonia.arilibfx.updater;

import com.google.gson.Gson;
import fr.arinonia.arilibfx.AriLibFX;
import fr.arinonia.arilibfx.updater.json.Data;
import fr.arinonia.arilibfx.updater.json.DataFile;
import fr.arinonia.arilibfx.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Arinonia on 07/06/2020 inside the package - fr.arinonia.arilibfx.updater
 */
public class Updater {

    private static final Gson JSON = new Gson();

    private final List<String> ignoredFiles = Collections.synchronizedList(new ArrayList<String>());
    private final Queue<DownloadManager> needToDownload = new LinkedList<>();
    private boolean fileDeleter = false;


    public void start(){
        int length = this.needToDownload.size();
        for (int i = 0; i < length; i++){
            DownloadManager downloadManager = this.needToDownload.poll();
            if (!downloadManager.getFile().exists()){
                downloadManager.getFile().mkdirs();
                AriLibFX.LOGGER.log("Folder " + downloadManager.getFile().getName() + " doesn't exist, mkdirs folder.");
            }else{
                AriLibFX.LOGGER.log("Folder " + downloadManager.getFile().getName() + " exist, start check url.");
            }
            this.jsonDeserialize(this.getUrlContents(downloadManager.getUrl()), downloadManager.getJob(), downloadManager.getFile());
            downloadManager.getJob().getExecutorService().shutdown();
            try {
                downloadManager.getJob().getExecutorService().awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void jsonDeserialize(String str, DownloadJob job, File dir){
        Data data = JSON.fromJson(str, Data.class);

        System.out.println("maintenance: " + data.getMaintenance().isMaintenance());
        System.out.println("maintenanceMessage: " + data.getMaintenance().getMessage());

        if(data.getMaintenance().isMaintenance()){
            //TODO
        }

        for (DataFile file : data.getFiles()){
            File client_file = new File(dir, file.getPath());
            if (!client_file.exists() || !client_file.isFile()){
                job.addDownloadable(client_file, file.getHash(), file.getUrl());
                this.ignoredFiles.add(client_file.getAbsolutePath());
                continue;
            }
            try {
                if(!Utils.getMD5(client_file).equals(file.getHash())){
                    AriLibFX.LOGGER.log("File " + client_file.getName() + " has a bad hash '" + Utils.getMD5(client_file) + "' hash on server => " + file.getHash());
                    job.addDownloadable(client_file, file.getHash(), file.getUrl());
                    this.ignoredFiles.add(client_file.getAbsolutePath());
                }else{
                    this.ignoredFiles.add(client_file.getAbsolutePath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (String ignore : data.getIgnoreFiles()){
            File file = new File(dir, ignore);
            if(file.isDirectory()){
                ArrayList<File> files = listFiles(file);
                for (File f : files){
                    this.ignoredFiles.add(f.getAbsolutePath());
                }
            }else{
                this.ignoredFiles.add(file.getAbsolutePath());
            }
        }
        if(fileDeleter){
            checkingFiles(dir);
        }
        job.startDownloading(job.getExecutorService());
    }

    private boolean isIgnored(File file){
        for (String path : this.ignoredFiles){
            if (path.equals(file.getAbsolutePath()))
                return true;
        }
        return false;
    }

    public void checkingFiles(File dir){
        ArrayList<File> files = listFiles(dir);
        for(File file : files){
            if (!isIgnored(file)){
                file.delete();
            }
        }

    }

    private ArrayList<File> listFiles(File folder) {
        File[] files = folder.listFiles();
        ArrayList<File> list = new ArrayList<File>();

        if (files == null) {
            return list;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                list.addAll(listFiles(f));
            }
            else {
                list.add(f);
            }
        }
        return list;
    }

    private String getUrlContents(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void addJobToDownload(DownloadManager downloadManager){
        this.needToDownload.add(downloadManager);
    }

    public void setFileDeleter(boolean fileDeleter) {
        this.fileDeleter = fileDeleter;
    }

    public List<String> getIgnoredFiles() {
        return ignoredFiles;
    }

    public Queue<DownloadManager> getNeedToDownload() {
        return needToDownload;
    }
}

