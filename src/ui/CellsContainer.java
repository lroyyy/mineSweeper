package ui;

import javax.swing.JPanel;

import element.GameManager;
import element.Resource;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

public class CellsContainer extends JPanel {

	private static final long serialVersionUID = 1L;
	/** ���Ӽ� */
	public Cell[][] cells;
	private int markI = 1;
	private int markJ = 0;
	private ArrayList<Integer> mines;

	/**
	 * ����CellsContainer
	 * 
	 * @param rowCount
	 *            ����
	 * @param columnCount
	 *            ����
	 * @param mineCount
	 *            ����
	 */
	public CellsContainer(int rowCount, int columnCount, int mineCount) {
		setLayout(new GridLayout(rowCount, columnCount, 0, 0));
		initCells();
	}

	/** add cell to cells */
	public void createCells(Cell cell) {
		if (markJ != GameManager.columnCount) {
			markJ++;
		} else if (markI != GameManager.rowCount) {
			markI++;
			markJ = 1;
		} else {
			System.out
					.println("full!(markI=" + markI + ",markJ=" + markJ + ")");
			return;
		}
		cells[markI - 1][markJ - 1] = cell;
	}

	/** add cells to cellsContainer and cells */
	public void addCells() {
		int i = 1;
		int j = 1;
		for (int k = 1; k <= GameManager.columnCount * GameManager.rowCount; k++) {
			Cell cell = new Cell(i, j, this);
			add(cell);// add cell to cellsContainer
			createCells(cell);// add cell to cells
			j++;
			if (j > GameManager.columnCount) {
				i++;
				j = 1;
			}
		}
	}

	/** ��ȡ���ø����� */
	public int getEnableCellCount() {
		int count = 0;
		for (int i = 0; i < GameManager.rowCount; i++) {
			for (int j = 0; j < GameManager.columnCount; j++) {
				Cell cell = cells[i][j];
				if (cell.isEnabled()) {
					count++;
				}
			}
		}
		return count;
	}

	public void setPressAround(int i, int j, boolean b) {
		i--;
		j--;
		// left
		if (j > 0) {
			cells[i][j - 1].setPressed(b);
		}
		// right
		if (j < GameManager.columnCount - 1) {
			cells[i][j + 1].setPressed(b);
		}
		// top
		if (i > 0) {
			cells[i - 1][j].setPressed(b);
			// top left
			if (j > 0) {
				cells[i - 1][j - 1].setPressed(b);
			}
			// top right
			if (j < GameManager.columnCount - 1) {
				cells[i - 1][j + 1].setPressed(b);
			}
		}
		// buttom
		if (i < GameManager.rowCount - 1) {
			cells[i + 1][j].setPressed(b);
			// buttom left
			if (j > 0) {
				cells[i + 1][j - 1].setPressed(b);
			}
			// buttom right
			if (j < GameManager.columnCount - 1) {
				cells[i + 1][j + 1].setPressed(b);
			}
		}
	}

	/**
	 * ��չ
	 * <p>
	 * �㿪��չԴ��(i,j)�����ķ��׸���
	 * 
	 * @param i
	 *            ��չԴ�������
	 * @param j
	 *            ��չԴ��������
	 * */
	public void clickAround(int i, int j) {
		i--;
		j--;
		Cell cell = cells[i][j];
		// ��ȡ�ܱ�������
		int flagCount = 0;
		// left
		if (j > 0) {
			if (cells[i][j - 1].getState() == Cell.flag) {
				flagCount++;
			}
		}
		// right
		if (j < GameManager.columnCount - 1) {
			if (cells[i][j + 1].getState() == Cell.flag) {
				flagCount++;
			}
		}
		// top
		if (i > 0) {
			if (cells[i - 1][j].getState() == Cell.flag) {
				flagCount++;
			}
			// top left
			if (j > 0) {
				if (cells[i - 1][j - 1].getState() == Cell.flag) {
					flagCount++;
				}
			}
			// top right
			if (j < GameManager.columnCount - 1) {
				if (cells[i - 1][j + 1].getState() == Cell.flag) {
					flagCount++;
				}
			}
		}
		// buttom
		if (i < GameManager.rowCount - 1) {
			if (cells[i + 1][j].getState() == Cell.flag) {
				flagCount++;
			}
			// buttom left
			if (j > 0) {
				if (cells[i + 1][j - 1].getState() == Cell.flag) {
					flagCount++;
				}
			}
			// buttom right
			if (j < GameManager.columnCount - 1) {
				if (cells[i + 1][j + 1].getState() == Cell.flag) {
					flagCount++;
				}
			}
		}
		// �㿪�ܱ߸���
		if (flagCount == cell.getDigit()) {// ���ܱ�����������Դ����ӵ�����
			// left
			if (j > 0) {
				cells[i][j - 1].leftClickAction();
			}
			// right
			if (j < GameManager.columnCount - 1) {
				cells[i][j + 1].leftClickAction();
			}
			// top
			if (i > 0) {
				cells[i - 1][j].leftClickAction();
				// top left
				if (j > 0) {
					cells[i - 1][j - 1].leftClickAction();
				}
				// top right
				if (j < GameManager.columnCount - 1) {
					cells[i - 1][j + 1].leftClickAction();
				}
			}
			// buttom
			if (i < GameManager.rowCount - 1) {
				cells[i + 1][j].leftClickAction();
				// buttom left
				if (j > 0) {
					cells[i + 1][j - 1].leftClickAction();
				}
				// buttom right
				if (j < GameManager.columnCount - 1) {
					cells[i + 1][j + 1].leftClickAction();
				}
			}
		}
	}

	/**
	 * ��ʼ����
	 * <P>
	 * �������
	 * */
	public void initMines(int mineCount) {
		int cellCount = GameManager.columnCount * GameManager.rowCount;
		mines = new ArrayList<Integer>();
		Random random = new Random();
		for (int i = 0; i < mineCount;) {
			int tempId = random.nextInt(cellCount) + 1;
			if (!mines.contains(tempId)) {
				mines.add(tempId);
				getCell(tempId).setMine(true);
				i++;
			}
		}
	}

	public Cell getCell(int id) {
		id--;
		if (id >= 0 && id <= GameManager.rowCount * GameManager.columnCount) {
			int i = id / GameManager.columnCount;
			int j = id % GameManager.columnCount;
			return cells[i][j];
		} else {
			System.out.println("out of range");
			return null;
		}
	}

	public Cell getCell(int i, int j) {
		if (i <= GameManager.rowCount && j <= GameManager.columnCount) {
			return cells[i - 1][j - 1];
		} else {
			System.out.println("out of range");
			return null;
		}
	}

	// private int[] IdToFix(int id) {
	// id--;
	// if (id >= 0 && id <= rowMineCount * columnMineCount) {
	// int i = id / rowMineCount + 1;
	// int j = id % columnMineCount + 1;
	// return new int[] { i, j };
	// } else {
	// System.out.println("out of range");
	// return null;
	// }
	// }

	/**
	 * ��ʼ��cells
	 * */
	public void initCells() {
		cells = new Cell[GameManager.rowCount][GameManager.columnCount];
		addCells();
		initMines(GameManager.mineCount);
		initCellsDigits();
	}

	/**
	 * �����������
	 * 
	 * @param i
	 *            ���Ӻ�����
	 * @param j
	 *            ����������
	 * */
	public void leftClickCell(int i, int j) {
		if (i <= GameManager.rowCount && j <= GameManager.columnCount) {
			if (cells[i - 1][j - 1].isEnabled()) {
				cells[i - 1][j - 1].leftClickAction();
			}
		} else {
			System.out.println("failed to click:out of range");
		}
	}

	/** ���ø��ӵ����� */
	public void initCellsDigits() {
		for (int i = 0; i < GameManager.mineCount; i++) {
			int tempId = (Integer) mines.get(i);
			// left
			if (tempId % GameManager.columnCount != 1) {
				getCell(tempId - 1).raiseDigit();
			}
			// right
			if (tempId % GameManager.columnCount != 0) {
				getCell(tempId + 1).raiseDigit();
			}
			// top
			if (tempId > GameManager.columnCount) {
				getCell(tempId - GameManager.columnCount).raiseDigit();
				// top left
				if (tempId % GameManager.columnCount != 1) {
					getCell(tempId - GameManager.columnCount - 1).raiseDigit();
				}
				// top right
				if (tempId % GameManager.columnCount != 0) {
					getCell(tempId - GameManager.columnCount + 1).raiseDigit();
				}
			}
			// buttom
			if (tempId + GameManager.columnCount <= GameManager.columnCount
					* GameManager.rowCount) {
				getCell(tempId + GameManager.columnCount).raiseDigit();
				// buttom left
				if (tempId % GameManager.columnCount != 1) {
					getCell(tempId + GameManager.columnCount - 1).raiseDigit();
				}
				// buttom right
				if (tempId % GameManager.columnCount != 0) {
					getCell(tempId + GameManager.columnCount + 1).raiseDigit();
				}
			}
		}

	}

	/** չʾ�� */
	public void showMines() {
		for (int i = 0; i < GameManager.rowCount; i++) {
			for (int j = 0; j < GameManager.columnCount; j++) {
				Cell cell = cells[i][j];
				if (cell.getState() != Cell.flag && cell.isMine()
						&& GameManager.isGameOver()) {
					cell.setIcon(Resource.mine.getIcon());
				}
			}
		}
	}

	/** �������и��ӵ�ͼ�� */
	public void updateCellsIcon() {
		for (int i = 0; i < GameManager.rowCount; i++) {
			for (int j = 0; j < GameManager.columnCount; j++) {
				Cell cell = cells[i][j];
				cell.updateIcon();
			}
		}
	}
	public void setCellsFont(Font font){
		for (int i = 0; i < GameManager.rowCount; i++) {
			for (int j = 0; j < GameManager.columnCount; j++) {
				Cell cell = cells[i][j];
				cell.setFont(font);
			}
		}
	}
}
