package element;

import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import ui.FrameMain;
import util.IniProcesser;

/**
 * 游戏管理器
 * 
 * @author zhengfei.fjhg
 * 
 */
public class GameManager {

	/** 配置路径 */
	private static String configPath = "./config.ini";
	/** 高分榜路径 */
	private static String scorePath = "./highScores.txt";
	/** 列数 */
	public static int columnCount = 16;
	/** 最大列数 */
	public static int maxColumnCount = 60;
	/** 最小列数 */
	public static int minColumnCount = 2;
	/** 行数 */
	public static int rowCount = 16;
	/** 最大行数 */
	public static int maxRowCount = 30;
	/** 最小行数 */
	public static int minRowCount = 2;
	/** 雷数 */
	public static int mineCount = 40;
	/** 最大雷数 */
	public static int maxMineCount = 999;
	/** 最小雷数 */
	public static int minMineCount = 1;
	/** 计时器 */
	public static LTimer timer;
	/** 级别 */
	public static int rank;
	/** 级别集 */
	public static ArrayList<Rank> ranks;
	/** 旧级别 */
	public static int preRank;
	/** 旗子图标索引 */
	public static int flagIconIndex;
	/** 雷图标索引 */
	public static int mineIconIndex;
	/** 主窗体 */
	public static FrameMain frameMain;
	/** 游戏是否结束 */
	public static boolean gameOver;

	public static void initGameManager(FrameMain frameMain, int rank) {
		GameManager.frameMain = frameMain;
		GameManager.rank = rank;
		initFonts();
		initGlobalFontSetting(fonts[0]);
		initRanks();
		loadSettings();
		initResources();
		setGameOver(false);
		timer = new LTimer(frameMain.getTimerLbl());
	}

	public static void loadSettings() {
		if (rank == -1) {
			setRank(Integer.parseInt(IniProcesser.GetPrivateProfileString(
					configPath, "Settings", "rank", "2")));
		}
		flagIconIndex = Integer.parseInt(IniProcesser.GetPrivateProfileString(
				configPath, "Settings", "flagIcon", "1"));
		mineIconIndex = Integer.parseInt(IniProcesser.GetPrivateProfileString(
				configPath, "Settings", "mineIcon", "5"));
	}

	public static void initResources() {
		Resource.flag = new Resource("graphics", "flag", flagIconIndex);
		Resource.mine = new Resource("graphics", "mine", mineIconIndex);
		Resource.cryFace = new Resource("graphics", "face_cry");
		Resource.surpriseFace = new Resource("graphics", "face_surprise");
		Resource.sosoFace = new Resource("graphics", "face_soso");
		Resource.happyFace = new Resource("graphics", "face_happy");
		Resource.bomb = new Resource("graphics", "mine11_2");
	}

	public static void startTimer() {
		timer.setLabel(frameMain.getTimerLbl());
		timer.start();
	}

	public static void stopTimer() {
		timer.stop();
	}

	public static void initRanks() {
		ranks = new ArrayList<Rank>();
		Rank rank1 = new Rank(1, 9, 9, 10);
		Rank rank2 = new Rank(2, 16, 16, 40);
		Rank rank3 = new Rank(3, 16, 30, 99);
		Rank rank4 = new Rank(4, 30, 30, 200);
		ranks.add(rank1);
		ranks.add(rank2);
		ranks.add(rank3);
		ranks.add(rank4);
	}

	private static Font[] fonts;

	/** 设置全局字体 */
	public static void initGlobalFontSetting(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource)
				UIManager.put(key, fontRes);
		}
	}

	private static int cellFontIndex;

	private static void initFonts() {
		fonts = new Font[13];
		fonts[0] = new Font("Dialog", Font.PLAIN, 12);
		fonts[1] = new Font("Agency FB", Font.BOLD, 15);
		fonts[2] = new Font("Belgium", Font.PLAIN, 12);
		fonts[3] = new Font("Century Gothic", Font.BOLD, 12);
		fonts[4] = new Font("Gigi", Font.PLAIN, 17);
		fonts[5] = new Font("Broadway", Font.PLAIN, 15);
		fonts[6] = new Font("Chiller", Font.BOLD, 15);
		fonts[7] = new Font("istanbul", Font.PLAIN, 15);
		fonts[8] = new Font("magneto", Font.PLAIN, 15);
		fonts[9] = new Font("jokerman", Font.PLAIN, 15);
		fonts[10] = new Font("MV Boli", Font.PLAIN, 15);
		fonts[11] = new Font("Tekton Pro Ext", Font.PLAIN, 15);
		fonts[12] = new Font("Rosewood Std Regular", Font.PLAIN, 15);
		cellFontIndex = Integer.parseInt(IniProcesser.GetPrivateProfileString(
				configPath, "Settings", "cellFont", "0"));
	}

	public static Font getCellFont() {
		return fonts[cellFontIndex];
	}

	public static int getCellFontIndex() {
		return cellFontIndex;
	}

	public static void setCellFontIndex(int index) {
		cellFontIndex = index;
	}

	/**
	 * 设置游戏参数
	 * 
	 * @param rowCount
	 *            行数
	 * @param columnCount
	 *            列数
	 * @param mineCount
	 *            雷数
	 */
	public static void setGameArgs(int rowCount, int columnCount, int mineCount) {
		GameManager.rowCount = rowCount;
		GameManager.columnCount = columnCount;
		GameManager.mineCount = mineCount;
	}

	/**
	 * （重新）开始游戏
	 * 
	 * @param notice
	 *            是否提示确认
	 */
	public static void restart(boolean notice) {
		int select = 1;
		if (notice) {
			select = JOptionPane.showConfirmDialog(null, "确定重开一局?", "提示",
					JOptionPane.YES_NO_OPTION);
		}
		if (select == 0 || !notice) {
			if (frameMain.getCellsContainer() != null) {
				frameMain.dispose();
				timer.stop();
				new FrameMain(getRank());
			}
		}
	}

	/** 检查是否满足胜利条件 */
	public static boolean canWin() {
		int enableCellsCount = frameMain.getCellsContainer()
				.getEnableCellCount();
		if (enableCellsCount == mineCount) {// 可用格子数=雷数
			win();
			return true;
		} else {
			return false;
		}
	}

	/** 游戏胜利后的处理 */
	public static void win() {
		if (gameOver) {
			return;
		}
		// 计时器停止
		stopTimer();
		// 点开所有格子
		frameMain.getCellsContainer().showMines();
		// 游戏结束状态
		gameOver = true;
		// 更改表情
		frameMain.setBtnFaceIcon(Resource.happyFace);
		// JOptionPane.showMessageDialog(null, "excellent!");
		// 登记高分榜
		if (rank != 0) {
			recordScore();
		}
	}

	/** 游戏失败后的处理 */
	public static void lose() {
		gameOver = true;
		stopTimer();
		frameMain.shake();
		frameMain.setBtnFaceIcon(Resource.cryFace);
		// JOptionPane.showMessageDialog(null, "boom!");
	}

	/** 登记分数 */
	private static void recordScore() {
		// 获取姓名
		String name = JOptionPane.showInputDialog(frameMain, "恭喜！请输入姓名",
				"登记排行榜", JOptionPane.INFORMATION_MESSAGE);
		if (name == null) {
			return;
		}
		if (name.equals("")) {
			name = "匿名";
		}
		// 获取时间
		Date nowDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		// 构造数据字符串
		String msg = rank + " " + name + " " + timer.getTime() + " "
				+ format.format(nowDate) + "\n";
		// 写入文件
		File file = new File(getScorePath());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!file.canWrite()) {
			JOptionPane.showMessageDialog(frameMain, "抱歉，排行榜登记失败。");
			return;
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(msg);
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frameMain, "抱歉，排行榜登记失败。");
			e.printStackTrace();
		}
	}

	public static void setRank(int rank) {
		// 保存旧级别
		setPreRank(GameManager.rank);
		GameManager.rank = rank;
		if (frameMain != null & frameMain.getBtnGroup() != null) {
			frameMain.setSelectedRankMntm(GameManager.getRank());
		}
		if (rank != 0) {
			// 根据级别设置参数
			Rank tmpRank = ranks.get(rank - 1);
			rowCount = tmpRank.getRowCount();
			columnCount = tmpRank.getColumnCount();
			mineCount = tmpRank.getMineCount();
			// 写入配置文件
			try {
				IniProcesser.WritePrivateProfileString(
						GameManager.getConfigPath(), "Settings", "rank",
						Integer.toString(rank));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getConfigPath() {
		return configPath;
	}

	public static int getRank() {
		return rank;
	}

	public static void setPreRank(int rank) {
		GameManager.preRank = rank;
	}

	public static int getPreRank() {
		return preRank;
	}

	/**
	 * @return gameOver
	 */
	public static boolean isGameOver() {
		return gameOver;
	}

	/**
	 * @param gameOver
	 *            要设置的 gameOver
	 */
	public static void setGameOver(boolean gameOver) {
		GameManager.gameOver = gameOver;
	}

	/**
	 * @return scorePath
	 */
	public static String getScorePath() {
		return scorePath;
	}

	/**
	 * @param scorePath
	 *            要设置的 scorePath
	 */
	public static void setScorePath(String scorePath) {
		GameManager.scorePath = scorePath;
	}

	public static Font[] getFonts() {
		return fonts;
	}

}
