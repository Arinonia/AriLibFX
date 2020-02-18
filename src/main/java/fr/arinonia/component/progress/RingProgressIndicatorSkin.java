package fr.arinonia.component.progress;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class RingProgressIndicatorSkin implements Skin<RingProgressIndicator>
{
    private final RingProgressIndicator indicator;
    private final Label percentLabel = new Label();
    private final Circle innerCircle = new Circle();
    private final Circle outerCircle = new Circle();
    private final StackPane container = new StackPane();
    private final Arc fillerArc = new Arc();
    private final RotateTransition transition = new RotateTransition(Duration.millis(2000), this.fillerArc);

    public RingProgressIndicatorSkin(final RingProgressIndicator indicator)
    {
        this.indicator = indicator;
        this.initContainer(indicator);
        this.initFillerArc();
        this.container.widthProperty().addListener((o, oldVal, newVal) -> this.fillerArc.setCenterX(newVal.intValue() / 2));
        this.container.heightProperty().addListener((o, oldVal, newVal) -> this.fillerArc.setCenterY(newVal.intValue() / 2));
        this.innerCircle.getStyleClass().add("ringindicator-inner-circle");
        this.outerCircle.getStyleClass().add("ringindicator-outer-circle-secondary");
        this.updateRadii();

        this.indicator.indeterminateProperty().addListener((o, oldVal, newVal) -> this.initIndeterminate(newVal));
        this.indicator.progressProperty().addListener((o, oldVal, newVal) ->
        {
            if (newVal.intValue() >= 0)
            {
                this.setProgressLabel(newVal.intValue());
                fillerArc.setLength(newVal.intValue() * -3.6);
            }
        });
        this.indicator.ringWidthProperty().addListener((o, oldVal, newVal) -> this.updateRadii());
        this.innerCircle.strokeWidthProperty().addListener((e) -> this.updateRadii());
        this.innerCircle.radiusProperty().addListener((e) -> this.updateRadii());
        this.initTransition();
        this.initIndeterminate(indicator.isIndeterminate());
        this.initLabel(indicator.getProgress());
        indicator.visibleProperty().addListener((o, oldVal, newVal) ->
        {
            if (newVal && this.indicator.isIndeterminate())
            {
                this.transition.play();
            }
            else
            {
                this.transition.pause();
            }
        });
        this.container.getChildren().addAll(this.fillerArc, this.outerCircle, this.innerCircle, this.percentLabel);
    }

    private void setProgressLabel(int value)
    {
        if (value >= 0)
        {
            this.percentLabel.setText(String.format("%d%%", value));
        }
    }

    private void initTransition()
    {
        this.transition.setAutoReverse(false);
        this.transition.setCycleCount(Animation.INDEFINITE);
        this.transition.setDelay(Duration.ZERO);
        this.transition.setInterpolator(Interpolator.LINEAR);
        this.transition.setByAngle(360);
    }

    private void initFillerArc()
    {
        this.fillerArc.setManaged(false);
        this.fillerArc.getStyleClass().add("ringindicator-filler");
        this.fillerArc.setStartAngle(90);
        this.fillerArc.setLength(this.indicator.getProgress() * -3.6);
    }

    private void initContainer(final RingProgressIndicator indicator)
    {
        this.container.getStylesheets().addAll(indicator.getStylesheets());
        this.container.getStyleClass().addAll("circleindicator-container");
        this.container.setMaxHeight(Region.USE_PREF_SIZE);
        this.container.setMaxWidth(Region.USE_PREF_SIZE);
    }

    private void updateRadii()
    {
        final double ringWidth = indicator.getRingWidth();
        final double innerCircleHalfStrokeWidth = innerCircle.getStrokeWidth() / 2;
        final double innerCircleRadius = indicator.getInnerCircleRadius();

        this.outerCircle.setRadius(innerCircleRadius + innerCircleHalfStrokeWidth + ringWidth);
        this.fillerArc.setRadiusY(innerCircleRadius + innerCircleHalfStrokeWidth - 1 + (ringWidth / 2));
        this.fillerArc.setRadiusX(innerCircleRadius + innerCircleHalfStrokeWidth - 1 + (ringWidth / 2));
        this.fillerArc.setStrokeWidth(ringWidth);
        this.innerCircle.setRadius(innerCircleRadius);
    }

    private void initLabel(int value)
    {
        this.setProgressLabel(value);
        this.percentLabel.getStyleClass().add("circleindicator-label");
    }

    private void initIndeterminate(boolean newVal)
    {
        this.percentLabel.setVisible(!newVal);
        if (newVal)
        {
            this.fillerArc.setLength(360);
            this.fillerArc.getStyleClass().add("indeterminate");
            if (this.indicator.isVisible())
            {
                this.transition.play();
            }
        }
        else
        {
            this.fillerArc.getStyleClass().remove("indeterminate");
            this.fillerArc.setRotate(0);
            this.transition.stop();
        }
    }

    @Override
    public RingProgressIndicator getSkinnable()
    {
        return this.indicator;
    }

    @Override
    public Node getNode()
    {
        return this.container;
    }

    @Override
    public void dispose()
    {
        this.transition.stop();
    }
}
