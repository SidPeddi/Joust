package mainApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Class: Level
 * 
 * @author Tulsi Manohar, Siddarth Peddi Purpose: Updates game for every tick
 *         and handles all collisions Restrictions: None
 *
 */
public class Level extends JComponent {
	private static final int TOTAL_LINES = 5;
	private static final int GRID_CONSTANT_X = 100;
	private static final int GRID_CONSTANT_Y = 200;
	private int gravity, total, heroLives, heroScore, playerHighScore;
	private String levelName;
	private Clip backgroundMusic;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private ArrayList<Lava> lavas = new ArrayList<Lava>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Egg> eggs = new ArrayList<Egg>();
	private ArrayList<Egg> eggsToRemove = new ArrayList<>();
	private ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
	private ArrayList<Lava> lavaToRemove = new ArrayList<>();
	private String filePathScore, lives, score, filePathLives, filePathHighScore, highScore;
	private MainApp main = new MainApp();
	Hero hero;

	/**
	 * ensures: that the gravity is returned
	 * 
	 * @return gravity
	 */
	public int getGravity() {
		return gravity;
	}

	/**
	 * initializes gravity
	 * 
	 * @param gravity
	 */
	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	/**
	 * ensures: that the level name is returned
	 * 
	 * @return levelName
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * initializes level name
	 * 
	 * @param levelName
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * ensures: that the array list of platforms is returned
	 * 
	 * @return platforms
	 */
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}

	/**
	 * adds platform when needs to the array list of platforms
	 * 
	 * @param platform
	 */
	public void addPlatforms(Platform platform) {
		this.platforms.add(platform);
	}

	/**
	 * ensures: that the array list of lava is returned
	 * 
	 * @return lavas
	 */
	public ArrayList<Lava> getLavas() {
		return lavas;
	}

	/**
	 * adds lava when needs to the array list of lavas
	 * 
	 * @param lava
	 */
	public void addLavas(Lava lava) {
		this.lavas.add(lava);
	}

	/**
	 * ensures: that the array list of enemies is returned
	 * 
	 * @return enemies
	 */
	public ArrayList<Enemy> getEnemies() {
		return this.enemies;
	}

	/**
	 * adds enemy when needs to the array list of enemies
	 * 
	 * @param enemy
	 */
	public void addEnemies(Enemy enemy) {
		this.enemies.add(enemy);
	}

	/**
	 * intializes enemies array list.
	 * 
	 * @param enemies
	 */
	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	/**
	 * ensures: that the array list of eggs is returned
	 * 
	 * @return eggs
	 */
	public ArrayList<Egg> getEggs() {
		return eggs;
	}

	/**
	 * adds egg to array list of eggs
	 * 
	 * @param egg
	 */
	public void addEggs(Egg egg) {
		eggs.add(egg);
	}

	/**
	 * ensures: that the hero is returned
	 * 
	 * @return hero
	 */
	public Hero getHero() {
		return this.hero;
	}

	/**
	 * Initializes hero.
	 * 
	 * @param hero
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/**
	 * writes most recent score and number of lives to the textfiles associated with
	 * the values
	 */
	public void writeToFile() {
		filePathLives = "levels/Lives.txt";
		filePathScore = "levels/Score.txt";
		filePathHighScore = "levels/INFO.txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathScore))) {
			score = Integer.toString(hero.getScore());
			writer.write(score);

		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathLives))) {
			lives = Integer.toString(hero.getLives());
			writer.write(lives);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * gets high score by reading from the textfile.
	 */
	public void getHighScore() {
		filePathHighScore = "levels/INFO.txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(filePathHighScore))) {
			String highScore;
			while ((highScore = reader.readLine()) != null)
				playerHighScore = Integer.valueOf(highScore);
			if (playerHighScore < hero.getScore()) {
				playerHighScore = hero.getScore();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathHighScore))) {
			highScore = Integer.toString(playerHighScore);
			writer.write(highScore);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * reads from scores/lives file to obtain current values for the same.
	 */
	public void readFromFile() {
		filePathLives = "levels/Lives.txt";
		filePathScore = "levels/Score.txt";

		try (BufferedReader reader = new BufferedReader(new FileReader(filePathScore))) {
			String score;
			while ((score = reader.readLine()) != null)
				heroScore = Integer.valueOf(score);
			hero.setScore(heroScore);

		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(filePathLives))) {
			String lives;
			while ((lives = reader.readLine()) != null)
				heroLives = Integer.valueOf(lives);
			hero.setLives(heroLives);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * initializes array lists for platforms, enemies and lava for the current
	 * level.
	 */
	public Level() {
		this.platforms = new ArrayList<Platform>();
		this.enemies = new ArrayList<Enemy>();
		this.lavas = new ArrayList<Lava>();
		System.out.println("making a level");
	}

	/**
	 * initializes other parameters such as gravity
	 * 
	 * @param otherParams
	 */
	public void setOtherParam(ArrayList<Integer> otherParams) {
		this.setGravity(otherParams.get(0));
	}

	/**
	 * paints the required platforms, enemies and lava onto the frame
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		JLayeredPane pane = new JLayeredPane();
		pane.setPreferredSize(new Dimension(400, 300));
		JPanel bgPanel = new JPanel();
		bgPanel.setBounds(0, 0, 400, 300);
		pane.add(bgPanel, JLayeredPane.DEFAULT_LAYER);
		ImageIcon bgImage = new ImageIcon("src/images/BG.jpg");
		Image bgImg = bgImage.getImage();
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);

		MainApp.frame.add(pane);

		hero.drawOn((Graphics2D) g2);
		for (Platform platform : platforms) {
			platform.drawOn((Graphics2D) g2);
		}
		for (Enemy enemy : enemies) {
			enemy.drawOn((Graphics2D) g2);
		}
		for (Egg egg : eggs) {
			egg.drawOn((Graphics2D) g2);
		}
		for (Lava lava : lavas) {
			lava.drawOn((Graphics2D) g2);
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Consolas", Font.PLAIN, 20));
		g.drawString("Score: " + String.valueOf(hero.getScore()), 30, 30);
		g.drawString("Lives: " + String.valueOf(hero.getLives()), 30, 50);
		g.setColor(Color.RED);
		g.drawString("HighScore " + String.valueOf(highScore), 30, 70);

	}

	/**
	 * updates game objects such as enemies andeggs based on hero movement.
	 */
	public void updateGameObjects() {

		for (Enemy enemy : enemies) {
			if (enemy.shouldRemove()) {
				Egg newEgg = new Egg(enemy.getPosistionX(), enemy.getPosistionY() + 50, enemy.tracking());
				addEggs(newEgg);
				enemiesToRemove.add(enemy);
			}
		}

		for (Egg egg : eggs) {
			if (egg.aliveTime() > 2) {
				Enemy removed = new Enemy(egg.getPosistionX() / GRID_CONSTANT_X, egg.getPosistionY() / GRID_CONSTANT_Y,
						egg.tracking());
				enemies.add(removed);
				eggsToRemove.add(egg);

			}
			if (egg.shouldRemove()) {
				eggsToRemove.add(egg);
			}

		}
		enemies.removeAll(enemiesToRemove);
		eggs.removeAll(eggsToRemove);
		if (enemies.size() == 0 && total == 0) {
			total++;
			writeToFile();
			main.increaseLevel();
		}
	}

	/**
	 * updates the game for every tick
	 */
	public void update() {
		getHighScore();
		updateGameObjects();
		if (hero.getLives() <= 0 && total == 0) {
			total++;
			main.endGame();
		}

		hero.update(this);
		handleCollisions();

		for (Enemy enemy : enemies) {
			if (enemy.tracking()) {
				double heroXPos = hero.getPosistionX();
				double heroYPos = hero.getPosistionY();
				double targetXPos = (heroXPos - enemy.getPosistionX()) / Math.abs((heroXPos - enemy.getPosistionX()));
				double targetYPos = (heroYPos - enemy.getPosistionY()) / Math.abs((heroYPos - enemy.getPosistionY()));
				enemy.setVelocityX(targetXPos);
				enemy.setVelocityY(targetYPos);

			} else {

				Random r = new Random();
				double random = r.nextInt(2);
				if (random == 1) {

					enemy.setGoingDown(true);
					enemy.setGoingUp(false);

				} else {

					enemy.setGoingUp(true);
					enemy.setGoingDown(false);

				}

				double random1 = r.nextInt(4);
				if (random1 == 1) {

					enemy.setGoingRight(true);
					enemy.setGoingLeft(false);

				} else {

					enemy.setGoingLeft(true);
					enemy.setGoingRight(false);

				}

			}
			enemy.update(this);
		}
		for (Egg egg : eggs) {
			egg.update(this);

		}
	}

	/**
	 * redraws the level for every tick
	 */
	public void drawLevel() {
		this.repaint();
	}

	/**
	 * plays collision music when collision is detected between lava and egg, hero
	 * and lava, hero and egg and hero and enemies
	 * 
	 * @param filepath
	 */
	public static void playCollisionMusic(String filepath) {

		Clip hitNoise;
		try {
			hitNoise = AudioSystem.getClip();
			hitNoise.open(AudioSystem.getAudioInputStream(new File(filepath)));
			hitNoise.start();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
			e.printStackTrace();
		}
	} // playCollisionMusic

	/**
	 * Ensures: sound is played when hero collects egg
	 * 
	 * @param filepath
	 */
	public static void playEggMusic(String filepath) {

		Clip powerUp;
		try {
			powerUp = AudioSystem.getClip();
			powerUp.open(AudioSystem.getAudioInputStream(new File(filepath)));
			powerUp.start();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
			e.printStackTrace();
		}
	} // playEggMusic

	/**
	 * Ensures: background music is played
	 * 
	 * @param filepath
	 */
	public void playMusic(String filepath) {
		Clip music;
		try {
			music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(new File(filepath)));
			music.loop(Clip.LOOP_CONTINUOUSLY);
			FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(20f * (float) Math.log10(.15));
			music.start();
			this.backgroundMusic = music;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
			e.printStackTrace();
		}
	} // playMusic

	/**
	 * reads files and ensures that it follows constraints to make level.
	 * 
	 * @param fileName
	 * @throws IncorrectNumberOfLinesException
	 */
	public void readFile(String fileName) throws IncorrectNumberOfLinesException {

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("level filetype is incorrect");
			System.exit(1);
		}

		this.setLevelName(scanner.nextLine());
		ArrayList<Integer> otherParams = new ArrayList<Integer>();
		while (scanner.hasNextInt())
			otherParams.add(scanner.nextInt());
		this.setOtherParam(otherParams);

		int lineNumber = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			this.importStuff(line, lineNumber);
			lineNumber++;
		}

		if (lineNumber != TOTAL_LINES) {
			throw new IncorrectNumberOfLinesException(TOTAL_LINES, lineNumber);
		}
		scanner.close();
	}

	/**
	 * ensures: Reads level files and spawn in appropriate game objects for each
	 * level
	 * 
	 * @param line
	 * @param lineNumber
	 */

	public void importStuff(String line, int lineNumber) {
		playMusic("src/images/background_music.wav");
		for (int i = 0; i < line.length(); i++) {

			if (line.charAt(i) == 'p') {
				Platform newPlatform = new Platform((i * Character.GRID_CONSTANT_X),
						(lineNumber * Character.GRID_CONSTANT_Y));
				this.addPlatforms(newPlatform);

			}
			if (line.charAt(i) == 'l') {
				Lava newLava = new Lava((i * Character.GRID_CONSTANT_X), (899));
				this.addLavas(newLava);

			}
			if (line.charAt(i) == 'E') {
				Enemy newEnemy = new Enemy(i, lineNumber, false);
				this.addEnemies(newEnemy);

			}
			if (line.charAt(i) == 'T') {
				Enemy newEnemy = new Enemy(i, lineNumber, true);
				this.addEnemies(newEnemy);

			}
			if (line.charAt(i) == 'H') {
				try {
					Hero newHero = new Hero(i, lineNumber);
					this.hero = newHero;
				} catch (IOException e) {
					System.err.println("Hero init. failed, try different file name.");
				}
			}
		}
	}

	/**
	 * ensures: Checks collisions between all game objects and calls appropriate
	 * functions to deal with the effects of the collisions
	 */

	public void handleCollisions() {
		for (Platform platform : platforms) {
			if (platform.overlaps(hero)) {
				platform.colldeWith(hero);

			}

			for (Enemy enemy : enemies) {
				if (platform.overlaps(enemy)) {
					platform.colldeWith(enemy);
				}
				if (hero.overlaps(enemy)) {
					hero.colldeWith(enemy);

					playCollisionMusic("src/images/crash.wav");
				}

			}
			for (Egg egg : eggs) {
				if (platform.overlaps(egg)) {
					platform.colldeWith(egg);
				}
				if (hero.overlaps(egg)) {
					hero.colldeWith(egg);

					playCollisionMusic("src/images/crash.wav");
				}

			}
		}
		for (Lava lava : lavas) {
			if (lava.overlaps(hero)) {
				lava.colldeWith(hero);
				playCollisionMusic("src/images/crash.wav");
			}

			for (Egg egg : eggs) {
				if (lava.overlaps(egg)) {
					lava.colldeWith(egg);
					playCollisionMusic("src/images/crash.wav");

				}

			}
		}
	}
}
