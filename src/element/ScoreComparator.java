package element;

import java.util.Comparator;

/**
 * @author zhengfei.fjhg
 *         <p>
 *         排序规则：<br>
 *         达成时间相同，耗时小的大
 */
public class ScoreComparator implements Comparator<Score> {
	@Override
	public int compare(Score s1, Score s2) {
		if (s1.getConsumingTime() < s2.getConsumingTime()) {
			return -1;
		} else if (s1.getConsumingTime() > s2.getConsumingTime()) {
			return 1;
		} else {
			if (s1.getDate().compareTo(s2.getDate()) < 0) {
				return -1;
			} else if (s1.getDate().compareTo(s2.getDate()) > 0) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
