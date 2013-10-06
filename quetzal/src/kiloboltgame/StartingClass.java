package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import kiloboltgame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {

	enum GameState {
		Running, Dead
	}

	GameState state = GameState.Running;

	private static Robot robot;
	public static Heliboy hb, hb2;
	public static int score = 0;
	private Font font = new Font(null, Font.BOLD, 30);

	private Image image, currentSprite, character, character2, character3,
			characterDown, characterJumped, background, heliboy, heliboy2,
			heliboy3, heliboy4, heliboy5;

	public static Image tilegrassTop,tilegrassTop2, tilegrassBot, tilegrassLeft,
			tilegrassRight, tiledirt;

	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	private Animation anim, hanim;

	private ArrayList<Tile> tilearray = new ArrayList<Tile>();

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Quetzal");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		character = getImage(base, "data/Kleiren.png");
		character2 = getImage(base, "data/Kleiren2.png");
		

		characterDown = getImage(base, "data/Kleiren.png");
		characterJumped = getImage(base, "data/Kleiren.png");

		heliboy = getImage(base, "data/spider1.png");
		heliboy2 = getImage(base, "data/spider2.png");
		heliboy3 = getImage(base, "data/spider3.png");
		heliboy4 = getImage(base, "data/spider2.png");
		heliboy5 = getImage(base, "data/spider3.png");

		background = getImage(base, "");

		tiledirt = getImage(base, "data/floor2.png");
		tilegrassTop = getImage(base, "data/wallcorridor2.png");
		tilegrassTop2 = getImage(base, "data/wallcorridor22.png");
		tilegrassBot = getImage(base, "data/wallcorridor8.png");
		tilegrassLeft = getImage(base, "data/wallcorridor6.png");
		tilegrassRight = getImage(base, "data/wallcorridor4.png");

		anim = new Animation();
		anim.addFrame(character, 200);
		anim.addFrame(character2, 200);
		

		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);

		currentSprite = anim.getImage();
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		robot = new Robot();
		// Initialize Tiles
		try {
			loadMap("data/map1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		hb = new Heliboy(340, 300);
		hb2 = new Heliboy(900, 300);

		Thread thread = new Thread(this);
		thread.start();
	}

	private void loadMap(String filename) throws IOException {
		ArrayList lines = new ArrayList();
		int width = 0;
		int height = 0;

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true) {
			String line = reader.readLine();
			// no more lines to read
			if (line == null) {
				reader.close();
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());

			}
		}
		height = lines.size();

		for (int j = 0; j < 12; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {

				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}

			}
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		if (state == GameState.Running) {

		while (true) {
			robot.update();
			if (robot.isJumped()) {
				currentSprite = characterJumped;
			} else if (robot.isJumped() == false && robot.isDucked() == false) {
				currentSprite = anim.getImage();
			}

			ArrayList projectiles = robot.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				if (p.isVisible() == true) {
					p.update();
				} else {
					projectiles.remove(i);
				}
			}

			updateTiles();
			hb.update();
			hb2.update();
			bg1.update();
			bg2.update();
			animate();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			/*if (robot.getCenterY() > 500) {
				state = GameState.Dead;
			}*/
		}}
	}

	public void animate() {
		anim.update(10);
		hanim.update(10);
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {
		
		if (state == GameState.Running) {

		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		paintTiles(g);

		ArrayList projectiles = robot.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		
		
		
		g.setColor(Color.red);
		g.drawRect(robot.getCenterX(),  robot.getCenterY() , 64, 64);
		
		
		g.drawImage(anim.getImage(), robot.getCenterX() ,
				robot.getCenterY() , this);
		
	
	
		
		
		g.drawImage(hanim.getImage(), hb.getCenterX() - 48,
				hb.getCenterY() - 48, this);
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
				hb2.getCenterY() - 48, this);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(score), 740, 30);
		
	} else if (state == GameState.Dead) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 480);
		g.setColor(Color.WHITE);
		g.drawString("Dead", 360, 240);


	}
	}

	private void updateTiles() {

		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}

	}

	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		

		case KeyEvent.VK_DOWN:
			robot.moveDown();
			robot.setMovingDown(true);
			break;
			
		case KeyEvent.VK_UP:
			robot.moveUp();
			robot.setMovingUp(true);
			break;

		case KeyEvent.VK_LEFT:
			robot.moveLeft();
			robot.setMovingLeft(true);
			break;

		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			robot.setMovingRight(true);
			break;

	//	case KeyEvent.VK_SPACE:
	//		robot.jump();
	//		break;

		case KeyEvent.VK_CONTROL:
			if (robot.isDucked() == false && robot.isJumped() == false
					&& robot.isReadyToFire()) {
				robot.shoot();
				robot.setReadyToFire(false);
			}
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			robot.stopUp();
			break;

		case KeyEvent.VK_DOWN:
			robot.stopDown();
			break;

		case KeyEvent.VK_LEFT:
			robot.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			robot.stopRight();
			break;

		case KeyEvent.VK_SPACE:
			break;

		case KeyEvent.VK_CONTROL:
			robot.setReadyToFire(true);
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static Robot getRobot() {
		return robot;
	}

}