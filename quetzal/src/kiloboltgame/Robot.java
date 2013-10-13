package kiloboltgame;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Robot {

	// Constants are Here
	final int JUMPSPEED = -15;
	final int MOVESPEED = 5;

	private int centerX = 100;
	private int centerY = 100;
	private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean movingUp = false;
	private boolean movingDown = false;
	private boolean ducked = false;
	private boolean readyToFire = true;

	private int speedX = 0;
	private int speedY = 0;
	public static Rectangle rect = new Rectangle(0, 0, 0, 0);
	
	
	private Background bg1 = StartingClass.getBg1();
	private Background bg2 = StartingClass.getBg2();

	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public void update() {
	

		if (speedX < 0 && centerX >= 100) {
			centerX += speedX;
		}
	
		if (speedX == 0 || speedX < 0) {
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);

		}
		if (centerX <= 600 && speedX > 0) {
			centerX += speedX;
		}
		if (speedX > 0 && centerX > 600) {
			bg1.setSpeedX(-MOVESPEED / 5);
			bg2.setSpeedX(-MOVESPEED / 5);
		}
		
		if (speedX < 0 && centerX < 100) {
			bg1.setSpeedX(MOVESPEED / 5);
			bg2.setSpeedX(MOVESPEED / 5);
		}

		// Updates Y Position
		if (speedY < 0 && centerY >= 100) {
			centerY += speedY;
		}
	
		if (speedY == 0 || speedY < 0) {
			bg1.setSpeedY(0);
			bg2.setSpeedY(0);

		}
		if (centerY <= 350 && speedY > 0) {
			centerY += speedY;
		}
		if (speedY > 0 && centerY > 350) {
			bg1.setSpeedY(-MOVESPEED / 5);
			bg2.setSpeedY(-MOVESPEED / 5);
		}
		
		if (speedY < 0 && centerY < 100) {
			bg1.setSpeedY(MOVESPEED / 5);
			bg2.setSpeedY(MOVESPEED / 5);
		}

		// Handles Jumping

			

		if (speedY > 3){
			jumped = true;
		}

		// Prevents going beyond X coordinate of 0
	/*	if (centerX + speedX <= 60) {
			centerX = 61;
		}
*/
		rect.setRect(centerX , centerY , 64, 64);
		
	/*	rect2.setRect(rect.getX(), rect.getY() + 63, 68, 63);
		rect3.setRect(rect.getX() - 26, rect.getY()+32, 26, 20);
		rect4.setRect(rect.getX() + 68, rect.getY()+32, 26, 20);
		yellowRed.setRect(centerX - 110, centerY - 110, 180, 180);

		footleft.setRect(centerX - 50, centerY + 20, 50, 15);
		footright.setRect(centerX, centerY + 20, 50, 15);
*/

	}

	public void moveRight() {
		if (ducked == false) {
			speedX = MOVESPEED;
		}
	}

	public void moveLeft() {
		if (ducked == false) {
			speedX = -MOVESPEED;
		}
	}
	
	public void moveUp() {
		if (ducked == false) {
			speedY = -MOVESPEED;
		}
	}

	public void moveDown() {
		if (ducked == false) {
			speedY = MOVESPEED;
		}
	}

	public void stopRight() {
		setMovingRight(false);
		stop();
	}

	public void stopLeft() {
		setMovingLeft(false);
		stop();
	}
	
	public void stopUp() {
		setMovingUp(false);
		stop();
	}

	public void stopDown() {
		setMovingDown(false);
		stop();
	}

	private void stop() {
		if (isMovingRight() == false && isMovingLeft() == false) {
			speedX = 0;
		}

		if (isMovingRight() == false && isMovingLeft() == true) {
			moveLeft();
		}

		if (isMovingRight() == true && isMovingLeft() == false) {
			moveRight();
		}
		
		if (isMovingUp() == false && isMovingDown() == false) {
			speedY = 0;
		}

		if (isMovingDown() == false && isMovingUp() == true) {
			moveUp();
		}

		if (isMovingDown() == true && isMovingUp() == false) {
			moveDown();
		}

	}

/*	public void jump() {
		if (jumped == false) {
			speedY = JUMPSPEED;
			jumped = true;
		}

	}*/

	public void shoot() {
		if (readyToFire) {
			Projectile p = new Projectile(centerX, centerY );
			projectiles.add(p);
		}
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isDucked() {
		return ducked;
	}

	public void setDucked(boolean ducked) {
		this.ducked = ducked;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}
	
	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}
	
	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}

	public ArrayList getProjectiles() {
		return projectiles;
	}

	public boolean isReadyToFire() {
		return readyToFire;
	}

	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}

}
