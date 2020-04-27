package util;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;

public class Utils {

	/**¸ù¾Ýindex·µ»ØJRadioButtonMenuItem*/
	public static JRadioButtonMenuItem getRadioButtonMenuItemByIndex (ButtonGroup btnGroup,int index){
		Enumeration<AbstractButton> e=btnGroup.getElements();
		JRadioButtonMenuItem mntm = null;
		for(int i=0;i<index;i++){
			mntm=(JRadioButtonMenuItem) e.nextElement();
		}
		return mntm;
	}
	public static JRadioButtonMenuItem getRadioButtonMenuItemByMnemonic (ButtonGroup btnGroup,int mnemonic){
		Enumeration<AbstractButton> e=btnGroup.getElements();
		JRadioButtonMenuItem mntm = null;
		for(int i=0;i<btnGroup.getButtonCount();i++){
			mntm=(JRadioButtonMenuItem) e.nextElement();
			if (mntm.getMnemonic()==mnemonic){
				return mntm;
			}
		}
		return null;		
	}
}
