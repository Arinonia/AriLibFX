package fr.arinonia.arilibfx.ui.component;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Created by Arinonia on 17/04/2020 inside the package - fr.arinonia.arilibfx.ui.component
 */
public class AProgressBar extends GridPane
{

	private final Rectangle backgroundRect = new Rectangle();
	private final Rectangle foregroundRect = new Rectangle();

	public AProgressBar()
	{
		init(100,40);
	}

	public AProgressBar(int width, int height)
	{
		init(width,height);
	}

	private void init(int width, int height)
	{
		GridPane.setVgrow(foregroundRect, Priority.ALWAYS);
		GridPane.setHgrow(foregroundRect, Priority.ALWAYS);
		GridPane.setValignment(foregroundRect, VPos.CENTER);
		GridPane.setHalignment(foregroundRect, HPos.LEFT);
		setBarWidth(width);
		setBarHeight(height);
		this.getChildren().addAll(backgroundRect,foregroundRect);
	}

	public void setBarWidth(double width)
	{
		this.backgroundRect.setWidth(width);
		this.foregroundRect.setWidth(0);
		this.setPrefWidth(width);
		this.setMaxWidth(width);
	}
	public void setBarHeight(double height)
	{
		this.backgroundRect.setHeight(height);
		this.foregroundRect.setHeight(height);
		this.setPrefHeight(height);
		this.setMaxHeight(height);
	}

	public double getBarWidth()
	{
		return this.backgroundRect.getWidth();
	}
	public void setBackgroundColor(Color fill){this.backgroundRect.setFill(fill);}
	public void setForegroundColor(Paint fill){this.foregroundRect.setFill(fill);}

	private float percentage(float val, float max){ return val * (float)this.getBarWidth() / max; }
	public void setProgress(float val, float max){ foregroundRect.setWidth(percentage(val,max)); }

}
