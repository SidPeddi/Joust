package mainApp;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Class: Enemy
 * 
 * @author Tulsi Manohar, Siddarth Peddi Purpose: Enemies to eliminate hero
 *         Restrictions: None
 *
 */
public class Enemy extends Character {
	private int score;
	public static final int SIZE = 50;
	private String file = "src/images/enemy1.png";
	private boolean tracks;
	private Image image = null;

	/**
	 * ensures: Gets the point value of each enemy
	 * 
	 * @return score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * ensures: Sets the point value of each enemy
	 * 
	 * @param score
	 */

	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * 
	 * @param tracks ensures: sets the tracking of the enemy to the boolean of
	 *               tracks
	 * 
	 */
	public void setTracking(boolean tracks) {
		this.tracks = tracks;
	}

	/**
	 * ensures: that the tracking of the enemy is returned
	 * 
	 * @return boolean of the type of enemy
	 */
	public boolean tracking() {
		return tracks;
	}

	/**
	 * Ensures: Enemy is initialized
	 * 
	 * @param x
	 * @param y
	 * @param tracking
	 */

	public Enemy(int x, int y, boolean tracking) {
		super(x * GRID_CONSTANT_X, y * GRID_CONSTANT_Y, 0, 10, 5, 10, 2, 4, 50, 34);
		if (tracking) {
			file = "src/images/enemy1.png";
			setTracking(true);
		} else {
			file = "src/images/enemy2.png";
			setTracking(false);
		}

		try {
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setImage(image);

	}

	/**
	 * Draws the enemy on the screen
	 */

	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.drawImage(getImage(), this.getPosistionX(), this.getPosistionY(), null);

	} // drawOn

}
