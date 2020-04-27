package ui;

import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import util.IniProcesser;
import element.GameManager;
import element.Resource;

import java.awt.GridLayout;

public class DialogSettings extends JDialog {
	private static final long serialVersionUID = 1L;
	private int flagIconsCount = 5;
	private int mineIconsCount = 13;
	private JComboBox<ImageIcon> comboBoxFlag;
	private JComboBox<ImageIcon> comboBoxMine;
	private ImageIcon[] flagIcons;
	private ImageIcon[] mineIcons;
	private FrameMain owner;
	private JComboBox<String> comboBoxFont;
//	private Font[] fonts;

	public DialogSettings(FrameMain owner) {
		this.owner = owner;
		setTitle("ÉèÖÃ");
		setType(Type.UTILITY);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(new Rectangle(300, 200));
		initIcons();
		initLayout();
		initListners();
		setLocationRelativeTo(owner);// ¾ÓÖÐ
	}

	private void initLayout() {
		getContentPane().setLayout(new GridLayout(3, 0, 0, 0));
		{
			JPanel panelFlag = new JPanel();
			panelFlag.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			getContentPane().add(panelFlag);
			{
				JLabel lblFlag = new JLabel("Æì");
				panelFlag.add(lblFlag);
			}
			{
				comboBoxFlag = new JComboBox<ImageIcon>();
				comboBoxFlag.setModel(new DefaultComboBoxModel<ImageIcon>(
						flagIcons));
				((JTextField) comboBoxFlag.getEditor()
						.getEditorComponent()).setHorizontalAlignment(SwingConstants.CENTER);
				comboBoxFlag.setSelectedIndex(Resource.flag.getIndex() - 1);
				panelFlag.add(comboBoxFlag);
			}
		}
		{
			JPanel panelMine = new JPanel();
			panelMine.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			getContentPane().add(panelMine);
			{
				JLabel lblMine = new JLabel("À×");
				panelMine.add(lblMine);
			}
			{
				comboBoxMine = new JComboBox<ImageIcon>();
				comboBoxMine.setModel(new DefaultComboBoxModel<ImageIcon>(
						mineIcons));
				comboBoxMine.setSelectedIndex(Resource.mine.getIndex() - 1);
				panelMine.add(comboBoxMine);
			}
		}
		{
			JPanel panelFont = new JPanel();
			getContentPane().add(panelFont);
			{
				JLabel lblFont = new JLabel("Êý×Ö");
				panelFont.add(lblFont);
			}
			{
				comboBoxFont = new JComboBox<String>();
				comboBoxFont.setRenderer(new MyComboboxItem(GameManager.getFonts()));
				JTextField textFieldFont=(JTextField) comboBoxFont.getEditor().getEditorComponent();
				textFieldFont.setColumns(10);
//				textFieldFont.setHorizontalAlignment(SwingConstants.CENTER);
				for(int i=0;i<GameManager.getFonts().length;i++){
					comboBoxFont.addItem((i+1)+"?0123456789");
				}
				comboBoxFont.setSelectedIndex(GameManager.getCellFontIndex());
				comboBoxFont.setFont(GameManager.getCellFont());
				panelFont.add(comboBoxFont);
			}
		}
	}

	private void initListners() {
		comboBoxFlag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = comboBoxFlag.getSelectedIndex() + 1;
				Resource.flag.setIndex(index);
				owner.getCellsContainer().updateCellsIcon();
				try {
					IniProcesser.WritePrivateProfileString(
							GameManager.getConfigPath(), "Settings",
							"flagIcon", Integer.toString(index));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		comboBoxMine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = comboBoxMine.getSelectedIndex() + 1;
				Resource.mine.setIndex(index);
				owner.getCellsContainer().updateCellsIcon();
				try {
					IniProcesser.WritePrivateProfileString(
							GameManager.getConfigPath(), "Settings",
							"mineIcon", Integer.toString(index));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		comboBoxFont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				int index = comboBoxFont.getSelectedIndex();
				GameManager.setCellFontIndex(index);
				comboBoxFont.setFont(GameManager.getCellFont());
				owner.getCellsContainer().setCellsFont(GameManager.getCellFont());
				try {
					IniProcesser.WritePrivateProfileString(
							GameManager.getConfigPath(), "Settings",
							"cellFont", Integer.toString(index));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void initIcons(){
		try {
			
		flagIcons = new ImageIcon[flagIconsCount];
		for (int i = 0; i < flagIcons.length; i++) {
			flagIcons[i] = new ImageIcon(ImageIO.read(new FileInputStream(
					"res/graphics/flag" + (i + 1) + ".png")));

		}
		mineIcons = new ImageIcon[mineIconsCount];
		for (int i = 0; i < mineIcons.length; i++) {
//			mineIcons[i] = ImageProcesser.scale(new ImageIcon(getClass().getResource(
//					"/graphics/mine" + (i + 1) + ".png")), 1);
			mineIcons[i] = new ImageIcon(ImageIO.read(new FileInputStream(
					"res/graphics/mine" + (i + 1) + ".png")));
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
