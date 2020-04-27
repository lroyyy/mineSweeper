package ui;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalButtonUI;

import sun.swing.SwingUtilities2;

public class CellUI extends MetalButtonUI {

	protected void paintText(Graphics g, JComponent c, Rectangle textRect,
			String text) {
		AbstractButton b = (AbstractButton) c;
		FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);
		int mnemonicIndex = b.getDisplayedMnemonicIndex();
		/*** paint the text normally */
		g.setColor(b.getForeground());
		SwingUtilities2.drawStringUnderlineCharAt(c, g, text, mnemonicIndex,
				textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent()
						+ getTextShiftOffset());

	}

	protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
		AbstractButton b = (AbstractButton) c;
		ButtonModel model = b.getModel();
		Icon icon = b.getIcon();
		Icon tmpIcon = null;

		if (icon == null) {
			return;
		}
		Icon selectedIcon = null;

		/* the fallback icon should be based on the selected state */
		if (model.isSelected()) {
			selectedIcon = b.getSelectedIcon();
			if (selectedIcon != null) {
				icon = selectedIcon;
			}
		}

		if (!model.isEnabled()) {
			if (model.isSelected()) {
				tmpIcon =b.getSelectedIcon();
//				tmpIcon = b.getDisabledSelectedIcon();
				if (tmpIcon == null) {
					tmpIcon = selectedIcon;
				}
			}

			if (tmpIcon == null) {
				tmpIcon =b.getSelectedIcon();
//				tmpIcon = b.getDisabledIcon();
			}
		} else if (model.isPressed() && model.isArmed()) {
			tmpIcon = b.getPressedIcon();
			if (tmpIcon != null) {
				// revert back to 0 offset
				clearTextShiftOffset();
			}
		} else if (b.isRolloverEnabled() && model.isRollover()) {
			if (model.isSelected()) {
				tmpIcon = b.getRolloverSelectedIcon();
				if (tmpIcon == null) {
					tmpIcon = selectedIcon;
				}
			}

			if (tmpIcon == null) {
				tmpIcon = b.getRolloverIcon();
			}
		}

		if (tmpIcon != null) {
			icon = tmpIcon;
		}

		if (model.isPressed() && model.isArmed()) {
			icon.paintIcon(c, g, iconRect.x + getTextShiftOffset(), iconRect.y
					+ getTextShiftOffset());
		} else {
			icon.paintIcon(c, g, iconRect.x, iconRect.y);
		}
	}
}
