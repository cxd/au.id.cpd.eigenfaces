/**
 * 
 */
package au.id.cpd.eigenfaces.data;

import java.io.*;

import au.id.cpd.algorithms.data.*;
import au.id.cpd.eigenfaces.data.operators.*;

/**
 * @author cd
 *
 */
public class ImageDataSource implements IImageDataSource, Serializable {

	static final long serialVersionUID = 7565792845901498290L;
	
	/**
	 * The source directory.
	 */
	private String directory;
	
	/**
	 * Resize height
	 */
	private int height;
	/**
	 * Resize width
	 */
	private int width;
	
	/**
	 * Resulting image data.
	 */
	private transient IMatrix<Double> imageData;
	/**
	 * path to image data file.
	 */
	private String imageFile;
	
	/**
	 * A collection of file labels for image data indexes
	 */
	private transient ITupleCollection<Integer, String> labels;
	
	/**
	 * Size of image data.
	 */
	private Size imageDataSize;
	
	/**
	 * Filename of image label file.
	 */
	private String imageLabelFile;
	
	/**
	 * Default
	 */
	public ImageDataSource() {	
	}
	/**
	 * Construct with 
	 * @param d - directory
	 * @param w - resize width
	 * @param h - resize height
	 */
	public ImageDataSource(String d, int w, int h) {
		directory = d;
		width = w;
		height = h;
	}
	
	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#getDirectory()
	 */
	public String getDirectory() {
		return directory;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#getImageData()
	 */
	public IMatrix<Double> getImageData() {
		return imageData;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#getImageDataFile()
	 */
	public String getImageDataFile() {
		return imageFile;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#getImageDataLabels()
	 */
	public ITupleCollection<Integer, String> getImageDataLabels() {
		return labels;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#getImageDataSize()
	 */
	public Size getImageDataSize() {
		return imageDataSize;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#getImageLabelFile()
	 */
	public String getImageLabelFile() {
		return imageLabelFile;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#loadImageData(java.lang.String, int, int)
	 */
	public boolean loadImageData(String d, int w, int h) {
		directory = d;
		width = w;
		height = h;
		return loadImageData();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#loadImageData()
	 */
	public boolean loadImageData() {
		ImageDataLoader loader = new ImageDataLoader();
		loader.adapt(this);
		if (this.imageData != null) {
			imageDataSize = imageData.getSize();
			return true;
		}
		return false;
	}

	/**
	 * Load image data from disc.
	 * @param d - directory
	 * @param w - resize width
	 * @param h - resize height
	 * @return
	 */
	public boolean loadStoredImageData(String f) {
		imageFile = f;
		return loadStoredImageData();
	}
	
	/**
	 * Load image data
	 * @return
	 */
	public boolean loadStoredImageData() {
		try {
			imageData = new MatrixBuffer<Double>(imageFile);
			return (imageData != null);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#loadImageDataLabels(java.lang.String)
	 */
	public boolean loadImageDataLabels(String f) {
		imageLabelFile = f;
		return loadImageDataLabels();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#loadImageDataLabels()
	 */
	public boolean loadImageDataLabels() {
		LabelDeserializer s = new LabelDeserializer(imageLabelFile);
		labels = s.adapt();
		return (labels != null);
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#saveImageData(java.lang.String)
	 */
	public boolean saveImageData(String f) {
		imageFile = f;
		return saveImageData();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#saveImageData()
	 */
	public boolean saveImageData() {
		return imageData.save(imageFile);
	}
	
	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#saveImageDataLabels(java.lang.String)
	 */
	public boolean saveImageDataLabels(String f) {
		imageLabelFile = f;
		return saveImageDataLabels();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#saveImageDataLabels()
	 */
	public boolean saveImageDataLabels() {
		try {
			LabelSerializer s = new LabelSerializer(imageLabelFile, labels);
			ObjectOutputStream oos = s.adapt();
			if (oos != null) {
				oos.close();
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#setDirectory(java.lang.String)
	 */
	public void setDirectory(String d) {
		directory = d;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#setHeight(int)
	 */
	public void setHeight(int h) {
		height = h;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#setImageData(au.id.cpd.algorithms.data.IMatrix)
	 */
	public void setImageData(IMatrix<Double> D) {
		imageData = D;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#setImageDataFile(java.lang.String)
	 */
	public void setImageDataFile(String f) {
		imageFile = f;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#setImageDataLabels(au.id.cpd.algorithms.data.TupleCollection)
	 */
	public void setImageDataLabels(ITupleCollection<Integer, String> labels) {
		this.labels = labels;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#setImageLabelFile(java.lang.String)
	 */
	public void setImageLabelFile(String f) {
		imageLabelFile = f;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataSource#setWidth(int)
	 */
	public void setWidth(int w) {
		width = w;
	}

}
