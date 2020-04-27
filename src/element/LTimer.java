package element;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;


public class LTimer extends Timer {
	private int time=0;
	private boolean isRunning;
	private TimerTask timerTask;
	private JLabel Label;
	
	public LTimer(JLabel Label){
		this.Label=Label;
	}
	
	public void start(){
		timerTask = new TimerTask() {
			@Override
			public void run() {
				if (!isRunning) {
					isRunning = true;
					time = 0;
				} else {
					time++;
				}
				Label.setText("" + time);
			}
		};
		schedule(timerTask, 0, 1000);
	}
	public void pause(){
		isRunning = false;
	}
	public void stop() {
		isRunning = false;
		cancel();
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	/**
	 * @return label
	 */
	public JLabel getLabel() {
		return Label;
	}

	/**
	 * @param label ÒªÉèÖÃµÄ label
	 */
	public void setLabel(JLabel label) {
		Label = label;
	}

	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
