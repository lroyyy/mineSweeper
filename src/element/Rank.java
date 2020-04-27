/**
 * 
 */
package element;

/**
 * @author zhengfei.fjhg
 *
 */
public class Rank {

	private int id;
	private int rowCount;
	private int columnCount;
	private int mineCount;
	/**
	 * @param id
	 * @param rowCount
	 * @param columnCount
	 * @param mineCount
	 */
	public Rank(int id,int rowCount, int columnCount, int mineCount) {
		this.id=id;
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
	}
	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}
	/**
	 * @return columnCount
	 */
	public int getColumnCount() {
		return columnCount;
	}
	/**
	 * @return mineCount
	 */
	public int getMineCount() {
		return mineCount;
	}
	
	
	
}
