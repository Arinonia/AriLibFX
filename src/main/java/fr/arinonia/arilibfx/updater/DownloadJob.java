package fr.arinonia.arilibfx.updater;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Arinonia on 07/06/2020 inside the package - fr.arinonia.arilibfx.updater
 */
public class DownloadJob {

    private final String name;
    private final DownloadListener listener;

    private final Queue<DownloadTask> remainingFiles = new ConcurrentLinkedQueue<DownloadTask>();
    private final List<DownloadTask> allFiles = Collections.synchronizedList(new ArrayList<DownloadTask>());
    private final List<DownloadTask> failures = Collections.synchronizedList(new ArrayList<DownloadTask>());
    private final AtomicInteger remainingThreads = new AtomicInteger();

    private boolean started;

    public DownloadJob(String name, DownloadListener listener) {
        this.name = name;
        this.listener = listener;
    }

    public void addDownloadable(File file, String serverHash, String url) {
        if(started)
            throw new IllegalStateException("Cannot add to download job that has already started");
        DownloadTask task = null;

        try {
            task = new DownloadTask(new URL(url), file, serverHash);
        } catch (MalformedURLException ignored) {}

        this.allFiles.add(task);
        this.remainingFiles.add(task);
    }

    private void popAndDownload() {
        DownloadTask task;
        while ((task = remainingFiles.poll()) != null) {
            if (task.getNunAttempts() > 5 ) {
                System.err.println("Gave up trying to download " + task.getUrl() + " for job '" + name + "'");
            }
            else{
                try {
                    final String result = task.download();
                    System.out.println("Finished downloading " + task.getDestination() + " for job '" + name + "'" + ": " + result);
                    this.listener.onDownloadJobProgressChanged(this);
                } catch (IOException e) {
                    System.err.println("Couldn't download " + task.getUrl() + " for job '" + name + "'");
                    remainingFiles.add(task);
                }
            }
        }
        if (remainingThreads.decrementAndGet() <= 0) {
            this.listener.onDownloadJobFinished(this);
        }
    }

    public void startDownloading(final ThreadPoolExecutor executor){
        if (started) {
            throw new IllegalStateException("Cannot start download job that as already started !");
        }
        started = true;
        if (allFiles.isEmpty()) {
            System.out.println("Download job '" + name + "' skipped as there are no files to download");
            this.listener.onDownloadJobFinished(this);
        }
        else {
            final int threads = executor.getMaximumPoolSize();
            remainingThreads.set(threads);
            System.out.println("Download job '" + name + "' started (" + threads + " threads, " + allFiles.size() + " files)");
            for (int i = 0; i < threads; i++) {
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        popAndDownload();
                    }
                });
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getFailures() {
        return failures.size();
    }


    public List<DownloadTask> getAllFiles() {
        return allFiles;
    }

    public boolean isComplete(){
        return started && remainingFiles.isEmpty() && remainingThreads.get() == 0;
    }
}
