package fr.arinonia.arilibfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
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
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(AriLibFX.class.getResourceAsStream(getResourcePath()+"/" + image + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bufferedImage != null;
        return SwingFXUtils.toFXImage(bufferedImage, null);
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
