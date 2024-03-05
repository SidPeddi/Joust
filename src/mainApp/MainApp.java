package mainApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Class: MainApp
 * 
 * @author Siddarth Peddi & Tulsi Manohar <br>
 *         Purpose: Top level class for CSSE220 Project containing main method
 *         <br>
 *         Restrictions: None
 */
public class MainApp {
	public static final int DELAY = 10;

	public static final int FRAME_HEIGHT_WIDTH = 1180;

	public static final int FINAL_SCALE = 800;

	public static JFrame frame = new JFrame();

	private ArrayList<Level> levels = new ArrayList<Level>();
	private Level currentLevel;

	private String file = "levels/level1.txt";

	/**
	 * 
	 * recognizes key presses and implements required movements.
	 *
	 */

	class KeyboardListen implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				currentLevel.hero.setGoingLeft(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				currentLevel.hero.setGoingRight(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				currentLevel.hero.setGoingUp(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				currentLevel.hero.setGoingDown(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_U) {
				increaseLevel();
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				decreaseLevel();

			}
			currentLevel.repaint();
			System.out.println();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				currentLevel.hero.setGoingLeft(false);
				currentLevel.hero.setVelocityX(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				currentLevel.hero.setGoingRight(false);
				currentLevel.hero.setVelocityX(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				currentLevel.hero.setGoingUp(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				currentLevel.hero.setGoingDown(false);
			}
		}

	}

	/**
	 * used to advance to next level when hero wins level or when 'u' key is pressed
	 */
	public void increaseLevel() {
		if (file != "levels/level3.txt") {
			if (file == "levels/level2.txt") {
				file = "levels/level3.txt";
			} else if (file == "levels/level1.txt") {
				file = "levels/level2.txt";
			}
			System.gc();
			MainApp.frame.dispose();
			runApp();
			currentLevel.readFromFile();
		} else {
			endGame();
		}
	}

	/**
	 * used to go back to previous level when hero loses level or when 'd' key is
	 * pressed
	 */
	public void decreaseLevel() {
		if (file == "levels/level3.txt") {
			file = "levels/level2.txt";
		} else if (file == "levels/level2.txt") {
			file = "levels/level1.txt";
		}
		MainApp.frame.dispose();
		System.gc();
		runApp();
		currentLevel.readFromFile();

	}

	/**
	 * creates stop screen which has buttons to either play the level again or go
	 * back to main screen when hero loses all 3 lives.
	 */
	public void endGame() {
		frame.dispose();
		JFrame frame2 = new JFrame();
		JButton endGame = new JButton("Play Again");
		endGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runApp();
				frame2.dispose();
			}
		});
		JButton mainMenu = new JButton("Main Menu");
		mainMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
				frame2.dispose();
			}
		});
		endGame.setBackground(Color.RED);
		endGame.setForeground(Color.WHITE);
		mainMenu.setBackground(Color.RED);
		mainMenu.setForeground(Color.WHITE);
		JPanel panel = new JPanel();
		panel.add(endGame);
		panel.add(mainMenu);
		panel.setLayout(null);
		endGame.setBounds(340, 460, 100, 35);
		mainMenu.setBounds(460, 460, 100, 35);

		frame2.setTitle("GAME OVER");
		frame2.setSize(1000, 1000);
		frame2.add(panel, null);
		frame2.setVisible(true);
	}

	/**
	 * creates start screen.
	 */
	public void startGame() {
		JButton startGame = new JButton("Start Game");
		startGame.setBackground(Color.BLUE);
		startGame.setForeground(Color.WHITE);
		JFrame frame1 = new JFrame();
		startGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runApp();
				frame1.dispose();
			}
		});
		frame1.setTitle("Joust");
		frame1.setSize(1000, 1000);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(startGame);
		startGame.setBounds(400, 460, 100, 35);
		frame1.add(panel, null);
		frame1.setVisible(true);
	}

	/**
	 * ensures: runs the game
	 * 
	 * 
	 */
	private void runApp() {
		final int frameWidth = 1000;
		final int frameHeight = 1000;
		frame = new JFrame();
		frame.setTitle("Arcade Game");
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		Level level = readFile(file);
		currentLevel = level;
		levels.add(level);
		frame.add(level);
		KeyboardListen keyboard = new KeyboardListen();
		frame.addKeyListener(keyboard);
		GameAdvanceListener advanceListener = new GameAdvanceListener(level);
		Timer timer = new Timer(DELAY, advanceListener);
		timer.start();
		frame.setVisible(true);
	} // runApp

	/**
	 * ensures: runs the main application
	 * 
	 * @param args unused
	 * @throws IOException
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.startGame();
	} // main

	/**
	 * read the text file. The expected format is filename on the first line, other
	 * stuff on the second line, and the rest of the level on the rest of the
	 * following lines
	 * 
	 * It should look something like this: LevelName gravity: 5, some other stuff
	 * here, the scanner is only looking for integers - - - - - - - H - - - - - - p
	 * p p - - - - - - - - - - - l l l l l l l
	 * 
	 * H being the Hero, p being platforms, and l being lava
	 * 
	 */
	private Level readFile(String filename) {

		Level level = new Level();

		try {
			level.readFile(filename);
		} catch (IncorrectNumberOfLinesException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		return level;
	}
}