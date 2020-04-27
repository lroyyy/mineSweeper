package ui;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import element.GameManager;
import element.Resource;

/** 格子 */
public class Cell extends JButton {
	public static int height = 20;
	public static int width = 20;
	/** 状态：设置了旗子 */
	public final static String flag = "FLAG";
	/** 状态：触发了雷 */
	public final static String mine = "MINE";
	/** 状态：未点开 */
	public final static String unDig = "UNDIG";
	/** 状态：展示了数字 */
	public final static String showDigit = "SHOWDIGIT";
	/** 状态：设置了问号 */
	public final static String question = "QUESTION";
	private static final long serialVersionUID = 1L;
	private CellsContainer container;
	/** 数字 */
	private int digit = 0;
	/** fix i */
	private int i;
	// private int id;
	private boolean isMine = false;
	/** fix j */
	private int j;
	/** 状态 */
	private String state = unDig;

	/**
	 * @param i
	 *            格子横坐标
	 * @param j
	 *            格子纵坐标
	 * @param container
	 *            所有者窗格
	 */
	public Cell(int i, int j, CellsContainer container) {
		// this.id = id;
		this.i = i;
		this.j = j;
		this.container = container;
		setFont(GameManager.getCellFont());
		setUI(new CellUI());
		setBounds(new Rectangle(width, height));
		setMargin(new Insets(1, 1, 1, 1));
		setListeners();
	}

	/** 是否可被选中 */
	public boolean canSelected() {
		if (isEnabled() && !(getState() == flag)) {
			return true;
		} else {
			return false;
		}
	}

	public int getDigit() {
		return digit;
	}

	/**
	 * @return state
	 */
	public String getState() {
		return state;
	}

	public boolean isMine() {
		return isMine;
	}

	/**
	 * 左键单击动作
	 * <p>
	 * 1.若有旗，无动作<br>
	 * 2.若无旗<br>
	 * 2.1.无雷，则点开，显示数字<br>
	 * 2.2.有雷，则显示雷<br>
	 * */
	public void leftClickAction() {
		if (getState() == flag) {
			return;
		}
		setEnabled(false);
		requestFocus();
		if (!isMine) {// is not mine
			if (digit != 0) {// digit=0
				setText("" + digit);
				setState(showDigit);
			} else {// digit!=0
				// left
				if (j > 1) {
					container.leftClickCell(i, j - 1);
				}
				// right
				if (j < GameManager.columnCount) {
					container.leftClickCell(i, j + 1);
				}
				// top
				if (i > 1) {
					container.leftClickCell(i - 1, j);
					// top left
					if (j > 1) {
						container.leftClickCell(i - 1, j - 1);
					}
					// top right
					if (j < GameManager.columnCount) {
						container.leftClickCell(i - 1, j + 1);
					}
				}
				// bottom
				if (i < GameManager.rowCount) {
					container.leftClickCell(i + 1, j);
					// bottom left
					if (j > 1) {
						container.leftClickCell(i + 1, j - 1);
					}
					// bottom right
					if (j < GameManager.columnCount) {
						container.leftClickCell(i + 1, j + 1);
					}
				}
			}
			GameManager.canWin();
		} else {// is mine
			GameManager.lose();
			container.showMines();

		}
	}

	public void raiseDigit() {
		digit++;
	}

	/**
	 * 右键单击动作
	 * <p>
	 * 空->旗->问号->空
	 * */
	public void rightClickAction() {
		switch (getState()) {
		case flag:
			setIcon(null);
			setText("?");
			setState(Cell.question);
			break;
		case question:
			setIcon(null);
			setText("");
			FrameMain.mineCountLbl.setText(""
					+ (Integer.parseInt(FrameMain.mineCountLbl.getText()) + 1));
			setState(Cell.unDig);
			break;
		case unDig:
			setIcon(Resource.flag.getIcon());
			FrameMain.mineCountLbl.setText(""
					+ (Integer.parseInt(FrameMain.mineCountLbl.getText()) - 1));
			setState(Cell.flag);
			break;
		default:
			break;
		}
	}

	public void setDigit(int digit) {
		this.digit = digit;
	}

	public void setListeners() {
		addMouseListener(new MouseAdapter() {
			private boolean doublePressed;

			@Override
			public void mouseClicked(MouseEvent e) {
				if (GameManager.isGameOver()) {
					return;
				}
				if (Cell.this.isEnabled()) {
					// 左键单击
					if (e.getButton() == MouseEvent.BUTTON1) {
						leftClickAction();
					}
					// 右键单击
					if (e.getButton() == MouseEvent.BUTTON3) {
						rightClickAction();
					}
				}
				super.mouseClicked(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (GameManager.isGameOver()) {
					return;
				}
				requestFocus(true);
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (GameManager.isGameOver()) {
					return;
				}
				requestFocus(false);
				super.mouseExited(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (GameManager.isGameOver()) {
					return;
				}
				// 按下左右键或中键
				if (!(getState() == flag)
						&& (e.getModifiersEx() == (InputEvent.BUTTON3_DOWN_MASK + InputEvent.BUTTON1_DOWN_MASK) || e
								.getModifiersEx() == (InputEvent.BUTTON2_DOWN_MASK))) {
					doublePressed = true;
					container.setPressAround(i, j, true);
				}
				// 按下左键
				if (e.getButton() == MouseEvent.BUTTON1 && isEnabled()) {
					GameManager.frameMain.setBtnFaceIcon(Resource.surpriseFace);
				}
				super.mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (GameManager.isGameOver()) {
					return;
				}
				// 松开左键或右键或中键
				if (e.getButton() == MouseEvent.BUTTON1
						|| e.getButton() == MouseEvent.BUTTON3
						|| e.getButton() == MouseEvent.BUTTON2) {
					container.setPressAround(i, j, false);
					if (doublePressed && isFocusOwner()) {// 按下双键或中键的前提下，且取得焦点
						container.clickAround(i, j);
						doublePressed = false;
					}
					GameManager.frameMain.setBtnFaceIcon(Resource.sosoFace);
				}
				super.mouseReleased(e);
			}
		});
	}

	public void setMine(boolean mine) {
		this.isMine = mine;
	}

	public void setPressed(boolean b) {
		if (canSelected()) {
			getModel().setPressed(b);
		}
	}

	/**
	 * @param state
	 *            要设置的 state
	 */
	public void setState(String state) {
		this.state = state;
	}

	public void updateIcon() {
		if (getState() == flag) {//有旗子
			setIcon(Resource.flag.getIcon());
			return;
		}
		if (isMine && GameManager.isGameOver()) {//是雷且游戏结束
			setIcon(Resource.mine.getIcon());
			return;
		}
		if(getState()==showDigit){
			
		}
	}
}
