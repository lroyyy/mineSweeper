package ui;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.Timer;

public class FrameShaker{

	// ���ھ����������һζ���������

	public static final int SHAKE_DISTANCE = 10;

	// ���ڻζ�һ��ѭ�����м䣬�ң��м䣬�� �м䣩����Ҫ��ʱ��(ms),

	// ���ֵԽС�� �ζ��ľ�Խ�졣

	public static final double SHAKE_CYCLE = 80;

	// �����ζ�����Ҫ��ʱ�䡣

	public static final int SHAKE_DURATION = 300;

	// ������趨Swing�೤ʱ�䣨ms�����´��ڵ�λ�á�

	public static final int SHAKE_UPDATE = 5;

	// private JDialog dialog;
	private JFrame frame;

	private Point naturalLocation;

	private long startTime;

	private Timer shakeTimer;

	@SuppressWarnings("unused")
	private final double HALF_PI = Math.PI / 2.0;

	private final double TWO_PI = Math.PI * 2.0;

	public FrameShaker(JFrame frame) {

		this.frame = frame;

	}

	public void startShake() {

		// ���洰�ڵ�ԭʼλ��

		naturalLocation = frame.getLocation();

		// ���濪ʼ��ʱ��

		startTime = System.currentTimeMillis();

		ActionListener listener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// ���㵹ĿǰΪֹ���ѵ�ʱ��

				long elapsed = System.currentTimeMillis() -

				startTime;

				// �������ǹ�ʽ��Ϊ������ʱ������ĳһʱ�̻ζ��ķ��ȣ�������A.
				double waveOffset = (elapsed % SHAKE_CYCLE) /

				SHAKE_CYCLE;

				double angle = waveOffset * TWO_PI;

				double angley = waveOffset * TWO_PI;

				int shakenX = (int) ((Math.sin(angle) *

				SHAKE_DISTANCE) +

				naturalLocation.x);

				int shakenY = (int) ((Math.sin(angley) *

				SHAKE_DISTANCE) +

				naturalLocation.y);

				frame.setLocation(shakenX, shakenY);

				frame.repaint();

				// should we stop timer?

				if (elapsed >= SHAKE_DURATION)

					stopShake();

			}

		};
		shakeTimer = new Timer(SHAKE_UPDATE, listener);
		shakeTimer.start();

	}

	public void stopShake() {

		shakeTimer.stop();

		frame.setLocation(naturalLocation);

		frame.repaint();

	}

	// ȥ����ԭʼ�����е�main

}