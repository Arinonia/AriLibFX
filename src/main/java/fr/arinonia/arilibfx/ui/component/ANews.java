package fr.arinonia.arilibfx.ui.component;

import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Arinonia on 07/03/2020 inside the package - fr.arinonia.arilibfx.ui.component
 */

class ANews extends GridPane {

    /*
    *Je déconseille de modifier la taille des rectangles sinon il faudra tout ré-alligner
     */

    private Rectangle backgroundRect = new Rectangle();
    private Rectangle borderRect = new Rectangle();

    public ANews(String link, String text) {
        Image news = new Image(link);
        ImageView imageNews = new ImageView(news);
        Rectangle borderRect = new Rectangle();
        Label info = new Label(text);
        GridPane.setVgrow(backgroundRect, Priority.ALWAYS);
        GridPane.setHgrow(backgroundRect, Priority.ALWAYS);
        GridPane.setValignment(backgroundRect, VPos.BOTTOM);
        GridPane.setVgrow(borderRect, Priority.ALWAYS);
        GridPane.setHgrow(borderRect, Priority.ALWAYS);
        GridPane.setValignment(borderRect, VPos.BOTTOM);
        GridPane.setVgrow(imageNews, Priority.ALWAYS);
        GridPane.setHgrow(imageNews, Priority.ALWAYS);
        GridPane.setValignment(imageNews, VPos.BOTTOM);
        GridPane.setVgrow(info, Priority.ALWAYS);
        GridPane.setHgrow(info, Priority.ALWAYS);
        GridPane.setValignment(info, VPos.BOTTOM);
        backgroundRect.setFill(Color.rgb(0,0,0,0.5d));
        backgroundRect.setWidth(300);
        backgroundRect.setHeight(200);
        borderRect.setWidth(300);
        borderRect.setHeight(170);
        borderRect.setTranslateY(-30);
        borderRect.setFill(Color.rgb(255,255,255,0.2));
        imageNews.setFitWidth(298);
        imageNews.setFitHeight(168);
        imageNews.setTranslateY(-31);
        imageNews.setTranslateX(1);
        info.setStyle("-fx-text-fill: #95bad3");
        info.setTranslateY(-8);
        info.setTranslateX(6);
        this.getChildren().addAll(backgroundRect,borderRect,imageNews,info);
    }

    public void setBackgroundRectColor(Color backgroundRectColor) {
        this.backgroundRect.setFill(backgroundRectColor);
    }
    public void setBorderRectColor(Color borderRectColor){
        this.borderRect.setFill(borderRectColor);
    }
}
