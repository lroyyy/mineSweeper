package ui;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.SystemColor;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import element.Resource;

import javax.swing.JLabel;

public class DialogAbout extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public DialogAbout(Frame owner) {
		super(owner);
		initLayout();
	}

	private void initLayout() {
		setTitle("关于");
		setModal(true);
		setType(Type.UTILITY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 211, 116);
		setLocationRelativeTo(null);// 居中
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelPic = new JPanel();
			panelPic.setBackground(SystemColor.activeCaption);
			contentPane.add(panelPic, BorderLayout.WEST);
			Resource tempMine=new Resource("graphics", "mine5");
			{				
				JLabel lblPic1 = new JLabel();
				lblPic1.setIcon(tempMine.getIcon());
				panelPic.add(lblPic1);
			}
			{
				JLabel lblPic2 = new JLabel();
				lblPic2.setIcon(tempMine.getIcon());
				panelPic.add(lblPic2);
			}
			{
				JLabel lblPic3 = new JLabel();
				lblPic3.setIcon(tempMine.getIcon());
				panelPic.add(lblPic3);
			}
		}
		{
			JPanel panelTxt = new JPanel();
			contentPane.add(panelTxt, BorderLayout.CENTER);
			{
				JLabel lblVersion = new JLabel("版本 1.1.20150603");
				panelTxt.add(lblVersion);
			}
		}
	}

}
