package vince.shoot;

import java.util.Random;

public class AirPlane extends FlyingObject implements Enemy {
	private int speed ;
	
	public AirPlane(){
		Random rand = new Random();
		this.speed =  rand.nextInt(5)+2;
		image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		x = (int)(Math.random()*(ShootGame.WIDTH - this.width));
		
//		x = rand.nextInt(ShootGame.WIDTH - width);
		y = this.height - 60;//从background外部进入
	}
	
	public AirPlane(int speed) {
		this();
		this.speed = speed;
	}
	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public void step() {
		y += speed;
		
	}

	public boolean outOfBounds() {
		return this.y >= ShootGame.HEIGHT;
	}

	public void slowDown() {
		// TODO Auto-generated method stub
		this.speed = 1;
	}
	
}
