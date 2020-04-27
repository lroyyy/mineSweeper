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
		super(owner, "�Զ���");
		this.owner = owner;
		setType(Type.UTILITY);
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(new Rectangle(181, 148));
		setResizable(false);// ��С���ɵ�
		initLayout();
		setListeners();
		setLocationRelativeTo(owner);// ����
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
			lblNorth = new JLabel("tip:�Զ������Ϸ���������а�");
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
					lblHeight = new JLabel("�߶�(" + GameManager.minRowCount
							+ "~" + GameManager.maxRowCount + ")");
					lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
					panelLbl.add(lblHeight);
				}
				{
					lblWidth = new JLabel("���(" + GameManager.minColumnCount
							+ "~" + GameManager.maxColumnCount + ")");
					lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
					panelLbl.add(lblWidth);
				}
				{
					lblMineCount = new JLabel("����(1~" + GameManager.rowCount
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
					txtHeight.setName("�߶�");
					txtHeight.setColumns(10);
					panelTxt.add(txtHeight);
					addInput(txtHeight);
				}
				{
					txtWidth = new MyTextField(GameManager.minColumnCount,
							GameManager.maxColumnCount);
					txtWidth.setHorizontalAlignment(SwingConstants.CENTER);
					txtWidth.setName("���");
					txtWidth.setColumns(10);
					panelTxt.add(txtWidth);
					addInput(txtWidth);
				}
				{
					txtMineCount = new MyTextField(1, GameManager.maxRowCount
							* GameManager.maxColumnCount);
					txtMineCount.setHorizontalAlignment(SwingConstants.CENTER);
					txtMineCount.setName("����");
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
				btnConfirm = new MyButton(this, "ȷ��");
				btnConfirm.setActionCommand("confirm");
				panelBottom.add(btnConfirm);
			}
			{
				btnCancel = new MyButton(this, "ȡ��");
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
		// ȷ��
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �������
				if (txtHeight.getText().equals("")
						|| txtWidth.getText().equals("")
						|| txtMineCount.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "����һ�����������Ϊ��");
					return;
				}
				// ���ò��������¿���
				int rowCount = Integer.parseInt(txtHeight.getText());
				int columnCount = Integer.parseInt(txtWidth.getText());
				int mineCount = Integer.parseInt(txtMineCount.getText());
				GameManager.setGameArgs(rowCount, columnCount, mineCount);
				GameManager.setRank(0);
				DialogCostom.this.dispose();
				owner.restart(false);
			}
		});
		// ȡ��
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
			lblMineCount.setText("����(1~" + maxMineCount + ")");
			txtMineCount.setRange(1, maxMineCount);
			// txtMineCount.fix();
		}
	}

	public static void updateTxt() {
		if (!txtHeight.getText().equals("") && !txtWidth.getText().equals("")) {
			int tempHeight = Integer.parseInt(txtHeight.getText());
			int tempWidth = Integer.parseInt(txtWidth.getText());
			int maxMineCount = tempHeight * tempWidth;
			lblMineCount.setText("����(1~" + maxMineCount + ")");
			txtMineCount.setRange(1, maxMineCount);
			txtMineCount.fix();
			// if (!txtMineCount.getText().equals("")) {
			// if (Integer.parseInt(txtMineCount.getText()) > maxMineCount) {
			// txtMineCount.setText("" + maxMineCount);
			// }
			// }
		} else {
			lblMineCount.setText("����(1~?)");
		}
	}
}
