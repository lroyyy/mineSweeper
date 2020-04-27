package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.border.Border;

import sun.swing.DefaultLookup;

/**
 * @author zhengfei.fjhg
 * 
 */
public class MyComboboxItem extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;
	private Font[] fonts;

	public MyComboboxItem(Font[] fonts) {
		super();
		this.fonts = fonts;
//		setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setComponentOrientation(list.getComponentOrientation());

		Color bg = null;
		Color fg = null;

		JList.DropLocation dropLocation = list.getDropLocation();
		if (dropLocation != null && !dropLocation.isInsert()
				&& dropLocation.getIndex() == index) {

			bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
			fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");

			isSelected = true;
		}

		if (isSelected) {
			setBackground(bg == null ? list.getSelectionBackground() : bg);
			setForeground(fg == null ? list.getSelectionForeground() : fg);
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		if (value instanceof Icon) {
			setIcon((Icon) value);
			setText("");
		} else {
			setIcon(null);
			setText((value == null) ? "" : value.toString());
		}

		setEnabled(list.isEnabled());
		if (index >= 0) {
			setFont(fonts[index]);
		}

		Border border = null;
		if (cellHasFocus) {
			if (isSelected) {
				border = DefaultLookup.getBorder(this, ui,
						"List.focusSelectedCellHighlightBorder");
			}
			if (border == null) {
				border = DefaultLookup.getBorder(this, ui,
						"List.focusCellHighlightBorder");
			}
		} else {
			// border = getNoFocusBorder();
		}
		setBorder(border);

		return this;
	}

}
