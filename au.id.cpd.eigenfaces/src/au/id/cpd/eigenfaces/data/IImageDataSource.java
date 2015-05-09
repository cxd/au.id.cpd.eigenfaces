/**
 * 
 */
package au.id.cpd.eigenfaces.data;

import au.id.cpd.algorithms.data.*;
/**
 * @author cd
 *
 */
public interface IImageDataSource {

	/**
	 * Load image data from the source directory.
	 * @param d - directory
	 * @param w - resize width
	 * @param h - resize height
	 * @return
	 */
	public boolean loadImageData(String d, int w, int h);
	
	/**
	 * Load image data
	 * @return
	 */
	public boolean loadImageData();
	
	/**
	 * Load image data from disc.
	 * @param d - directory
	 * @param w - resize width
	 * @param h - resize height
	 * @return
	 */
	public boolean loadStoredImageData(String f);
	
	/**
	 * Load image data
	 * @return
	 */
	public boolean loadStoredImageData();
	
	
	/**
	 * Save resulting image data to file specified.
	 * @param f
	 * @return
	 */
	public boolean saveImageData(String f);
	
	/**
	 * Save image data
	 * @return
	 */
	public boolean saveImageData();
	
	/**
	 * Load image data labels from file specified
	 * @param f
	 * @return
	 */
	public boolean loadImageDataLabels(String f);
	
	/**
	 * Load the image data labels
	 * @return
	 */
	public boolean loadImageDataLabels();
	
	/**
	 * Save image data labels to the file specified
	 * @param f
	 * @return
	 */
	public boolean saveImageDataLabels(String f);
	
	/**
	 * Save image data labels
	 * @return
	 */
	public boolean saveImageDataLabels();
	
	/**
	 * Label file name property
	 * @return
	 */
	public String getImageLabelFile();
	
	/**
	 * Label file name property
	 * @param f
	 */
	public void setImageLabelFile(String f);
	
	/**
	 * Data file name property
	 * @return
	 */
	public String getImageDataFile();
	/**
	 * Data filename property
	 * @param f
	 */
	public void setImageDataFile(String f);
	
	/**
	 * Read only returns the image data size.
	 * @return
	 */
	public Size getImageDataSize();
	
	/**
	 * Source directory of image repository
	 * @return
	 */
	public String getDirectory();
	/**
	 * Set the directory repository
	 * @param d
	 */
	public void setDirectory(String d);
	
	/**
	 * Resize Height property
	 * @return
	 */
	public int getHeight();
	/**
	 * Resize height property
	 * @param h
	 */
	public void setHeight(int h);
	/**
	 * Resize with property
	 * @return
	 */
	public int getWidth();
	/**
	 * Resize with property
	 * @param w
	 */
	public void setWidth(int w);
	/**
	 * Image data
	 * @return
	 */
	public IMatrix<Double> getImageData();
	/**
	 * Image data property
	 * @param D
	 */
	public void setImageData(IMatrix<Double> D);
	/**
	 * Image label property
	 * @return
	 */
	public ITupleCollection<Integer, String> getImageDataLabels();
	/**
	 * Image label property
	 * @param labels
	 */
	public void setImageDataLabels(ITupleCollection<Integer, String> labels);
	
}
