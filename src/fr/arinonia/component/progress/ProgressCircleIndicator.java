package fr.arinonia.component.progress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.Control;

import com.sun.javafx.css.converters.SizeConverter;

import fr.arinonia.AriLibFX;


@SuppressWarnings("restriction")
abstract class ProgressCircleIndicator extends Control {
    private static final int INDETERMINATE_PROGRESS = -1;

    private ReadOnlyIntegerWrapper progress = new ReadOnlyIntegerWrapper(0);
    private ReadOnlyBooleanWrapper indeterminate = new ReadOnlyBooleanWrapper(false);
    /**
     * constructor
     */
    public ProgressCircleIndicator() {
        this.getStylesheets().add(AriLibFX.class.getResource("resources/circleprogress.css").toExternalForm());
    }
    /**
     * 
     * @return
     */
    public int getProgress() {
        return progress.get();
    }

    /**
     * 
     * @param progressValue
     */
    public void setProgress(int progressValue) {
        progress.set(defaultToHundred(progressValue));
        indeterminate.set(progressValue < 0);
    }
    /**
     * 
     * @return
     */
    public ReadOnlyIntegerProperty progressProperty() {
        return progress.getReadOnlyProperty();
    }
    /**
     * 
     * @return
     */
    public boolean isIndeterminate() {
        return indeterminate.get();
    }
    /**
     * progress indeterminate
     */
    public void makeIndeterminate() {
        setProgress(INDETERMINATE_PROGRESS);
    }
    /**
     * 
     * @return
     */
    public ReadOnlyBooleanProperty indeterminateProperty() {
        return indeterminate.getReadOnlyProperty();
    }
    /**
     * 
     * @param value
     * @return
     */
    private int defaultToHundred(int value) {
        if (value > 100) {
            return 100;
        }
        return value;
    }
    /**
     * 
     * @param value
     */
    public final void setInnerCircleRadius(int value) {
        innerCircleRadiusProperty().set(value);
    }
    /**
     * 
     * @return
     */
    public final DoubleProperty innerCircleRadiusProperty() {
        return innerCircleRadius;
    }
    /**
     * 
     * @return
     */
    public final double getInnerCircleRadius() {
        return innerCircleRadiusProperty().get();
    }

    /**
     * 
     */
    private DoubleProperty innerCircleRadius = new StyleableDoubleProperty(60) {
        @Override
        public Object getBean() {
            return ProgressCircleIndicator.this;
        }

        @Override
        public String getName() {
            return "innerCircleRadius";
        }

        @Override
        public CssMetaData<ProgressCircleIndicator, Number> getCssMetaData() {
            return StyleableProperties.INNER_CIRCLE_RADIUS;
        }
    };
    /**
     * 
     * @author arino
     *
     */
    private static class StyleableProperties {
		private static final CssMetaData<ProgressCircleIndicator, Number> INNER_CIRCLE_RADIUS = new CssMetaData<ProgressCircleIndicator, Number>(
                "-fx-inner-radius", SizeConverter.getInstance(), 60) {

            @Override
            public boolean isSettable(ProgressCircleIndicator n) {
                return n.innerCircleRadiusProperty() == null || !n.innerCircleRadiusProperty().isBound();
            }

            @SuppressWarnings("unchecked")
			@Override
            public StyleableProperty<Number> getStyleableProperty(ProgressCircleIndicator n) {
                return (StyleableProperty<Number>) n.innerCircleRadiusProperty();
            }
        };

        public static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Control.getClassCssMetaData());
            styleables.add(INNER_CIRCLE_RADIUS);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }
    /**
     * 
     * @return
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }
    
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }
}
