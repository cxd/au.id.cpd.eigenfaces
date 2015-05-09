/**
 * 
 */
package au.id.cpd.eigenfaces.data;

import au.id.cpd.algorithms.data.IMatrix;
import au.id.cpd.algorithms.data.Size;
import au.id.cpd.algorithms.data.TupleCollection;
import au.id.cpd.eigenfaces.data.operators.*;
import java.io.*;
/**
 * @author cd
 *
 */
public class ImageDataModel extends ImageDataSource implements IImageDataModel {

	static final long serialVersionUID = 8236124137861616788L;

	
	/**
	 * Feature matrix
	 */
	private IMatrix<Double> features;
	
	/**
	 * Eigen-vectors.
	 */
	private IMatrix<Double> vectors;
	
	/**
	 * eigen-vector file
	 */
	private String vectorFile;
	
	/**
	 * Feature matrix file.
	 */
	private String featuresFile;
	
	/**
	 * Number of components
	 */
	private int numComponents = 0;
	
	/**
	 * Default constructor.
	 */
	public ImageDataModel() {
		super();
	}
	
	/**
	 * Construct with
	 * @param d - source image directory
	 * @param w - resize width
	 * @param h - resize height
	 */
	public ImageDataModel(String d, int w, int h) {
		super(d, w, h);
	}
	
	/**
	 * Construct with
	 * @param d - image data file
	 * @param f - feature matrix file
	 * @param v - vector matrix file
	 * @param n - number of components
	 */
	public ImageDataModel(String d, String f, String v, int n) {
		super();
		setImageDataFile(d);
		featuresFile = f;
		vectorFile = v;
		numComponents = n;
	}
	
	public ImageDataModel(IImageDataSource s) {
		super();
		this.setDirectory(s.getDirectory());
		this.setHeight(s.getHeight());
		this.setWidth(s.getWidth());
		this.setImageData(s.getImageData());
		this.setImageDataFile(s.getImageDataFile());
		this.setImageDataLabels(s.getImageDataLabels());
		this.setImageLabelFile(s.getImageLabelFile());
	}
	
	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#loadFeatureMatrix()
	 */
	public boolean loadFeatureMatrix() {
		FeatureMatrixLoader loader = new FeatureMatrixLoader();
		loader.setDomain(this);
		features = loader.adapt();
		return (features != null);
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#loadFeatureMatrix(java.lang.String)
	 */
	public boolean loadFeatureMatrix(String file) {
		featuresFile = file;
		return loadFeatureMatrix();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#loadVectorMatrix()
	 */
	public boolean loadVectorMatrix() {
		VectorMatrixLoader loader = new VectorMatrixLoader();
		loader.setDomain(this);
		vectors = loader.adapt();
		return (vectors != null);
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#loadVectorMatrix(java.lang.String)
	 */
	public boolean loadVectorMatrix(String file) {
		vectorFile = file;
		return loadVectorMatrix();
	}

	
	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#getFeatureMatrix()
	 */
	public IMatrix<Double> getFeatureMatrix() {
		return features;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#getFeatureMatrixFile()
	 */
	public String getFeatureMatrixFile() {
		return featuresFile;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#getNumberOfComponents()
	 */
	public int getNumberOfComponents() {
		return numComponents;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#getVectorMatrix()
	 */
	public IMatrix<Double> getVectorMatrix() {
		return vectors;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#getVectorMatrixFile()
	 */
	public String getVectorMatrixFile() {
		return vectorFile;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#setFeatureMatrix(au.id.cpd.algorithms.data.IMatrix)
	 */
	public void setFeatureMatrix(IMatrix<Double> mat) {
		features = mat;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#setFeatureMatrixFile(java.lang.String)
	 */
	public void setFeatureMatrixFile(String f) {
		featuresFile = f;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#setNumberOfComponents(int)
	 */
	public void setNumberOfComponents(int n) {
		numComponents = n;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#setVectorMatrix(au.id.cpd.algorithms.data.IMatrix)
	 */
	public void setVectorMatrix(IMatrix<Double> mat) {
		vectors = mat;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#setVectorMatrixFile(java.lang.String)
	 */
	public void setVectorMatrixFile(String f) {
		vectorFile = f;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#saveFeatureMatrix()
	 */
	public boolean saveFeatureMatrix() {
		return features.save(featuresFile);
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#saveFeatureMatrix(java.lang.String)
	 */
	public boolean saveFeatureMatrix(String file) {
		featuresFile = file;
		return saveFeatureMatrix();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#saveVectorMatrix()
	 */
	public boolean saveVectorMatrix() {
		return vectors.save(vectorFile);
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.eigenfaces.data.IImageDataModel#saveVectorMatrix(java.lang.String)
	 */
	public boolean saveVectorMatrix(String file) {
		vectorFile = file;
		return saveVectorMatrix();
	}
}
