package ui;

import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JDialog;

public abstract class MyDialog extends JDialog {
	protected ArrayList<JComponent> inputs;
	private static final long serialVersionUID = 1L;

	public MyDialog() {
		super();
		inputs = new ArrayList<JComponent>();
	}

	public MyDialog(Frame owner) {
		super(owner);
		inputs = new ArrayList<JComponent>();
	}

	public MyDialog(FrameMain owner, String string) {
		super(owner, string);
		inputs = new ArrayList<JComponent>();
	}

	public abstract boolean isEditValid();

	public void addInput(JComponent comp) {
		inputs.add(comp);
		return;
	}

}
