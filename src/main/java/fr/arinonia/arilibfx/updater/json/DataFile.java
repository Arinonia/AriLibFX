package fr.arinonia.arilibfx.updater.json;

/**
 * Created by Arinonia on 07/06/2020 inside the package - fr.arinonia.arilibfx.updater.json
 */
public class DataFile {
    private String path;
    private String hash;
    private String url;

    public DataFile(String path, String hash, String url) {
        this.path = path;
        this.hash = hash;
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public String getHash() {
        return hash;
    }

    public String getUrl() {
        return url;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
