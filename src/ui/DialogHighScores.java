package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import element.GameManager;
import element.Score;

public class DialogHighScores extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnBack;
	private JButton btnClear;
	private ArrayList<Score> scores1;
	private ArrayList<Score> scores2;
	private ArrayList<Score> scores3;
	private ArrayList<Score> scores4;

	public DialogHighScores() {
		scores1 = new ArrayList<Score>();
		scores2 = new ArrayList<Score>();
		scores3 = new ArrayList<Score>();
		scores4 = new ArrayList<Score>();
		setModal(true);
		setType(Type.UTILITY);
		setTitle("扫雷排行榜");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 353, 469);
		setLocationRelativeTo(null);// 居中
		initScores();
		initLayout();
		setListeners();
	}

	class PanelScore extends JPanel {
		private static final long serialVersionUID = 1L;
		private String title;
		private ArrayList<Score> scores;
		private MyTable table;
		private JScrollPane scrollPane;

		public PanelScore(String title, ArrayList<Score> scores) {
			super();
			this.title = title;
			this.scores = scores;
			initLayout();
		}

		public void initLayout() {
			setBorder(new TitledBorder(null, title, TitledBorder.CENTER,
					TitledBorder.TOP, null, null));
			setLayout(new BorderLayout(0, 0));
			{
				table = new MyTable();
				table.addRows(scores);
			}
			{
				scrollPane = new JScrollPane(table);
				add(scrollPane);
			}
		}
	}

	public void initLayout() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		{
			JPanel panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(4, 0, 0, 0));
			contentPane.add(panelCenter, BorderLayout.CENTER);
			{
				PanelScore panelRank1 = new PanelScore("新手级", scores1);
				panelCenter.add(panelRank1);
			}
			{
				PanelScore panelRank2 = new PanelScore("入门级", scores2);
				panelCenter.add(panelRank2);
			}
			{
				PanelScore panelRank3 = new PanelScore("专家级", scores3);
				panelCenter.add(panelRank3);
			}
			{
				PanelScore panelRank4 = new PanelScore("变态级", scores4);
				panelCenter.add(panelRank4);
			}
		}
		{
			JPanel panelButtom = new JPanel();
			contentPane.add(panelButtom, BorderLayout.SOUTH);
			{
				btnClear = new JButton("清空");
				panelButtom.add(btnClear);
			}
			{
				btnBack = new JButton("返回");
				panelButtom.add(btnBack);
			}
		}

	}

	private void setListeners() {
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File(GameManager.getScorePath());
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter(file, false));
					writer.write("");
					writer.close();
					DialogHighScores.this.dispose();
					new DialogHighScores().setVisible(true);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "抱歉，清空失败。");
					e1.printStackTrace();
				}
			}
		});
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogHighScores.this.dispose();
			}
		});
	}

	private void initScores() {
		File file = new File(GameManager.getScorePath());
		if (!file.exists() || !file.canRead()) {
			return;
		}
		String line = "null";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(
					GameManager.getScorePath()));
			while ((line = reader.readLine()) != null) {
				String[] msgs = line.split(" ");
				int tmpRank = Integer.parseInt(msgs[0]);
				String tmpName = msgs[1];
				int tmpTime = Integer.parseInt(msgs[2]);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd,HH:mm:ss");
				Date tmpDate = null;
				try {
					tmpDate = sdf.parse(msgs[3]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Score score = new Score(tmpRank, tmpName, tmpTime, tmpDate);
				switch (tmpRank) {
				case 1:
					scores1.add(score);
					break;
				case 2:
					scores2.add(score);
					break;
				case 3:
					scores3.add(score);
					break;
				case 4:
					scores4.add(score);
					break;
				default:
					break;
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Score.sort(scores1);
		Score.sort(scores2);
		Score.sort(scores3);
		Score.sort(scores4);
	}

}
