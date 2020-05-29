package fr.arinonia.arilibfx;

import javafx.scene.image.Image;

/**
 * Created by Arinonia on 05/03/2020 inside the package - fr.arinonia.arilibfx
 */

public class AriLibFX {

    private static String programName;
    private static String resourcePath;

    public static String setResponsiveBackground(String url) {
        return "-fx-background-image: url('"+url+"');"
                +"-fx-backgound-repeat: skretch;"+"-fx-backgound-position: center center;"
                +"-fx-background-size: cover;";
    }
    
    public static Image loadImage(String image) {
        loadImage(image, ".png");
    }
    
    public static Image loadImage(String image, String extension) {
        return new Image(AriLibFX.class.getResourceAsStream(getResourcePath() + "/" + image + extension));
    }

    public static void setProgramName(String programName) { AriLibFX.programName = programName; }

    public static String getProgramName() {
        if (programName == null)
            return "";
        return programName;
    }

    public static String getResourcePath() {return resourcePath;}


    public static void setResourcePath(String resourcePath) {
        AriLibFX.resourcePath = resourcePath.endsWith("/") ? resourcePath.substring(0, resourcePath.length() - 1) : resourcePath;
    }

}
