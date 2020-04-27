package element;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * 分数
 * @author zhengfei.fjhg
 * 
 */
public class Score {

//	/**编号*/
//	private int id;
	/**级别*/
	private int rank;
	/**玩家名*/
	private String playerName;
	/**耗时*/
	private int consumingTime;
	/**日期*/
	private Date date;

	public static ArrayList<Score> sort(ArrayList<Score> scores){
		Collections.sort(scores,new ScoreComparator());
		return scores;		
	}
	
	/**
	 * @param rank
	 * @param playerName
	 * @param consumingTime
	 * @param date
	 */
	public Score(int rank, String playerName, int consumingTime,
			Date date) {
		super();
		this.rank = rank;
		this.playerName = playerName;
		this.consumingTime = consumingTime;
		this.date = date;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		return rank+" "+playerName+" "+consumingTime+" "+dateFormat.format(date);
	}

	/**
	 * @return rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @return playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @return consumingTime
	 */
	public int getConsumingTime() {
		return consumingTime;
	}

	/**
	 * @return date
	 */
	public Date getDate() {
		return date;
	}
	
	public String getDateString(){
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		return dateFormat.format(date);
	}

}
