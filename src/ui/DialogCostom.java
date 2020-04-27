package ui;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import element.GameManager;

public class DialogCostom extends MyDialog {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static MyTextField txtHeight;
	private static MyTextField txtWidth;
	private static MyTextField txtMineCount;
	private JPanel panelCenter;
	private JPanel panelLbl;
	private JLabel lblHeight;
	private JLabel lblWidth;
	private static JLabel lblMineCount;
	private JPanel panelTxt;
	private JPanel panelBottom;
	private MyButton btnConfirm;
	private MyButton btnCancel;
	private FrameMain owner;
	private JLabel lblNorth;

	public DialogCostom(FrameMain owner) {
		super(owner, "自定义");
		this.owner = owner;
		setType(Type.UTILITY);
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(new Rectangle(181, 148));
		setResizable(false);// 大小不可调
		initLayout();
		setListeners();
		setLocationRelativeTo(owner);// 居中
		fillDefaults();
	}

	private void fillDefaults() {
		txtHeight.setText("" + GameManager.rowCount);
		txtWidth.setText("" + GameManager.columnCount);
		txtMineCount.setText("" + GameManager.mineCount);
	}

	private void initLayout() {
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		{
			lblNorth = new JLabel("tip:自定义的游戏不参与排行榜");
			lblNorth.setHorizontalAlignment(SwingConstants.CENTER);
			contentPane.add(lblNorth, BorderLayout.NORTH);
		}
		{
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(0, 2, 0, 0));
			contentPane.add(panelCenter, BorderLayout.CENTER);
			{
				panelLbl = new JPanel();
				panelLbl.setLayout(new GridLayout(3, 0, 0, 0));
				panelCenter.add(panelLbl);
				{
					lblHeight = new JLabel("高度(" + GameManager.minRowCount
							+ "~" + GameManager.maxRowCount + ")");
					lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
					panelLbl.add(lblHeight);
				}
				{
					lblWidth = new JLabel("宽度(" + GameManager.minColumnCount
							+ "~" + GameManager.maxColumnCount + ")");
					lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
					panelLbl.add(lblWidth);
				}
				{
					lblMineCount = new JLabel("雷数(1~" + GameManager.rowCount
							* GameManager.columnCount + ")");
					lblMineCount.setHorizontalAlignment(SwingConstants.CENTER);
					panelLbl.add(lblMineCount);
				}
			}
			{
				panelTxt = new JPanel();
				panelTxt.setLayout(new GridLayout(3, 0, 0, 0));
				panelCenter.add(panelTxt);
				{
					txtHeight = new MyTextField(GameManager.minRowCount,
							GameManager.maxRowCount);
					txtHeight.setHorizontalAlignment(SwingConstants.CENTER);
					txtHeight.setName("高度");
					txtHeight.setColumns(10);
					panelTxt.add(txtHeight);
					addInput(txtHeight);
				}
				{
					txtWidth = new MyTextField(GameManager.minColumnCount,
							GameManager.maxColumnCount);
					txtWidth.setHorizontalAlignment(SwingConstants.CENTER);
					txtWidth.setName("宽度");
					txtWidth.setColumns(10);
					panelTxt.add(txtWidth);
					addInput(txtWidth);
				}
				{
					txtMineCount = new MyTextField(1, GameManager.maxRowCount
							* GameManager.maxColumnCount);
					txtMineCount.setHorizontalAlignment(SwingConstants.CENTER);
					txtMineCount.setName("雷数");
					txtMineCount.setColumns(10);
					panelTxt.add(txtMineCount);
					addInput(txtMineCount);
				}
			}
		}
		{
			panelBottom = new JPanel();
			contentPane.add(panelBottom, BorderLayout.SOUTH);
			{
				btnConfirm = new MyButton(this, "确定");
				btnConfirm.setActionCommand("confirm");
				panelBottom.add(btnConfirm);
			}
			{
				btnCancel = new MyButton(this, "取消");
				btnCancel.setActionCommand("cancel");
				panelBottom.add(btnCancel);
			}
		}
	}

	public boolean isEditValid() {
		for (JComponent i : inputs) {
			if (i instanceof MyTextField) {
				if (!((MyTextField) i).isEditValid()) {
					return false;
				}
			}
		}
		return true;
	}

	private void setListeners() {
		// 确定
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 检查输入
				if (txtHeight.getText().equals("")
						|| txtWidth.getText().equals("")
						|| txtMineCount.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "任意一项参数都不能为空");
					return;
				}
				// 设置参数，重新开局
				int rowCount = Integer.parseInt(txtHeight.getText());
				int columnCount = Integer.parseInt(txtWidth.getText());
				int mineCount = Integer.parseInt(txtMineCount.getText());
				GameManager.setGameArgs(rowCount, columnCount, mineCount);
				GameManager.setRank(0);
				DialogCostom.this.dispose();
				owner.restart(false);
			}
		});
		// 取消
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogCostom.this.dispose();
			}
		});
	}

	@Override
	public void dispose() {
		GameManager.setRank(GameManager.getPreRank());
		super.dispose();
	}

	public static void fixMineCount() {
		if (!txtHeight.getText().equals("") && !txtWidth.getText().equals("")) {
			int tempHeight = Integer.parseInt(txtHeight.getText());
			int tempWidth = Integer.parseInt(txtWidth.getText());
			int maxMineCount = tempHeight * tempWidth;
			lblMineCount.setText("雷数(1~" + maxMineCount + ")");
			txtMineCount.setRange(1, maxMineCount);
			// txtMineCount.fix();
		}
	}

	public static void updateTxt() {
		if (!txtHeight.getText().equals("") && !txtWidth.getText().equals("")) {
			int tempHeight = Integer.parseInt(txtHeight.getText());
			int tempWidth = Integer.parseInt(txtWidth.getText());
			int maxMineCount = tempHeight * tempWidth;
			lblMineCount.setText("雷数(1~" + maxMineCount + ")");
			txtMineCount.setRange(1, maxMineCount);
			txtMineCount.fix();
			// if (!txtMineCount.getText().equals("")) {
			// if (Integer.parseInt(txtMineCount.getText()) > maxMineCount) {
			// txtMineCount.setText("" + maxMineCount);
			// }
			// }
		} else {
			lblMineCount.setText("雷数(1~?)");
		}
	}
}
