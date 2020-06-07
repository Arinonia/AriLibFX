package fr.arinonia.arilibfx.updater.json;

/**
 * Created by Arinonia on 07/06/2020 inside the package - fr.arinonia.arilibfx.updater.json
 */
public class DataMaintenance {
    private boolean maintenance;
    private String message;

    public boolean isMaintenance() {
        return maintenance;
    }

    public String getMessage() {
        return message;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
