package vince.shoot;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;

import javax.imageio.ImageIO;
//�������ͨ��ImageIO�ṩ��Image.read(��Դ)�ķ�����ȡͼƬ
//��Դ��λ��������ں���λ��ͬһ�ļ����У�����ͨ��(����.class.getResource("��Դλ��"))����ȡ��Դ
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class ShootGame extends JPanel {//�̳���һ��֮���Ժ����һ�����
	public static final int WIDTH = 485;
	public static final int HEIGHT = 880;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;
	private int numOfAirPlane = 0;
	
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage bullet;
	
	//��̬��ʼ���飬�ڳ������ʱ���Զ����г�ʼ������ִֻ��һ��
	static {
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Hero hero = new Hero();
	private FlyingObject[] flyings = {};
	private Bullet[] bullets = {};
	
	//�����볡����
	int flyEnteredIndex = 0;
	int timeMultable = 40;
	int countOfHungred = 1;
	public void enterAction() {
		if(score > countOfHungred*200 && timeMultable >10 ) {
			timeMultable -= 3;
			countOfHungred++;
		}
		flyEnteredIndex++;
		if(flyEnteredIndex % timeMultable == 0) {
			FlyingObject obj = nextOne();
			//����
			flyings = Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1] = obj;
		}
	}
	
	int shootIndex = 0;
	public void shootAction(){
		shootIndex++;
		if(shootIndex % 16 == 0) {
			Bullet[] bs = hero.shoot();
			int bsLength = bs.length;
			bullets = Arrays.copyOf(bullets, bsLength+bullets.length);
			System.arraycopy(bs, 0,bullets ,bullets.length-bs.length,bs.length);
		}
	}
	
	public void stepAction(){
		hero.step();
		for(int i = 0; i < flyings.length; i++) {
			flyings[i].step();
		}
		for(int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}
	}
	
	public void outOfBoundsAction() {//10������һ��
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		int index = 0;
		for(int i = 0; i < flyings.length;i++) {
			FlyingObject f = flyings[i];
			if(!f.outOfBounds()) {
				flyingLives[index] = f;
				index++;
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);
		
		Bullet[] bulletLives = new Bullet[bullets.length];
		index = 0;
		for(int i = 0; i < bullets.length;i++) {
			Bullet f = bullets[i];
			if(!f.outOfBounds()) {
				bulletLives[index] = f;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);
	}

	public void bangAction(){
		for(int i = 0 ;i < bullets.length; i++) {
			Bullet b = bullets[i];
			boolean bangOrNot = bang(b);
			if(bangOrNot) {
				bullets[i] = bullets[bullets.length-1];
				bullets[bullets.length-1] = b;
				bullets = Arrays.copyOf(bullets, bullets.length - 1);
				i--;
			}
		}
	}
	int score = 0 ;
	public boolean bang(Bullet b){
		int index = -1;
		boolean flag = false;
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if(f.shootBy(b)){
				index = i;
				flag = true;
				break;
			}
		}
		if( index != -1) {//ײ����
			FlyingObject one = flyings[index];
			if(one instanceof Award) {
				Award a = (Award)one;
				switch(a.getType()) {
				case Award.DOUBLE_FIRE:
					hero.doubleFire();
					break;
				case Award.LIFE:
					hero.addLife();
					break;
				case Award.SLOWDOWN:
					for(int i = 0 ; i<flyings.length; i++) {
						flyings[i].slowDown();
					}
					numOfAirPlane = 10;
					break;
				}
			}
			if(one instanceof Enemy) {
				Enemy e =(Enemy)one;
				score += e.getScore();
			}
			FlyingObject t = flyings[index];
			flyings[index] = flyings[flyings.length-1];
			flyings[flyings.length-1] = t;
			flyings = Arrays.copyOf(flyings, flyings.length-1);
		}
		return flag;
	}
	
	public void hitAction(){
		for (int i = 0 ; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if(hero.hit(f)) {
				hero.minusLife();
				hero.clearDoubleFire();
				FlyingObject t = flyings[i];
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length-1);
				i--;
			}
			
		}
	}
	
	public void checkGameOverAction(){
		if(hero.getLife() <= 0) {
			state = GAME_OVER;
		}
	}
	
	/*��ʱ��
	 * �����볡
	 * �ӵ��볡
	 * �����ӵ��ƶ�
	 * �ӵ��������
	 * Ӣ�ۻ��͵�����ײ
	 */
	public void action() {
		MouseAdapter l = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if(state == RUNNING) {
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			public void mouseClicked(MouseEvent e) {
				switch(state){
					case START:
						state = RUNNING;
						break;
					case GAME_OVER:
						score = 0;
						hero = new Hero();
						flyings = new FlyingObject[0];
						bullets = new Bullet[0];
						state = START;
						break;
				}
			}
			public void mouseExited(MouseEvent e) {
				if(state == RUNNING) {
					state = PAUSE;
				}
			}
			public void mouseEntered(MouseEvent e) {
				if(state == PAUSE) {
					state = RUNNING;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		Timer timer = new Timer();
		int intervel = 10;//ʱ�������Ժ���Ϊ��λ��
		/*timer.schedule(TimerTask,10,10);
		 * ��һ��10��������������һ�δ�����ʱ����
		 * �ڶ���10���������ÿ�δ�����ʱ����
		 */
		timer.schedule(new TimerTask() {
			public void run() {//ÿ��intervel������һ��run
				if (state == RUNNING) {
				enterAction();
				stepAction();
				shootAction();
				outOfBoundsAction();
				bangAction();
				hitAction();
				checkGameOverAction();
				}
				repaint();
			}
		}, 50, intervel);
		
	}
	
	public FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(50);
		FlyingObject object; 
		if (type < 2) {
			object = new Bee();
		}else {
			if(numOfAirPlane <= 0) {
				object = new AirPlane();
			}else {
				object = new AirPlane(1);
				numOfAirPlane--;
			}	
		}
		return object;
	}
	
	public void paint(Graphics g) {
		g.drawImage(background,0,0,null);
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		paintScoreAndLife(g);
		paintState(g);
	}
	
	public void paintState(Graphics g) {
		switch(state){
		case START:
			g.drawImage(start, (WIDTH-start.getWidth())/2,(HEIGHT-start.getHeight())/2,null);
			break;
		case PAUSE:
			g.drawImage(pause,(WIDTH-pause.getWidth())/2,(HEIGHT-pause.getHeight())/2,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,(WIDTH-gameover.getWidth())/2,(HEIGHT-gameover.getHeight())/2,null);
			break;
		}
		
	}
	
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	
	public void paintFlyingObjects(Graphics g) {
		for(int i = 0 ;i < flyings.length ; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}
	
	public void paintBullets(Graphics g) {
		for(int i = 0 ; i < bullets.length ; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.image, b.x, b.y, null);
		}
	}
	
	public void paintScoreAndLife(Graphics g) {
		g.setColor(new Color( 250, 128, 10));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		g.drawString("SCORE:"+score,10,25);
		g.drawString("LIFE:"+hero.getLife(),10,45);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Vince_�ɻ���ս");//����
		ShootGame game = new ShootGame();//���
		frame.add(game);//�����ӽ�ȥ����
		
		frame.setSize(WIDTH, HEIGHT);//���ڴ�С
		frame.setResizable(false);//���ò������
		frame.setAlwaysOnTop(true);//�������϶�
		//Ĭ�ϵĹرղ������ڴ��ڹر�ʱ���˳�����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//���������λ�ã�������ʾ�����û�������󶥵����
		frame.setLocationRelativeTo(null);
		Font  font =new  Font(Font.SERIF, Font.BOLD , 20);
	    UIManager.put("Label.font", font);	
		JOptionPane.showMessageDialog(null, "��ӭ��������Ϸ���r(�s���t)�q\n"
				+ "������������Ϸ��ʼ\n"
				+ "��Ϸ�������ƶ��������ɻ�\n"
				+"����뿪������Ϸ��ͣ��");  
		//���ô��ڿɼ���Ĭ�ϱ������ 2.���ø����paint()����
		frame.setVisible(true);
		
		game.action();
		}

}
