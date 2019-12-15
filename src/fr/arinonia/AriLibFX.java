package fr.arinonia;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class AriLibFX {

	private static String programName;
	private static String resourcePath;
	
	/**
	 * 
	 * @param url
	 * @return link to responsive background
	 */
	public static String setResponsiveBackground(String url) {
		return "-fx-background-image: url('"+url+"');"
				+"-fx-backgound-repeat: skretch;"+"-fx-backgound-position: center center;"
				+"-fx-background-size: cover;";
	}
	
	
	/**
	 * 
	 * @param image
	 * @return imageFX
	 */
	public static Image loadImage(String image) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(AriLibFX.class.getResourceAsStream(getResourcePath()+"/" + image + ".png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
		return fxImage;
	}
	
	
	/**
	 * @param programName
	 */
	public static void setProgramName(String programName) {
		AriLibFX.programName = programName;
	}

	/**
	 * 
	 * @return programName
	 */
	public static String getProgramName() {
		if (programName == null)
			return "";
		return programName;
	}

	/**
	 * 
	 * @return resourcePath
	 */
	public static String getResourcePath() {
		return resourcePath;
	}

	/**
	 * 
	 * @param resourcePath
	 */
	public static void setResourcePath(String resourcePath) {
		AriLibFX.resourcePath = resourcePath.endsWith("/") ? resourcePath.substring(0, resourcePath.length() - 1)
				: resourcePath;
	}
	
	
	
	
}
