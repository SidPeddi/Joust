package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

/**
 * Class: Lava
 * 
 * @author Tulsi Manohar, Siddarth Peddi Purpose: Creates Lava on floor of level
 *         Restrictions: None
 *
 */
public class Lava {
	private static final Color LAVA_COLOR = Color.orange;
	private int lavawidth, lavaheight, xPos, yPos;
	private Hero hero;

	/**
	 * ensures: Initialize lava
	 * 
	 * @param xPos  x coordinate of lava
	 * @param yPos  y coordinate of lava
	 * @param level
	 */
	public Lava(int x, int y) {
		this.xPos = x;
		this.yPos = y - 34;
		this.lavawidth = Character.GRID_CONSTANT_Y;
		this.lavaheight = Character.GRID_CONSTANT_X;
	} // Lava

	/**
	 * Draws the lava on the screen
	 * 
	 * @param g2
	 */
	public void drawOn(Graphics2D g2) {
		Rectangle2D rect = new Rectangle2D.Double(this.xPos, this.yPos, this.lavawidth, this.lavaheight);
		g2.setColor(LAVA_COLOR);
		g2.fill(rect);
		g2.draw(rect);

	} // drawOn

	/**
	 * ensures: Checks if the lava is intersecting the bounding boxes of hero or egg
	 * 
	 * @param character
	 * @return boolean
	 */

	public boolean overlaps(Character character) {
		return this.getBoundingBox().intersects(character.getBoundingBox());
	}

	/**
	 * ensures: Creates bounding box for collisions with lava
	 * 
	 * @return
	 */

	private RectangularShape getBoundingBox() {
		return new Rectangle2D.Double(this.xPos, this.yPos, this.lavawidth, this.lavaheight);
	}

	/**
	 * ensures: Effect of Hero colliding with lava causing Hero to lose a life and
	 * respawn
	 * 
	 * @param hero
	 */

	public void colldeWith(Hero hero) {
		hero.setLives(hero.getLives() - 1);
		hero.respawn();
	}

	/**
	 * ensures: Effect of Egg colliding with lava causing egg to be destroyed
	 * 
	 * @param egg
	 */
	public void colldeWith(Egg egg) {
		egg.markToRemove();
	}

} // Lava
