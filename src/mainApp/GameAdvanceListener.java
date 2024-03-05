package mainApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class: GameAdvanceListener
 * 
 * @author Tulsi Manohar, Siddarth Peddi Purpose: Implements action listener and
 *         timer that updates game per tick Restrictions: None
 *
 */
public class GameAdvanceListener implements ActionListener {

	private Level level;

	/**
	 * loads the listener to implement on current level
	 * 
	 * @param level
	 */
	public GameAdvanceListener(Level level) {
		this.level = level;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		advanceOneTick();
	}

	/**
	 * used to check for advance in ticks and calls methods that need to be
	 * implement when true
	 */
	public void advanceOneTick() {
		this.level.update();
		this.level.drawLevel();

	}
}
