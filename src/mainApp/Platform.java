package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

/**
 * 
 * @author Siddarth Peddi & Tulsi Manohar Purpose: Creates platforms for enemy,
 *         egg and hero to land on in game
 *
 */
public class Platform {
	private int friction, xPos, yPos, platformHeight;
	private int platformWidth;

	/**
	 * ensures: to return value of friction
	 * 
	 * @return friction
	 */
	public int getFriction() {
		return friction;
	}

	/**
	 * initializes friction value
	 * 
	 * @param friction
	 */
	public void setFriction(int friction) {
		this.friction = friction;
	}

	/**
	 * constructor method for platform
	 * 
	 * @param x
	 * @param y
	 */
	public Platform(int x, int y) {
		this.xPos = x;
		this.yPos = y;
		this.platformWidth = Character.GRID_CONSTANT_Y;
		this.platformHeight = Character.GRID_CONSTANT_X / 2;

	}

	/**
	 * ensures: creates method to accurately draw platforms on the screen
	 * 
	 * @param g2
	 */
	public void drawOn(Graphics2D g2) {
		Rectangle2D rect = new Rectangle2D.Double(this.xPos, this.yPos, this.platformWidth, this.platformHeight);
		g2.setColor(Color.LIGHT_GRAY);
		g2.fill(rect);
		g2.draw(rect);

	} // drawOn

	/**
	 * ensures: checks for collisions of platform with all characters.
	 * 
	 * @param character
	 */
	public void colldeWith(Character character) {
		if (character.getVelocityY() > 0) {
			// object is falling, prevent passing through platform from below
			character.setVelocityY(0);
			character.setPosistionY(yPos - character.getHeight());
		} else if (character.getVelocityY() < 0) {
			// object is jumping, prevent passing through platform from above
			character.setPosistionY(yPos + platformHeight);
			character.setVelocityY(0);
		} else {
			// object is moving horizontally, prevent passing through platform from the
			// sides
			if (character.getVelocityX() > 0) {
				character.setPosistionX(xPos - character.getWidth());
			} else {
				character.setPosistionX(xPos + platformWidth);
			}
		}
	}

	/**
	 * checks for overlapping between bounding box of platform with bounding box of
	 * eggs and hero.
	 * 
	 * @param character
	 * @return
	 */
	public boolean overlaps(Character character) {
		return this.getBoundingBox().intersects(character.getBoundingBox());
	}

	/**
	 * creates bounding box for platforms.
	 */
	private RectangularShape getBoundingBox() {
		return new Rectangle2D.Double(this.xPos, this.yPos, Character.GRID_CONSTANT_Y,
				(int) (Character.GRID_CONSTANT_X) / 2);
	}

}
