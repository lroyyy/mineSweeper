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
 * ��Ϸ������
 * 
 * @author zhengfei.fjhg
 * 
 */
public class GameManager {

	/** ����·�� */
	private static String configPath = "./config.ini";
	/** �߷ְ�·�� */
	private static String scorePath = "./highScores.txt";
	/** ���� */
	public static int columnCount = 16;
	/** ������� */
	public static int maxColumnCount = 60;
	/** ��С���� */
	public static int minColumnCount = 2;
	/** ���� */
	public static int rowCount = 16;
	/** ������� */
	public static int maxRowCount = 30;
	/** ��С���� */
	public static int minRowCount = 2;
	/** ���� */
	public static int mineCount = 40;
	/** ������� */
	public static int maxMineCount = 999;
	/** ��С���� */
	public static int minMineCount = 1;
	/** ��ʱ�� */
	public static LTimer timer;
	/** ���� */
	public static int rank;
	/** ���� */
	public static ArrayList<Rank> ranks;
	/** �ɼ��� */
	public static int preRank;
	/** ����ͼ������ */
	public static int flagIconIndex;
	/** ��ͼ������ */
	public static int mineIconIndex;
	/** ������ */
	public static FrameMain frameMain;
	/** ��Ϸ�Ƿ���� */
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

	/** ����ȫ������ */
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
	 * ������Ϸ����
	 * 
	 * @param rowCount
	 *            ����
	 * @param columnCount
	 *            ����
	 * @param mineCount
	 *            ����
	 */
	public static void setGameArgs(int rowCount, int columnCount, int mineCount) {
		GameManager.rowCount = rowCount;
		GameManager.columnCount = columnCount;
		GameManager.mineCount = mineCount;
	}

	/**
	 * �����£���ʼ��Ϸ
	 * 
	 * @param notice
	 *            �Ƿ���ʾȷ��
	 */
	public static void restart(boolean notice) {
		int select = 1;
		if (notice) {
			select = JOptionPane.showConfirmDialog(null, "ȷ���ؿ�һ��?", "��ʾ",
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

	/** ����Ƿ�����ʤ������ */
	public static boolean canWin() {
		int enableCellsCount = frameMain.getCellsContainer()
				.getEnableCellCount();
		if (enableCellsCount == mineCount) {// ���ø�����=����
			win();
			return true;
		} else {
			return false;
		}
	}

	/** ��Ϸʤ����Ĵ��� */
	public static void win() {
		if (gameOver) {
			return;
		}
		// ��ʱ��ֹͣ
		stopTimer();
		// �㿪���и���
		frameMain.getCellsContainer().showMines();
		// ��Ϸ����״̬
		gameOver = true;
		// ���ı���
		frameMain.setBtnFaceIcon(Resource.happyFace);
		// JOptionPane.showMessageDialog(null, "excellent!");
		// �ǼǸ߷ְ�
		if (rank != 0) {
			recordScore();
		}
	}

	/** ��Ϸʧ�ܺ�Ĵ��� */
	public static void lose() {
		gameOver = true;
		stopTimer();
		frameMain.shake();
		frameMain.setBtnFaceIcon(Resource.cryFace);
		// JOptionPane.showMessageDialog(null, "boom!");
	}

	/** �ǼǷ��� */
	private static void recordScore() {
		// ��ȡ����
		String name = JOptionPane.showInputDialog(frameMain, "��ϲ������������",
				"�Ǽ����а�", JOptionPane.INFORMATION_MESSAGE);
		if (name == null) {
			return;
		}
		if (name.equals("")) {
			name = "����";
		}
		// ��ȡʱ��
		Date nowDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		// ���������ַ���
		String msg = rank + " " + name + " " + timer.getTime() + " "
				+ format.format(nowDate) + "\n";
		// д���ļ�
		File file = new File(getScorePath());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!file.canWrite()) {
			JOptionPane.showMessageDialog(frameMain, "��Ǹ�����а�Ǽ�ʧ�ܡ�");
			return;
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(msg);
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frameMain, "��Ǹ�����а�Ǽ�ʧ�ܡ�");
			e.printStackTrace();
		}
	}

	public static void setRank(int rank) {
		// ����ɼ���
		setPreRank(GameManager.rank);
		GameManager.rank = rank;
		if (frameMain != null & frameMain.getBtnGroup() != null) {
			frameMain.setSelectedRankMntm(GameManager.getRank());
		}
		if (rank != 0) {
			// ���ݼ������ò���
			Rank tmpRank = ranks.get(rank - 1);
			rowCount = tmpRank.getRowCount();
			columnCount = tmpRank.getColumnCount();
			mineCount = tmpRank.getMineCount();
			// д�������ļ�
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
	 *            Ҫ���õ� gameOver
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
	 *            Ҫ���õ� scorePath
	 */
	public static void setScorePath(String scorePath) {
		GameManager.scorePath = scorePath;
	}

	public static Font[] getFonts() {
		return fonts;
	}

}
