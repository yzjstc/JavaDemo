package vince.shoot;

import java.util.Random;

public class Bee extends FlyingObject implements Award{
	private int xSpeed = 2;
	private int ySpeed = 2;
	private int awardType;
	
	public Bee() {
		// TODO Auto-generated constructor stub
		image = ShootGame.bee;
		width = image.getWidth();
		height = image.getHeight();
	//	x = (int)(Math.random()*(ShootGame.WIDTH - this.width));
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
		y = this.height - 65;
		int awardTemp = rand.nextInt(6);
		if(awardTemp < 2) {
			awardType = awardTemp;
		}else {
			awardType = 2;
		}
	}
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub	
		return awardType;
	}
	public void step() {
		y += ySpeed;
		x += xSpeed;
		if(this.x <= 0) 
		{
			xSpeed = 1;
		}else if(x >= ShootGame.WIDTH-this.width)
		{
			xSpeed = -1;
		}
	}

	public boolean outOfBounds() {
		return this.y >= ShootGame.HEIGHT;
	}

	public void slowDown() {
		// TODO Auto-generated method stub
		this.xSpeed = 1;
		this.ySpeed = 1;
	}
	
}
