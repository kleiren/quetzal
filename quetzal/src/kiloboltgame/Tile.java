package kiloboltgame;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {

	private int tileX, tileY, speedX,speedY, type;
	public Image tileImage;

	private Robot robot = StartingClass.getRobot();
	private Background bg = StartingClass.getBg1();

	private Rectangle r;

	public Tile(int x, int y, int typeInt) {
		tileX = x * 64;
		tileY = y * 64;

		type = typeInt;

		r = new Rectangle();

		if (type == 5) {
			tileImage = StartingClass.tiledirt;
		} else if (type == 8) {
			tileImage = StartingClass.tilegrassBot;
		} else if (type == 4) {
			tileImage = StartingClass.tilegrassRight;

		} else if (type == 6) {
			tileImage = StartingClass.tilegrassLeft;

		} else if (type == 2) {
			int rand =(int)(Math.random() * ((2- 0) + 1));
			System.out.println (rand);
			if (rand==0){
			tileImage = StartingClass.tilegrassTop;}else{
				tileImage = StartingClass.tilegrassTop2;
			}
		} else {
			tileImage = StartingClass.tiledirt;
			type = 0;
		}

	}

		public void update() {
			speedX = bg.getSpeedX() * 5;
			speedY = bg.getSpeedY() * 5;

			tileX += speedX;
			tileY += speedY;
			r.setBounds(tileX, tileY, 64, 64);
	
			if (r.intersects(Robot.rect) && type != 0) {

				checkSideCollision(Robot.rect);
			}
	
		}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

/*	public void checkVerticalCollision(Rectangle rect) {
		if (rect.intersects(r)) {
			
		}

		if (rect.intersects(r) && type == 8) {
			robot.setJumped(false);
			robot.setSpeedY(0);
			robot.setCenterY(tileY - 64);
		}
	}*/

	public void checkSideCollision(Rectangle rect) {
	
			if ((type==8) &&rect.intersects(r)) {
				
	
				robot.setSpeedX(0);
				
				robot.setSpeedY(0);
				robot.setCenterY(tileY - 64);
	
			}
			
			if ((type==2)&& rect.intersects(r)) {
				robot.setCenterY(tileY + 64);
	
				robot.setSpeedX(0);
				robot.setSpeedY(0);
			}
			
			
			if (type==6 && rect.intersects(r)) {
				robot.setCenterX(tileX + 64);
	
				robot.setSpeedX(0);
				robot.setSpeedY(0);
			}
			if (type==4 && rect.intersects(r)) {
				robot.setCenterX(tileX - 64);
	
				robot.setSpeedX(0);
				robot.setSpeedY(0);
			}
		
		
	}

}