/**
 * 
 */
package ui;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import element.Score;

/**
 * @author zhengfei.fjhg
 * 
 */
public class MyTable extends JTable {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;

	public MyTable() {
		// �в���ѡ
		setRowSelectionAllowed(false);
		// ��Ԫ�񲻿ɱ༭
		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// ���ñ�ͷ
		tableModel.setColumnIdentifiers(new String[] { "����", "����", "��ʱ(s)",
				"���ʱ��" });
		setModel(tableModel);
		// ���ø��п�
		getColumnModel().getColumn(0).setPreferredWidth(5);
		getColumnModel().getColumn(1).setPreferredWidth(60);
		getColumnModel().getColumn(2).setPreferredWidth(30);
		getColumnModel().getColumn(3).setPreferredWidth(100);
		// ��Ԫ�����
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		setDefaultRenderer(Object.class, renderer);
	}

	public void addRows(ArrayList<Score> scores) {
		for (int i = 0; i < scores.size() && i < 5; i++) {
			Score score = scores.get(i);
			Vector<String> v = new Vector<String>();
			v.add(Integer.toString(i + 1));
			v.add(score.getPlayerName());
			v.add(Integer.toString(score.getConsumingTime()));
			v.add(score.getDateString());
			tableModel.addRow(v);
		}
	}
}
