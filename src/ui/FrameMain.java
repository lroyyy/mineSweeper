package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.Utils;
import element.GameManager;
import element.Resource;

public class FrameMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton btnFace;
	public static JLabel mineCountLbl;
	public static String playerName;
	public static JLabel stateLbl;
	public static boolean timeFlag;
	private static JLabel timerLbl;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new FrameMain(-1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private ButtonGroup btnGroup;
	private JPanel buttonPanel;
	private CellsContainer cellsContainer;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu menuGame;
	private JMenu menuHelp;
	private JPanel mineCountPanel;
	private JLabel mineCountTxtLbl;
	private JMenuItem mntmAbout;
	private JMenuItem mntmExit;
	private JMenuItem mntmHighScores;
	private JRadioButtonMenuItem mntmR1;
	private JRadioButtonMenuItem mntmR2;
	private JRadioButtonMenuItem mntmR3;
	private JRadioButtonMenuItem mntmR4;
	private JMenuItem mntmSettings;
	private JMenuItem mntmStart;
	private JRadioButtonMenuItem mntmSelfDefine;
	private JPanel timerPanel;
	private JLabel timerTxtLbl;
	private JPanel topPanel;

	public FrameMain(int rank) {
		GameManager.initGameManager(this, rank);
		initLayout();
		setListeners();
		setSelectedRankMntm(GameManager.getRank());
		GameManager.startTimer();
		setVisible(true);
	}

	private void createMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		{
			menuGame = new JMenu("游戏");
			menuBar.add(menuGame);
			{
				mntmStart = new JMenuItem("开局");
				menuGame.add(mntmStart);
			}
			menuGame.addSeparator();
			{
				mntmR1 = new JRadioButtonMenuItem("新手级");
				mntmR1.setMnemonic(1);
				menuGame.add(mntmR1);
			}
			{
				mntmR2 = new JRadioButtonMenuItem("入门级");
				mntmR2.setMnemonic(2);
				menuGame.add(mntmR2);
			}
			{
				mntmR3 = new JRadioButtonMenuItem("专家级");
				mntmR3.setMnemonic(3);
				menuGame.add(mntmR3);
			}
			{
				mntmR4 = new JRadioButtonMenuItem("变态级");
				mntmR4.setMnemonic(4);
				menuGame.add(mntmR4);
			}
			{
				mntmSelfDefine = new JRadioButtonMenuItem("自定义");
				mntmSelfDefine.setMnemonic(0);
				menuGame.add(mntmSelfDefine);
			}
			{
				btnGroup = new ButtonGroup();
				btnGroup.add(mntmR1);
				btnGroup.add(mntmR2);
				btnGroup.add(mntmR3);
				btnGroup.add(mntmR4);
				btnGroup.add(mntmSelfDefine);
				// Utils.getRadioButtonMenuItem(btnGroup,
				// GameManager.getRank()).setSelected(true);
			}

			menuGame.addSeparator();
			{
				mntmHighScores = new JMenuItem("排行榜");
				menuGame.add(mntmHighScores);
			}
			menuGame.addSeparator();
			{
				mntmSettings = new JMenuItem("设置");
				menuGame.add(mntmSettings);
				menuGame.addSeparator();
			}
			{
				mntmExit = new JMenuItem("退出");
				menuGame.add(mntmExit);
			}
		}
		{
			menuHelp = new JMenu("帮助");
			menuBar.add(menuHelp);
			{
				mntmAbout = new JMenuItem("关于");
				menuHelp.add(mntmAbout);
			}
		}
	}

	private void createTopPanel() {
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout(0, 0));
		contentPane.add(topPanel, BorderLayout.NORTH);
		{
			mineCountPanel = new JPanel();
			mineCountPanel.setLayout(new GridLayout(2, 0, 0, 0));
			topPanel.add(mineCountPanel, BorderLayout.WEST);
			{
				mineCountTxtLbl = new JLabel("雷数");
				mineCountPanel.add(mineCountTxtLbl);
			}
			{
				mineCountLbl = new JLabel("0");
				mineCountLbl.setHorizontalAlignment(SwingConstants.CENTER);
				mineCountLbl.setText("" + GameManager.mineCount);
				mineCountPanel.add(mineCountLbl);
			}
		}
		{
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			topPanel.add(buttonPanel, BorderLayout.CENTER);
			{
				btnFace = new JButton();
				btnFace.setMargin(new Insets(0, 0, 0, 0));
				btnFace.setIcon(Resource.sosoFace.getIcon());
				buttonPanel.add(btnFace);
			}
		}
		{
			timerPanel = new JPanel();
			topPanel.add(timerPanel, BorderLayout.EAST);
			timerPanel.setLayout(new GridLayout(2, 2, 0, 0));
			{
				timerTxtLbl = new JLabel("计时");
				timerPanel.add(timerTxtLbl);
			}
			{
				timerLbl = new JLabel("" + 0);
				timerLbl.setHorizontalAlignment(SwingConstants.CENTER);
				timerPanel.add(timerLbl);
			}
		}
	}

	public CellsContainer getCellsContainer() {
		return cellsContainer;
	}

	public JLabel getTimerLbl() {
		return timerLbl;
	}

	private void initLayout() {
		setIconImage(Resource.bomb.getIcon().getImage());
		setTitle("Mine Sweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(new Rectangle(GameManager.columnCount * Cell.width + 50,
				GameManager.rowCount * Cell.height + 150));
		setLocationRelativeTo(null);// 居中
		setResizable(false);// 大小不可调
		{
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			// 创建菜单
			createMenu();
			{
				cellsContainer = new CellsContainer(GameManager.rowCount,
						GameManager.columnCount, GameManager.mineCount);
				contentPane.add(cellsContainer, BorderLayout.CENTER);
			}
			// 创建顶端窗格
			createTopPanel();
			{// 状态栏
				stateLbl = new JLabel(" ");
				contentPane.add(stateLbl, BorderLayout.SOUTH);
			}
		}
	}

	private void setListeners() {
		mntmStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restart(false);
			}
		});
		mntmR1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameManager.setRank(1);
				// GameManager.setGameArgs(9, 9, 10);
				restart(false);
			}
		});
		mntmR2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameManager.setRank(2);
				// GameManager.setGameArgs(16, 16, 40);
				restart(false);
			}
		});
		mntmR3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameManager.setRank(3);
				// GameManager.setGameArgs(16, 30, 99);
				restart(false);
			}
		});
		mntmR4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameManager.setRank(4);
				// GameManager.setGameArgs(30, 30, 200);
				restart(false);
			}
		});
		mntmSelfDefine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameManager.setRank(0);
				new DialogCostom(FrameMain.this).setVisible(true);
			}
		});
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogSettings(FrameMain.this).setVisible(true);
			}
		});
		mntmHighScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogHighScores().setVisible(true);
			}
		});
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogAbout(FrameMain.this).setVisible(true);
			}
		});
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnFace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restart(false);
				setBtnFaceIcon(Resource.sosoFace);
			}
		});
	}

	public void setBtnFaceIcon(Resource resource) {
		btnFace.setIcon(resource.getIcon());
	}

	public void setSelectedRankMntm(int mnemonic) {
		// System.out.println(index);
		if (btnGroup != null) {
			Utils.getRadioButtonMenuItemByMnemonic(btnGroup, mnemonic)
					.setSelected(true);
		} else {
			System.out.println("failed to setRankMntm:btnGroup!=null");
		}
	}

	public void shake() {
		FrameShaker shaker = new FrameShaker(this);
		shaker.startShake();
	}

	/**
	 * (re)start a game
	 * 
	 * @param b
	 *            是否提示
	 */
	public void restart(boolean b) {
		int select = 1;
		if (b) {
			select = JOptionPane.showConfirmDialog(null, "确定重开一局?", "提示",
					JOptionPane.YES_NO_OPTION);
		}
		if (select == 0 || !b) {
			if (cellsContainer != null) {
				FrameMain.this.dispose();
				GameManager.stopTimer();
				new FrameMain(GameManager.getRank()).setVisible(true);
			}
		}
	}

	public ButtonGroup getBtnGroup() {
		return btnGroup;
	}
}
