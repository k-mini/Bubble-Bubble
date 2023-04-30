package bubble.test.ex17;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;



// class Player - > new 가능한 애들!! 게임에 존재할 수 있음. (추상메서드를 가질 수 없다.)
@Getter
@Setter
public class Player extends JLabel implements Moveable {
	
	private BubbleFrame mContext;
	private List<Bubble> bubbleList;
	
	// 위치 상태
	private int x;
	private int y;
	
	// 플레이어의 방향
	private PlayerWay playerWay;

	// 움직임 상태
	private volatile boolean left;
	private volatile boolean right;
	private volatile boolean up;
	private volatile boolean down;
	
	// 벽에 충돌한 상태
	private boolean leftWallCrash;
	private boolean rightWallCrash;
	
	// 플레이어 속도 상태
	private final int SPEED = 4;
	private final int JUMPSPEED = 2; // up, down
	
	private ImageIcon playerR, playerL;

	public Player(BubbleFrame mContext) {
		this.mContext = mContext;
		initObject();
		initSetting();
		initBackgroundPlayerService();
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
		bubbleList = new ArrayList<>();
	}

	private void initSetting() {
		x = 80;
		y = 535;
		
		left = false;
		right = false;
		up = false;
		down = false;
		
		leftWallCrash = false;
		rightWallCrash = false;
		
		playerWay = PlayerWay.RIGHT;
		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}
	
	public void initBackgroundPlayerService() {
		new Thread(new BackgroundPlayerService(this) ).start();
	}
	
	@Override
	public void attack() {
		new Thread(() ->{
			Bubble bubble = new Bubble(mContext);
			mContext.add(bubble);
			bubbleList.add(bubble);
			if(playerWay == PlayerWay.LEFT) {
				bubble.left();
			} else {
				bubble.right();
			}
		}).start();
	}
	
	// 이벤트 핸들러
	@Override
	public void left() {
		//System.out.println("left 쓰레드 생성");
		playerWay = PlayerWay.LEFT;
		left = true;
		//System.out.println(left);
		new Thread( () -> { 
			while(left) {
				setIcon(playerL);
				x = x - SPEED;
				setLocation(x,y);
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}).start();
		System.out.println(left);
	}

	@Override
	public void right() {
		//System.out.println("right 쓰레드 생성");
		playerWay = PlayerWay.RIGHT;
		right = true;
		//System.out.println("right : true");
		new Thread( () -> {
			while(right) {
				setIcon(playerR);
				x = x + SPEED;
				setLocation(x,y);
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}).start();
		//System.out.println("right : true");
	}
	
	// left + up , right + up
	@Override
	public synchronized void up() {
		//System.out.println("up");
		up = true;
		new Thread(()->{
			for(int i=0;i< 130/JUMPSPEED ;i++) {
				y = y - JUMPSPEED;
				setLocation(x,y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			up = false;
			down();
			//System.out.println("up 종료");
		}).start();
	}

	@Override
	public void down() {
		System.out.println("down");
		down = true;
		new Thread(() -> {
			while (down) {
				//System.out.println(down);
				y = y + JUMPSPEED;
				setLocation(x,y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//up = false;
			down = false;
			
		}).start();
	}
}