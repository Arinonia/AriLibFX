package fr.arinonia.arilibfx.auth.premium;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Arinonia on 10/04/2020 inside the package - fr.arinonia.arilibfx.auth.premium
 */
public class Profile implements Serializable {

    private String id;
    private String name;
    private boolean legacy;

    public Profile(String id, String name, boolean legacy) {
        this.id = id;
        this.name = name;
        this.legacy = legacy;
    }
    public UUID getUUID(){return UUID.fromString(id.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));}
    public String getName() {return name;}
    public boolean isLegacy() {return legacy;}
}