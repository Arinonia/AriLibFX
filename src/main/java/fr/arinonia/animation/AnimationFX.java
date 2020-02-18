package fr.arinonia.animation;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public abstract class AnimationFX {

	public static final int INDEFINITE = -1;
	private Timeline timeline;
	private boolean reset;
	private Node node;
	private AnimationFX nextAnimation;
	private boolean hasNextAnimation;

	public AnimationFX(Node node) {
		super();
		setNode(node);

	}

	public AnimationFX() {
		hasNextAnimation = false;
		this.reset = false;

	}


	private AnimationFX onFinished() {
		if (reset) {
			resetNode();
		}
		if (this.nextAnimation != null) {
			this.nextAnimation.play();
		}
		return this;
	}


	public AnimationFX playOnFinished(AnimationFX animation) {
		setNextAnimation(animation);
		return this;

	}

	public AnimationFX setResetOnFinished(boolean reset) {
		this.reset = reset;
		return this;
	}
	
	public void play() {
		timeline.play();
	}
	
	public AnimationFX stop() {
		timeline.stop();
		return this;
	}

	
	abstract AnimationFX resetNode();
	
	abstract void initTimeline();

	public Timeline getTimeline() {
		return timeline;
	}
	
	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}
	
	public boolean isResetOnFinished() {
		return reset;
	}
	
	protected void setReset(boolean reset) {
		this.reset = reset;
	}
	
	public Node getNode() {
		return node;
	}
	
	public void setNode(Node node) {
		this.node = node;
		initTimeline();
		timeline.statusProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.equals(Animation.Status.STOPPED)) {
				onFinished();
			}

		});
	}
	
	public AnimationFX getNextAnimation() {
		return nextAnimation;
	}
	
	protected void setNextAnimation(AnimationFX nextAnimation) {
		hasNextAnimation = true;
		this.nextAnimation = nextAnimation;
	}
	
	public boolean hasNextAnimation() {
		return hasNextAnimation;
	}
	
	protected void setHasNextAnimation(boolean hasNextAnimation) {
		this.hasNextAnimation = hasNextAnimation;
	}
	
	public AnimationFX setCycleCount(int value) {
		this.timeline.setCycleCount(value);
		return this;
	}

	public AnimationFX setSpeed(double value) {
		this.timeline.setRate(value);
		return this;
	}
	
	public AnimationFX setDelay(Duration value) {
		this.timeline.setDelay(value);
		return this;
	}
	
	public final void setOnFinished(EventHandler<ActionEvent> value) {
		this.timeline.setOnFinished(value);
	}

}
