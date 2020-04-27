package ui;
import java.text.DecimalFormat;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 * @author zhengfei.fjhg
 * 
 */
public class MyTextField extends JFormattedTextField {

	private static final long serialVersionUID = 1L;
	private int minNum = 0;
	private int maxNum = 0;

	public MyTextField() {
		super(DecimalFormat.getIntegerInstance());
		setInputVerifier(new FormattedTextFieldVerifier());
	}
	public MyTextField(int minNum,int maxNum) {		
		this();
		this.minNum=minNum;
		this.maxNum=maxNum;
	}

	class FormattedTextFieldVerifier extends InputVerifier {

		@Override
		public boolean verify(JComponent input) {
			MyTextField tf = (MyTextField) input;
			if (tf.isEditValid()) {
				DialogCostom.updateTxt();
				return true;
			} else {
				notice();
				return false;
			}
		}
	}

	
	@Override
	public boolean isEditValid() {
		if (super.isEditValid() == true) {
			if(getText().length()>9){
				return false;
			}
			int value = 0;
			try {
				value = Integer.parseInt(getText());
			} catch (NumberFormatException e) {
				return false;
			}			
			if (value >= minNum && value <= maxNum) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public void fix() {
		if (getText().equals("")) {
			return;
		}
		int resultNum = Integer.parseInt(getText());
		// 太小
		if (resultNum < minNum) {
			setText("" + minNum);
			return;
		}
		// 太大
		if (resultNum > maxNum) {
			setText("" + maxNum);
			return;
		}
	}
	public void setRange(int minNum,int maxNum){
		this.minNum=minNum;
		this.maxNum=maxNum;
		
	}

	public void notice() {			
		 JOptionPane.showMessageDialog(null,
		 getName()+"的值必须是数字，且在"+minNum+"和"+maxNum+"之间");		
	}

}
