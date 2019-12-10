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

	/**
	 * 
	 * @param node
	 */
	public AnimationFX(Node node) {
		super();
		setNode(node);

	}

	public AnimationFX() {
		hasNextAnimation = false;
		this.reset = false;

	}

	/**
	 * 
	 * @return on finish animation
	 */
	private AnimationFX onFinished() {
		if (reset) {
			resetNode();
		}
		if (this.nextAnimation != null) {
			this.nextAnimation.play();
		}
		return this;
	}

	/**
	 * 
	 * @param animation
	 * @return play next animation when current animation is finish
	 */
	public AnimationFX playOnFinished(AnimationFX animation) {
		setNextAnimation(animation);
		return this;

	}
	/**
	 * 
	 * @param reset
	 * @return
	 */
	public AnimationFX setResetOnFinished(boolean reset) {
		this.reset = reset;
		return this;
	}
	/**
	 * play animation
	 */
	public void play() {
		timeline.play();
	}
	/**
	 * stop animation
	 * @return
	 */
	public AnimationFX stop() {
		timeline.stop();
		return this;
	}

	/**
	 * 
	 * @return
	 */
	abstract AnimationFX resetNode();
	/**
	 * 
	 */
	abstract void initTimeline();
	/**
	 * 
	 * @return timeline
	 */
	public Timeline getTimeline() {
		return timeline;
	}
	/**
	 * 
	 * @param timeline
	 */
	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isResetOnFinished() {
		return reset;
	}
	/**
	 * 
	 * @param reset
	 */
	protected void setReset(boolean reset) {
		this.reset = reset;
	}
	/**
	 * 
	 * @return node
	 */
	public Node getNode() {
		return node;
	}
	/**
	 * 
	 * @param node
	 */
	public void setNode(Node node) {
		this.node = node;
		initTimeline();
		timeline.statusProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.equals(Animation.Status.STOPPED)) {
				onFinished();
			}

		});
	}
	/**
	 * 
	 * @return
	 */
	public AnimationFX getNextAnimation() {
		return nextAnimation;
	}
	/**
	 * 
	 * @param nextAnimation
	 */
	protected void setNextAnimation(AnimationFX nextAnimation) {
		hasNextAnimation = true;
		this.nextAnimation = nextAnimation;
	}
	/**
	 * 
	 * @return
	 */
	public boolean hasNextAnimation() {
		return hasNextAnimation;
	}
	/**
	 * 
	 * @param hasNextAnimation
	 */
	protected void setHasNextAnimation(boolean hasNextAnimation) {
		this.hasNextAnimation = hasNextAnimation;
	}
	/**
	 * 
	 * @param value
	 * @return
	 */
	public AnimationFX setCycleCount(int value) {
		this.timeline.setCycleCount(value);
		return this;
	}
	/**
	 * 
	 * @param value
	 * @return
	 */
	public AnimationFX setSpeed(double value) {
		this.timeline.setRate(value);
		return this;
	}
	/**
	 * 
	 * @param value
	 * @return
	 */
	public AnimationFX setDelay(Duration value) {
		this.timeline.setDelay(value);
		return this;
	}
	/**
	 * 
	 * @param value
	 */
	public final void setOnFinished(EventHandler<ActionEvent> value) {
		this.timeline.setOnFinished(value);
	}

}
