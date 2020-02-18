package fr.arinonia;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AriLibFX
{
	private static String programName;
	private static String resourcePath;

	public static String setResponsiveBackground(String url)
	{
		return "-fx-background-image: url('"+url+"');"
				+"-fx-backgound-repeat: skretch;"+"-fx-backgound-position: center center;"
				+"-fx-background-size: cover;";
	}

	public static Image loadImage(String image)
	{
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(AriLibFX.class.getResourceAsStream(getResourcePath()+"/" + image + ".png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return SwingFXUtils.toFXImage(bufferedImage, null);
	}

	public static void setProgramName(String programName)
	{
		AriLibFX.programName = programName;
	}

	public static String getProgramName()
	{
		return programName == null ? "" : programName;
	}

	public static String getResourcePath()
	{
		return resourcePath;
	}

	public static void setResourcePath(String resourcePath)
	{
		AriLibFX.resourcePath = resourcePath.endsWith("/")
				? resourcePath.substring(0, resourcePath.length() - 1)
				: resourcePath;
	}
}
