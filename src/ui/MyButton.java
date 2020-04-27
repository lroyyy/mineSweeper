package ui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

import javax.swing.DefaultButtonModel;
import javax.swing.JButton;

public class MyButton extends JButton {
	private static final long serialVersionUID = 1L;
	private MyDialog owner;

	public MyButton(final MyDialog owner) {
		super();
		initModel();
		this.owner = owner;
	}

	public MyButton(final MyDialog owner, String string) {
		super(string);
		initModel();
		this.owner = owner;
	}

	private void initModel() {
		setModel(new DefaultButtonModel() {
			private static final long serialVersionUID = 1L;
			public void setPressed(boolean b) {
				if ((isPressed() == b) || !isEnabled()) {
					return;
				}
				if(getActionCommand()!=null&getActionCommand().equals("confirm")){
					if(!owner.isEditValid()){
						return;
					}
				}
				if (b) {
					stateMask |= PRESSED;
				} else {
					stateMask &= ~PRESSED;
				}
				if (!isPressed() && isArmed()) {
					int modifiers = 0;
					AWTEvent currentEvent = EventQueue.getCurrentEvent();
					if (currentEvent instanceof InputEvent) {
						modifiers = ((InputEvent) currentEvent).getModifiers();
					} else if (currentEvent instanceof ActionEvent) {
						modifiers = ((ActionEvent) currentEvent).getModifiers();
					}
					fireActionPerformed(new ActionEvent(this,
							ActionEvent.ACTION_PERFORMED, getActionCommand(),
							EventQueue.getMostRecentEventTime(), modifiers));
				}
				fireStateChanged();
			}
		});
	}

}
