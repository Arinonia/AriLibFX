package fr.arinonia.arilibfx.updater.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arinonia on 07/06/2020 inside the package - fr.arinonia.arilibfx.updater.json
 */
public class Data {
    private DataMaintenance maintenance;
    private List<DataFile> files = new ArrayList<DataFile>();
    private List<String> ignoredFiles = new ArrayList<String>();


    public DataMaintenance getMaintenance() {
        return maintenance;
    }

    public List<DataFile> getFiles() {
        return files;
    }

    public List<String> getIgnoredFiles() {
        return ignoredFiles;
    }

    public void setMaintenance(DataMaintenance maintenance) {
        this.maintenance = maintenance;
    }

    public void setFiles(List<DataFile> files) {
        this.files = files;
    }

    public void setIgnoredFiles(List<String> ignoredFiles) {
        this.ignoredFiles = ignoredFiles;
    }
}
