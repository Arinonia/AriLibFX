package fr.arinonia.arilibfx.updater.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arinonia on 07/06/2020 inside the package - fr.arinonia.arilibfx.updater.json
 */
public class Data {
    private DataMaintenance maintenance;
    private List<DataFile> files = new ArrayList<DataFile>();
    private List<String> ignoreFiles = new ArrayList<String>();


    public DataMaintenance getMaintenance() {
        return maintenance;
    }

    public List<DataFile> getFiles() {
        return files;
    }

    public List<String> getIgnoreFiles() {
        return ignoreFiles;
    }

    public void setMaintenance(DataMaintenance maintenance) {
        this.maintenance = maintenance;
    }

    public void setFiles(List<DataFile> files) {
        this.files = files;
    }

    public void setIgnoredFiles(List<String> ignoreFiles) {
        this.ignoreFiles = ignoreFiles;
    }
}
