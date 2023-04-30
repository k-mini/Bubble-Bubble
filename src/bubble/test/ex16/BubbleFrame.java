package bubble.test.ex16;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BubbleFrame extends JFrame {
	
	private BubbleFrame mContext = this;
	private JLabel backgroundMap;
	private Player player;
	private Enemy enemy;

	public BubbleFrame() {
		initObject();
		initSetting();
		initListener();
		setVisible(true);
		setTitle("Bubble Bubble");
		
	}

	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap);
		player = new Player(this);
		add(player);
		enemy = new Enemy(this);
		add(enemy);
	}

	private void initSetting() {
		setSize(1000, 640);
		setLayout(null); // absolute 레이아웃 (자유롭게 그림을 그릴 수 있다)
		setLocationRelativeTo(null); // JFrame 가운데 배치하기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼으로 창을 끌 떄 JVM 같이 종료하기
	}

	private void initListener() {
		addKeyListener(new KeyAdapter() {
			
			// 키보드 클릭 이벤트 핸들러
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.getKeyCode());
				
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						if (!player.isLeft() && !player.isLeftWallCrash()) player.left();
						break;
					case KeyEvent.VK_RIGHT:
						if (!player.isRight()  && !player.isRightWallCrash()) player.right();
						break;
					case KeyEvent.VK_UP:
						if (!player.isUp() && !player.isDown() ) player.up();    // && !player.isDown()
						break;
					case KeyEvent.VK_SPACE:
						player.attack();
						break;
				}
			}
		
			// 키보드 해제 이벤트 핸들러
			@Override
			public void keyReleased(KeyEvent e) {
				//System.out.println(e.getKeyCode());
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						player.setLeft(false);
						//System.out.println("left : false");
						break;
					case KeyEvent.VK_RIGHT:
						player.setRight(false);
						//System.out.println("right : false");
						break;
				}
				
			}
		});
		
	}

	public static void main(String[] args) {
		new BubbleFrame();
		//System.out.println(System.identityHashCode(mContext2));
		
	}
}
