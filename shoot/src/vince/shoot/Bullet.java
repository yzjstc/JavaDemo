package vince.shoot;

public class Bullet extends FlyingObject {
	private int speed = 3;
	public Bullet(int x ,int y) {
		// TODO Auto-generated constructor stub
		image = ShootGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
	}
	public void step() {
		y -= speed;	
	}
	
	public boolean outOfBounds() {
		return this.y <= -this.height;
	}
	@Override
	public void slowDown() {
		// TODO Auto-generated method stub	
	}
	
}
