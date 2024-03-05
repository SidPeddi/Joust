package mainApp;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class: Egg
 * 
 * @author Tulsi Manohar, Siddarth Peddi Purpose: Spawn after elimination of
 *         enemy and collectable for addition of score Restrictions: None
 *
 */
public class Egg extends Character {
	private Image image = null;
	private boolean tracks;
	private long time;

	/**
	 * Ensures: initializes Egg
	 * 
	 * @param x
	 * @param y
	 * @param tracking
	 */
	public Egg(int x, int y, boolean tracking) {
		super(x, y, 0, 10, 5, 10, 2, 4, 50, 61);
		try {
			image = ImageIO.read(new File("src/images/egg.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setImage(image);
		setTracking(tracking);
		time = System.currentTimeMillis();
	}

	/**
	 * 
	 * @param tracks ensures: sets the tracking of the egg to the boolean of tracks
	 * 
	 */

	public void setTracking(boolean tracks) {
		this.tracks = tracks;
	}

	/**
	 * ensures: that the tracking of the enemy is returned
	 * 
	 * @return boolean of the type of enemy inside egg
	 */

	public boolean tracking() {
		return tracks;
	}

	/**
	 * ensures: That the time the it has been since enemy has turned into an egg
	 * 
	 * @return time since egg has spawned
	 */

	public double aliveTime() {

		return (System.currentTimeMillis() - time) / 1000.0;

	}

	/**
	 * Draws the egg on the screen
	 */

	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.drawImage(getImage(), this.getPosistionX(), this.getPosistionY(), null);
	}

}
