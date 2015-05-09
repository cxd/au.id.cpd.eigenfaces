/**
 * 
 */
package au.id.cpd.eigenfaces.data;

import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.color.*;
import java.awt.geom.*;
import au.id.cpd.algorithms.data.*;

/**
 * A class to provide work of loading an image and converting
 * into a grey scale matrix of dimension n x n.
 * 
 * @author cd
 *
 */
public class GrayscaleImageLoader {

	/**
	 * default width for converted image.
	 */
	private int width = 100;
	/**
	 * Default height for converted image.
	 */
	private int height = 100;
	
	
	/**
	 * Default constructor.
	 *
	 */
	public GrayscaleImageLoader() {
		
	}
	
	/**
	 * Construct an image loader for the supplied width and height.
	 * @param w
	 * @param h
	 */
	public GrayscaleImageLoader(int w, int h) {
		width = w;
		height = h;
	}
	
	/**
	 * Load an image at the supplied file
	 * convert it into grayscale and return the grey scale matrix.
	 * Returns a matrix width x height of grayscale values between 0-255.
	 * @param file
	 * @return
	 */
	public IMatrix<Double> loadMatrix(String file) {
		IMatrix<Double> data = MatrixBuffer.CreateMatrixBuffer(width, height);
		byte[] bytes = loadBytes(file);
		if (bytes == null) return null;
		for(int i=0;i<bytes.length;i++) {
			data.add(new Double((int)bytes[i] & 0xff));
		}
		return data;
	}
	
	/**
	 * Load an image at the supplied file
	 * convert it into grayscale and return the grey scale matrix.
	 * Returns a matrix width x height of grayscale values between 0-255.
	 * @param file
	 * @return
	 */
	public IMatrix<Double> loadRowMatrix(String file) {
		IMatrix<Double> data = MatrixBuffer.CreateMatrixBuffer(1, width*height);
		byte[] bytes = loadBytes(file);
		if (bytes == null) return null;
		for(int i=0;i<bytes.length;i++) {
			data.set(0, i, new Double((int)bytes[i] & 0xff));
		}
		return data;
	}
	
	/**
	 * Load an image of the supplied file.
	 * Convert it to grayscale and return the gray scale bytes between 0-255.
	 * @param file
	 * @return
	 */
	public byte[] loadBytes(String file) {
		BufferedImage im = loadImage(file);
		if (im == null) return null;
		im = rescale(im, width, height);
		im = grayscale(im);
		byte[] data = ( (DataBufferByte)im.getRaster().getDataBuffer()).getData();
		im.flush();
		im = null;
		return data;
	}
	
	
	/**
	 * Rescale the supplied image to the supplied width and height.
	 * @param srcImage
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage rescale(BufferedImage srcImage, int w, int h) {
		Image im = srcImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = newImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(im, 0, 0, null);
		g.dispose();
		return newImage;
	}
	
	/**
	 * Convert the source buffered image to greyscale.
	 * @param srcImage
	 * @return
	 */
	public BufferedImage grayscale(BufferedImage srcImage) {
		BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		return op.filter(srcImage, null);
	}
	
	/** 
	 * Load an image for the supplied filename.
	 * @param file
	 * @return
	 */
	public BufferedImage loadImage(String file) {
		try {
			BufferedImage im = ImageIO.read(new File(file));
			return im;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
}
