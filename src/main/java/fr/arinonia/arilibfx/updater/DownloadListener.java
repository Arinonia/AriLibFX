package fr.arinonia.arilibfx.updater;

/**
 * Created by Arinonia on 07/06/2020 inside the package - fr.arinonia.arilibfx.updater
 */
public interface DownloadListener {
    public void onDownloadJobFinished(DownloadJob job);
    public void onDownloadJobProgressChanged(DownloadJob job);
}
