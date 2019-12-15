package fr.arinonia.component.alert;

import java.io.PrintWriter;
import java.io.StringWriter;

import fr.arinonia.AriLibFX;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class FxAlert {

	private Alert alert;
	
	/**
	 * 
	 * @param type
	 * @param title
	 * @param header
	 * @param content
	 * @param exception
	 * @param label
	 */
	public FxAlert(AlertType type, String title, String header, String content, Exception exception,String lbl) {
		alert = new Alert(type);
		alert.setTitle(title);
		
		alert.setHeaderText(header);
		alert.setContentText(content);

		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label(lbl);

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		DialogPane dialogPane = alert.getDialogPane();
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		dialogPane.getStylesheets().add(AriLibFX.class.getResource("/fr/arinonia/resources/dialogs.css").toExternalForm());
		dialogPane.getStyleClass().add("dialogs");
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
	
	/**
	 * 
	 * @param type
	 * @param title
	 * @param header
	 * @param content
	 * @param exception
	 * @param label
	 * @param cssPath
	 * @param main class
	 */
	public FxAlert(AlertType type, String title, String header, String content, Exception exception,String lbl, String cssPath, Class<?> cls) {
		alert = new Alert(type);
		alert.setTitle(title);
		
		alert.setHeaderText(header);
		alert.setContentText(content);

		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label(lbl);

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		DialogPane dialogPane = alert.getDialogPane();
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		dialogPane.getStylesheets().add(cls.getResource(cssPath).toExternalForm());
		dialogPane.getStyleClass().add("dialogs");
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		
	}
	/**
	 * 
	 * @param image
	 */
	public void setIcon(String image) {
		Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(AriLibFX.loadImage(image));
	}
	/**
	 * 
	 * @param image
	 * @param x
	 * @param y
	 */
	public void setImage(String image, int x, int y) {
		ImageView imageView = new ImageView(AriLibFX.loadImage(image));
		imageView.setFitHeight(x);
		imageView.setFitWidth(y);
		alert.setGraphic(imageView);
	}
	/**
	 * set alert visible
	 */
	public void show() {
		alert.showAndWait();
	}
	
}
