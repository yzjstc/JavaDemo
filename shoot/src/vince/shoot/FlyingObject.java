package vince.shoot;
import java.awt.image.BufferedImage;
//该类可以获得图片的getWidth();getHeight();方法

public abstract class FlyingObject {
	protected BufferedImage  image;
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	public abstract void step();
	public abstract boolean outOfBounds();
	public abstract void slowDown();
	
	public boolean shootBy(Bullet bullet) {
//		int x1 = this.x;
//		int x2 = this.x + this.width;
//		int y1 = this.y;
//		int y2 = this.y + this.height;
//		int x = bullet.x;
//		int y = bullet.x;
//		return x >= x1 && x <= x2 && y >= y1  && y<= y2;
		
		return ((this.y+this.height) >= bullet.y && (this.x+this.width/8) <= bullet.x && (this.x+7*this.width/8) >= bullet.x);
	}
}
