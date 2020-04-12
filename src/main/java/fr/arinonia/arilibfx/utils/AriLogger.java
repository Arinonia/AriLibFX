package fr.arinonia.arilibfx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Arinonia on 05/03/2020 inside the package - fr.arinonia.arilibfx.utils
 */
public class AriLogger {

    private String name;

    public AriLogger(String name) {
        this.name = name;
    }

    public void log(String s, Object... format) {
        System.out.println(genPrefix() + "[INFO] " + String.format(s, format));
    }

    public void warn(String s, Object... format) {
        System.err.println(genPrefix() + "[WARN] " + String.format(s, format));
    }

    private String genPrefix() {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(name).append("]").append(" [");
        String pattern = "dd/MM/YYYY HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        builder.append(date).append("] ");
        return builder.toString();
    }

    public String getName() { return name;}
}
