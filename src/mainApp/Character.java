package mainApp;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

/**
 * Class: Character
 * 
 * @author Tulsi Manohar, Siddarth Peddi Purpose: Abstract class that connects
 *         Egg, Enemy, and Hero Restrictions: None
 *
 */
public abstract class Character {
	public final static int SCREEN_SIZE_X = 1000;
	public final static int SCREEN_SIZE_Y = 1000;
	public final static int GRID_CONSTANT_X = SCREEN_SIZE_X / 10;
	public final static int GRID_CONSTANT_Y = SCREEN_SIZE_Y / 5;
	private int facingDirection, posistionX, posistionY, maximumVerticalSpeed, maxSpeed, jumpStregnth, hAcceleration,
			vAcceleration, width, height;
	double velocityX, velocityY;
	private boolean isGoingLeft, isGoingRight, isGoingUp, isGoingDown, shouldRemove;
	private Image image;

	/**
	 * ensures: that the image is returned
	 * 
	 * @return image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * ensures: that image is set to image
	 * 
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * ensures: that the facing direction is returned
	 * 
	 * @return facingDirection
	 */
	public int getFacingDirection() {
		return facingDirection;
	}

	/**
	 * ensures: that Facing Direction is set to facingDirection
	 * 
	 * @param facingDirection
	 */
	public void setFacingDirection(int facingDirection) {
		this.facingDirection = facingDirection;
	}

	/**
	 * ensures: that the velocity in x is returned
	 * 
	 * @return velocityX
	 */
	public int getVelocityX() {
		return (int) velocityX;
	}

	/**
	 * ensures: that Velocity in X is set to velocityX
	 * 
	 * @param velocityX
	 */
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	/**
	 * adds acceleration in x to the current velocity in x
	 * 
	 * @param accelX
	 */
	public void addVelocityX(int accelX) {
		this.velocityX = velocityX + accelX;
	}

	/**
	 * ensures: that the velocity in y is returned
	 * 
	 * @return velocityY
	 */
	public int getVelocityY() {
		return (int) velocityY;
	}

	/**
	 * ensures: that Velocity in Y is set to velocityY
	 * 
	 * @param velocityY
	 */
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	/**
	 * adds acceleration in y to the current velocity in y
	 * 
	 * @param accelX
	 */
	public void addVelocityY(int accelY) {
		this.velocityY = velocityY + accelY;
	}

	/**
	 * ensures: that the position in x is returned
	 * 
	 * @return positionX
	 */
	public int getPosistionX() {
		return posistionX;
	}

	/**
	 * ensures: that Position in X is set to posistionX
	 * 
	 * @param posistionX
	 */
	public void setPosistionX(int posistionX) {
		this.posistionX = posistionX;
	}

	/**
	 * ensures: that the position in y is returned
	 * 
	 * @return positionYX
	 */
	public int getPosistionY() {
		return posistionY;
	}

	/**
	 * ensures: that Position in Y is set to posistionY
	 * 
	 * @param posistionY
	 */
	public void setPosistionY(int posistionY) {
		this.posistionY = posistionY;
	}

	/**
	 * ensures: that the maxSpeed is returned
	 * 
	 * @return maxSpeed
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * ensures: that MaxSpeed is set to maxSpeed
	 * 
	 * @param maxSpeed
	 */
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * ensures: that the jumpStregnth is returned
	 * 
	 * @return jumpStregnth
	 */
	public int getJumpStregnth() {
		return jumpStregnth;
	}

	/**
	 * ensures: that JumpStregnth is set to jumpStregnth
	 * 
	 * @param jumpStregnth
	 */
	public void setJumpStregnth(int jumpStregnth) {
		this.jumpStregnth = jumpStregnth;
	}

	public abstract void drawOn(Graphics2D g2);

	public Character() {

	}

	/**
	 * ensures: that the height is returned
	 * 
	 * @return height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * ensures: that the width is returned
	 * 
	 * @return width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * initializes all aspects of character and it's associated movement.
	 * 
	 * @param xPos
	 * @param yPos
	 * @param facingDirection
	 * @param maximumVerticalSpeed
	 * @param maxSpeed
	 * @param jumpStregnth
	 * @param hAcceleration
	 * @param vAcceleration
	 * @param width
	 * @param height
	 */
	public Character(int xPos, int yPos, int facingDirection, int maximumVerticalSpeed, int maxSpeed, int jumpStregnth,
			int hAcceleration, int vAcceleration, int width, int height) {
		this.facingDirection = facingDirection;
		this.posistionX = xPos;
		this.posistionY = yPos;
		this.maximumVerticalSpeed = maximumVerticalSpeed;
		this.maxSpeed = maxSpeed;
		this.jumpStregnth = jumpStregnth;
		this.isGoingLeft = false;
		this.isGoingRight = false;
		this.isGoingUp = false;
		this.hAcceleration = hAcceleration;
		this.vAcceleration = vAcceleration;
		this.shouldRemove = false;
		this.width = width;
		this.height = height;
	}

	/**
	 * checks if character is moving left
	 * 
	 * @return true/false
	 */
	public boolean isGoingLeft() {
		return isGoingLeft;
	}

	/**
	 * sets to either true or false
	 * 
	 * @param isGoingLeft
	 */
	public void setGoingLeft(boolean isGoingLeft) {
		this.isGoingLeft = isGoingLeft;
	}

	/**
	 * checks if character is moving right
	 * 
	 * @return true/false
	 */
	public boolean isGoingRight() {
		return isGoingRight;
	}

	/**
	 * sets to either true or false
	 * 
	 * @param isGoingRight
	 */
	public void setGoingRight(boolean isGoingRight) {
		this.isGoingRight = isGoingRight;
	}

	/**
	 * checks if character is moving up
	 * 
	 * @return true/false
	 */
	public boolean isGoingUp() {
		return isGoingUp;
	}

	/**
	 * sets to either true or false
	 * 
	 * @param isGoingUp
	 */
	public void setGoingUp(boolean isGoingUp) {
		this.isGoingUp = isGoingUp;
	}

	/**
	 * checks if character is moving down
	 * 
	 * @return true/false
	 */
	public boolean isGoingDown() {
		return isGoingDown;
	}

	/**
	 * sets to either true or false
	 * 
	 * @param isGoingDown
	 */
	public void setGoingDown(boolean isGoingDown) {
		this.isGoingDown = isGoingDown;
	}

	/**
	 * checks if character needs to be removed
	 * 
	 * @return true/false
	 */
	public boolean shouldRemove() {
		return this.shouldRemove;
	} // shouldRemove

	/**
	 * marks if character needs to be removed
	 */
	public void markToRemove() {
		this.shouldRemove = true;
	} // markToRemove

	/**
	 * updates the hero's velocity for every tick
	 * 
	 * @param level
	 */
	public void update(Level level) {
		if (isGoingLeft())
			this.velocityX -= this.hAcceleration;
		if (isGoingRight())
			this.velocityX += this.hAcceleration;
		if (isGoingUp())
			this.velocityY -= this.vAcceleration;
		if (isGoingDown())
			this.velocityY += .5 * this.vAcceleration;
		this.setPosistionX(this.getPosistionX() + this.getVelocityX());
		this.setPosistionY(this.getPosistionY() + this.getVelocityY());
		if (this.getPosistionY() <= 0) {
			this.setPosistionY(0);
			this.setVelocityY(0);
		}
		if (!(this.getPosistionY() >= SCREEN_SIZE_Y - this.getImage().getHeight(null))) {
			this.addVelocityY(level.getGravity());
		}
		if (this.getPosistionX() > SCREEN_SIZE_X)
			this.posistionX -= SCREEN_SIZE_X;
		if (this.getPosistionX() < 0 - this.getImage().getWidth(null))
			this.posistionX += SCREEN_SIZE_X + this.getImage().getWidth(null);
		if (this.getPosistionY() > SCREEN_SIZE_Y - 3 * this.getImage().getHeight(null)) {
			this.setVelocityY(0);
			this.setPosistionY(SCREEN_SIZE_Y - 3 * this.getImage().getHeight(null));
		}
		if (this.velocityX > this.getMaxSpeed())
			this.setVelocityX(this.getMaxSpeed());
		if (this.velocityX < -this.getMaxSpeed())
			this.setVelocityX(-this.getMaxSpeed());
		if (this.velocityY > this.getMaximumVerticalSpeed())
			this.setVelocityY(this.getMaximumVerticalSpeed());
		if (this.velocityY < -this.getMaximumVerticalSpeed())
			this.setVelocityY(-this.getMaximumVerticalSpeed());
	}

	/**
	 * ensures: that maximumVerticalSpeed is returned
	 * 
	 * @return maximumVerticalSpeed
	 */
	public int getMaximumVerticalSpeed() {
		return maximumVerticalSpeed;
	}

	/**
	 * ensures: that the maximum vertical speed is set to maximumVerticalSpeed
	 * 
	 * @param maximumVerticalSpeed
	 */
	public void setMaximumVerticalSpeed(int maximumVerticalSpeed) {
		this.maximumVerticalSpeed = maximumVerticalSpeed;
	}

	/**
	 * ensures: that the bounding box is returned
	 * 
	 * @return bounding box
	 */
	public Rectangle2D getBoundingBox() {
		return new Rectangle2D.Double(this.getPosistionX(), this.getPosistionY(), width, height);
	}

}
