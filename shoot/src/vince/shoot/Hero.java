package vince.shoot;
import java.awt.image.BufferedImage;


public class Hero extends FlyingObject{
	private int life;
	private int doubleFire;
	private BufferedImage[] images = {};
	private int index;
	
	public Hero() {
		// TODO Auto-generated constructor stub
		image = ShootGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 200;
		y = 700;
		doubleFire = 0;
		life = 3;
		images = new BufferedImage[] {ShootGame.hero0,ShootGame.hero1};
		index = 0;
	}
	
	public void step() {
		index++;	
		image = images[(index / 15) % images.length];
	}
	
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 10;
		if(doubleFire > 0) {
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+xStep*1,this.y - yStep);
			bs[1] = new Bullet(this.x+xStep*3,this.y - yStep);
			doubleFire -= 2;
			return bs;
		}else {
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x+xStep*2,this.y);
			return bs;
		}
		
	}

	public void moveTo(int x,int y) {
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}

	public boolean outOfBounds() {
		return false;
	}

	public void addLife() {
		life++;
	}
	
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	
	public void minusLife(){
		life--;
	}
	
	public int getLife() {
		return this.life;
	}
	
	public boolean hit(FlyingObject other) {
		int x1 = other.x - this.width/2;
		int x2 = other.x + other.width +this.width/2;
		int y1 = other.y - this.height/2;
		int y2 = other.y + other.height+ this.height/2;
		int x = this.x + this.width/2;
		int y = this.y + this.height/2;
		return x >= x1 && x <= x2 && y >= y1  && y<= y2;
	}
	public void doubleFire() {
		doubleFire += 40;
	}
	
	public void slowDown() {
	}
}
