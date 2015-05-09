/**
 * 
 */
package au.id.cpd.eigenfaces.data;

import java.net.*;
import java.io.*;
import java.util.*;
import au.id.cpd.algorithms.data.*;
import au.id.cpd.algorithms.data.io.*;


/**
 * This utility class will load all images in a directory
 * and convert them into a matrix of 
 * (width*height)^2
 * @author cd
 *
 */
public class ImageDirectoryLoader {

	/**
	 * resize width
	 */
	private int width;
	/**
	 * resize height
	 */
	private int height;
	/**
	 * The directory path
	 */
	private String directory;
	/**
	 * The directory file instance.
	 */
	private File dirFile;
	/**
	 * The matrix representation of the image directory.
	 */
	private IMatrix<Double> mat;
	
	/**
	 * A mapping Integer x String of labels
	 */
	private ITupleCollection<Integer, String> labels;
	
	/**
	 * counters
	 */
	private int i;
	private int j;
	/**
	 * Default
	 *
	 */
	public ImageDirectoryLoader() {
		width = 100;
		height = 100;
	}
	
	/**
	 * Construct with width, height and directory path.
	 * @param w
	 * @param h
	 * @param dir
	 */
	public ImageDirectoryLoader(int w, int h, String dir) {
		width = w;
		height = h;
		directory = dir;
	}
	
	public IMatrix<Double> loadImages(int w, int h, String dir) {
		width = w;
		height = h;
		directory = dir;
		return loadImages();
	}
	
	/**
	 * Load all images in the supplied directory into a matrix in memory.
	 * Process each image by converting to grayscale and 
	 * scaling to the required with and height.
	 * The resulting Matrix is n images rows x w*h cols.
	 * Each image is flattened into a single row.
	 * @return Matrix containing resulting grayscale data.
	 */
	public IMatrix<Double> loadImages() {
		if (!readDirectory()) {
			return null;
		}
		labels= new TupleCollection<Integer, String>();
		String[] files = dirFile.list();
		Arrays.sort(files);
		List<File> list = new ArrayList<File>();
		for(String file : files) {
			String path = dirFile.getAbsolutePath() + dirFile.separator + file;
			File f = new File(path);
			if (f.isFile()) {
				list.add(f);
			}
		}
		// matrix num files rows x w*h cols.
		int dim = width*height;
		mat = MatrixBuffer.CreateMatrixBuffer(list.size(), dim);
		i = 0;
		for(File f : list) {
			byte[] data = readImage(f.getAbsolutePath());
			if (data != null) {
				if ( (data.length == width*height) && (i < list.size()) ) {
					ITuple<Integer, String> pair = new Tuple<Integer, String>();
					pair.setKey(i);
					pair.setValue(f.getName());
					labels.add(pair);
					appendMatrix(data);
				}
			}
		}
		return mat;
	}
	
	/**
	 * Append the row of data to the matrix.
	 * @param mat
	 * @param data
	 */
	private void appendMatrix(byte[] data) {
		for(int j=0;j<data.length;j++) {
			mat.set(i, j, new Double((int)data[j] & 0xff));
		}
		i++;
	}
	
	/**
	 * Load the directory into a file and read the contents.
	 * @return
	 */
	public boolean readDirectory() {
		try {
			dirFile = new File(directory);
			return dirFile.isDirectory();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * Read the image at the path into memory,
	 * convert it to grayscale and return its bytes.
	 * @param path
	 * @return
	 */
	public byte[] readImage(String path) {
		GrayscaleImageLoader loader = new GrayscaleImageLoader(width, height);
		return loader.loadBytes(path);
	}
	
	/**
	 * @return the directory
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * @param directory the directory to set
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
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

	/**
	 * @return the labels
	 */
	public ITupleCollection<Integer, String> getLabels() {
		return labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(ITupleCollection<Integer, String> labels) {
		this.labels = labels;
	}
	
}
