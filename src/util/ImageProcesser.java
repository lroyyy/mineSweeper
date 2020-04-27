package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图像处理
 * <p>
 * 都是静态方法
 * 
 * @version 0.0.0.120516
 * 
 */
public class ImageProcesser {
	/**
	 * 图像水平翻转
	 * 
	 * @param url
	 * @return ImageIcon
	 * 
	 */
	public static ImageIcon fliphorizontal(URL url) {// url to ImageIcon
		BufferedImage dstImage = null;
		try {
			BufferedImage sourceImage = ImageIO.read(url);
			AffineTransform transform = new AffineTransform(-1, 0, 0, 1,
					sourceImage.getWidth(), 0);// 水平翻转
			AffineTransformOp op = new AffineTransformOp(transform,
					AffineTransformOp.TYPE_BILINEAR);
			dstImage = op.filter(sourceImage, null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ImageIcon imageicon = new ImageIcon(dstImage);
		return imageicon;
	}

	/**
	 * 图像水平翻转
	 * 
	 * @param srcimgcion
	 * @return ImageIcon
	 */
	public static ImageIcon fliphorizontal(ImageIcon srcimgcion) {
		BufferedImage sourceImage = ImageProcesser.toBufferedImage(srcimgcion
				.getImage());
		// BufferedImage sourceImage =
		// processimage.toBufferedImage(srcimgcion.getImage());
		BufferedImage dstImage = null;
		// BufferedImage sourceImage = ImageIO.read(url);
		AffineTransform transform = new AffineTransform(-1, 0, 0, 1,
				sourceImage.getWidth(), 0);// 水平翻转
		AffineTransformOp op = new AffineTransformOp(transform,
				AffineTransformOp.TYPE_BILINEAR);
		dstImage = op.filter(sourceImage, null);
		ImageIcon imageicon = new ImageIcon(dstImage);
		return imageicon;
	}

	/**
	 * 图像缩放 - 参数指定目标图缩放比例。
	 * 
	 * @param url
	 *            源图像地址
	 * @param scale
	 *            图像的缩放比例
	 * @return 缩放后的ImageIcon对象。
	 */
	public static ImageIcon scale(ImageIcon srcimgcion, double scale) {
		BufferedImage srcImage = ImageProcesser.toBufferedImage(srcimgcion
				.getImage());
		ImageIcon desimgicon = null;
		// srcImage = ImageIO.read(url);
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.scale(scale, scale);

		AffineTransformOp affineTransformOp = new AffineTransformOp(
				affineTransform, null);

		int width = (int) ((double) srcImage.getWidth() * scale);
		int height = (int) ((double) srcImage.getHeight() * scale);
		BufferedImage dstImage = new BufferedImage(width, height,
				srcImage.getType());
		desimgicon = new ImageIcon(affineTransformOp.filter(srcImage, dstImage));
		return desimgicon;
	}

	public static void resize(File originalFile, File resizedFile,   
            int newWidth, float quality) throws IOException {   
  
        if (quality > 1) {   
            throw new IllegalArgumentException(   
                    "Quality has to be between 0 and 1");   
        }   
  
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());   
        Image i = ii.getImage();   
        Image resizedImage = null;   
  
        int iWidth = i.getWidth(null);   
        int iHeight = i.getHeight(null);   
  
        if (iWidth > iHeight) {   
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)   
                    / iWidth, Image.SCALE_SMOOTH);   
        } else {   
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,   
                    newWidth, Image.SCALE_SMOOTH);   
        }   
  
        // This code ensures that all the pixels in the image are loaded.   
        Image temp = new ImageIcon(resizedImage).getImage();   
  
        // Create the buffered image.   
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),   
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);   
  
        // Copy image to buffered image.   
        Graphics g = bufferedImage.createGraphics();   
  
        // Clear background and paint the image.   
        g.setColor(Color.white);   
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));   
        g.drawImage(temp, 0, 0, null);   
        g.dispose();   
  
        // Soften.   
        float softenFactor = 0.05f;   
        float[] softenArray = { 0, softenFactor, 0, softenFactor,   
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };   
        Kernel kernel = new Kernel(3, 3, softenArray);   
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);   
        bufferedImage = cOp.filter(bufferedImage, null);   
  
        // Write the jpeg to a file.   
        FileOutputStream out = new FileOutputStream(resizedFile);   
  
        // Encodes image as a JPEG data stream   
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
  
        JPEGEncodeParam param = encoder   
                .getDefaultJPEGEncodeParam(bufferedImage);   
  
        param.setQuality(quality, true);   
  
        encoder.setJPEGEncodeParam(param);   
        encoder.encode(bufferedImage);   
    } 
	public static ImageIcon scale(URL url, double scale) {
		ImageIcon desimgicon = null;
		BufferedImage srcImage;
		try {
			srcImage = ImageIO.read(url);
			AffineTransform affineTransform = new AffineTransform();
			affineTransform.scale(scale, scale);

			AffineTransformOp affineTransformOp = new AffineTransformOp(
					affineTransform, null);

			int width = (int) ((double) srcImage.getWidth() * scale);
			int height = (int) ((double) srcImage.getHeight() * scale);
			BufferedImage dstImage = new BufferedImage(width, height,
					srcImage.getType());
			desimgicon = new ImageIcon(affineTransformOp.filter(srcImage,
					dstImage));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return desimgicon;
	}

	/**
	 * 图像缩放 - 参数指定目标图固定大小。
	 * 
	 * @param url
	 *            源图像地址
	 * @param width
	 *            目标图像宽
	 * @param height
	 *            目标图像高
	 * @return 缩放后的ImageIcon对象。
	 */
	public static ImageIcon scale(ImageIcon srcimgcion, int width, int height) {
		BufferedImage srcImage = ImageProcesser.toBufferedImage(srcimgcion
				.getImage());
		ImageIcon desimgicon = null;
		// srcImage = ImageIO.read(url);
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.scale((double) width / (double) srcImage.getWidth(),
				(double) height / (double) srcImage.getHeight());

		AffineTransformOp affineTransformOp = new AffineTransformOp(
				affineTransform, null);

		// int width = (int) ((double) srcImage.getWidth() * scale);
		// int height = (int) ((double) srcImage.getHeight() * scale);
		BufferedImage dstImage = new BufferedImage(width, height,
				srcImage.getType());
		desimgicon = new ImageIcon(affineTransformOp.filter(srcImage, dstImage));
		return desimgicon;
	}

	// This method returns a buffered image with the contents of an image
	/**
	 * Image to BufferedImage
	 * */
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}
		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();
		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent
		// Pixels
		// boolean hasAlpha = hasAlpha(image);
		// Create a buffered image with a format that's compatible with the
		// screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			// if (hasAlpha) {
			// transparency = Transparency.BITMASK;
			// }
			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null),
					image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}
		if (bimage == null) {
			// Create a buffered image using the default color model
			// int type = BufferedImage.TYPE_INT_RGB;
			// if (hasAlpha) {
			int type = BufferedImage.TYPE_INT_ARGB;
			// }
			bimage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), type);
		}
		// Copy image to buffered image
		Graphics g = bimage.createGraphics();
		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}

	/**
	 * 图片变灰
	 * 
	 * @param url
	 * @return ImageIcon
	 */
	public static ImageIcon gray(URL url) {
		BufferedImage srcimg;
		ImageIcon desimgicon = null;
		try {
			srcimg = ImageIO.read(url);
			Image desimg = new ColorConvertOp(
					ColorSpace.getInstance(ColorSpace.CS_GRAY), null).filter(
					srcimg, null);
			desimgicon = new ImageIcon(desimg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return desimgicon;
	}

	/**
	 * 裁剪图像
	 */
	public static ImageIcon cut(URL url, int x, int y, int width, int height) {
		BufferedImage mm;
		BufferedImage child = null;
		try {
			mm = ImageIO.read(url);

			int cw = width;
			int ch = height;
			int pw = mm.getWidth();
			int ph = mm.getHeight();
			if (pw < x + width) {
				System.out.println("给出的参数超出原图片的范围！程序会自动减小宽度或高度");
				cw = pw - x;
			}
			if (ph < y + height) {
				System.out.println("给出的参数超出原图片的范围！程序会自动减小宽度或高度");
				ch = ph - y;
			}
			child = new BufferedImage(cw, ch, BufferedImage.TYPE_INT_ARGB);
			for (int i = 0; i < ch; i++) {
				for (int j = 0; j < cw; j++) {
					child.setRGB(j, i, mm.getRGB(x + j, y + i));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(child);
	}

}
