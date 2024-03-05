package mainApp;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Class: Hero
 * 
 * @author Tulsi Manohar, Siddarth Peddi Purpose: Creates Hero Restrictions:
 *         None
 *
 */
public class Hero extends Character {
	private int row;
	private int col;
	private int lives = 3;
	ArrayList<Egg> eggs = new ArrayList<Egg>();
	private int score = 0;

	/**
	 * Ensures: Hero is initialized
	 * 
	 * @param row
	 * @param col
	 * @throws IOException
	 */
	public Hero(int row, int col) throws IOException {
		super(row * GRID_CONSTANT_X, col * GRID_CONSTANT_Y, 0, 10, 5, 10, 2, 4, 50, 67);
		this.row = row;
		this.col = col;
		Image image = ImageIO.read(new File("src/images/hero.png"));
		this.setImage(image);
	}

	/**
	 * Draws the Hero on the screen
	 */

	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.drawImage(getImage(), this.getPosistionX(), this.getPosistionY(), null);
	}

	/**
	 * ensures: Gets the lives of the hero
	 * 
	 * @return lives of hero
	 */

	public int getLives() {
		return lives;
	}

	/**
	 * ensures Sets the lives of the Hero
	 * 
	 * @param lives
	 */

	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * ensures Sets the score of the Hero
	 * 
	 * @param score
	 */

	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * ensures: Gets the score of the hero
	 * 
	 * @return
	 */

	public int getScore() {
		return this.score;
	}

	/**
	 * ensures: Checks if the hero is intersecting the bounding boxes of enemy or
	 * egg
	 * 
	 * @param character
	 * @return boolean
	 */

	public boolean overlaps(Character character) {
		return this.getBoundingBox().intersects(character.getBoundingBox());
	}

	/**
	 * ensures: respawns Hero to original spawn point in level
	 */

	public void respawn() {
		this.setPosistionX(row * GRID_CONSTANT_X);
		this.setPosistionY(col * GRID_CONSTANT_Y);
		this.setVelocityX(0);
	}

	/**
	 * ensures: Compares Y position of enemy and hero when they collide if Hero is
	 * above enemy the enemy turns into an egg if Hero is equal to enemy the enemy
	 * and hero get thrown apart if Hero is below enemy the Hero loses a life and
	 * respawns
	 * 
	 * @param enemy
	 */

	public void colldeWith(Enemy enemy) {
		if (this.getPosistionY() < enemy.getPosistionY()) {
			enemy.markToRemove();
			eggs.add(new Egg(enemy.getPosistionX(), enemy.getPosistionY(), enemy.tracking()));

		}
		if (this.getPosistionY() > enemy.getPosistionY()) {
			setLives(getLives() - 1);
			respawn();
		}
		if (this.getPosistionY() == enemy.getPosistionY()) {
			if (this.getPosistionX() - enemy.getPosistionX() < 0) {
				this.setVelocityX(-200);
				enemy.setPosistionX(enemy.getPosistionX() + (300));
			}
			if (this.getPosistionX() - enemy.getPosistionX() > 0) {
				this.setVelocityX(200);
				enemy.setPosistionX(enemy.getPosistionX() + (-300));
			}
		}

	}

	/**
	 * Checks collision between egg and Hero and assigns point value to the egg
	 * depending on if it came from a tracking or non tracking hero
	 * 
	 * @param egg
	 */

	public void colldeWith(Egg egg) {
		egg.markToRemove();
		if (egg.tracking()) {
			score += 25;
		} else {
			score += 50;
		}
	}

}
