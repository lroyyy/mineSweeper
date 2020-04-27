package element;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Resource {

	private String name;
	private int index;
	private String type;
	public static Resource flag;
	public static Resource mine;
	public static Resource cryFace;
	public static Resource surpriseFace;
	public static Resource sosoFace;
	public static Resource happyFace;
	public static Resource bomb;

	public Resource(String type, String name) {
		this(type, name, 0);
	}

	public Resource(String type, String name, int index) {
		this.type = type;
		this.name = name;
		this.index = index;
	}

	// public URL getURL() {
	// if(index!=0){
	// return getClass()
	// .getResource("/" + type + "/" + name + index + ".png");
	// }else{
	// return getClass()
	// .getResource("/" + type + "/" + name+ ".png");
	// }
	// }

	public ImageIcon getIcon() {
		String indexString=index==0?"":String.valueOf(index);
		ImageIcon imageIcon=null;
		try {
			imageIcon=new ImageIcon(ImageIO.read(new FileInputStream("res/" + type + "/" + name + indexString + ".png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageIcon;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
