/**
 * 
 */
package au.id.cpd.eigenfaces.data;


import au.id.cpd.algorithms.data.*;
/**
 * @author cd
 *
 */
public interface IImageDataModel extends IImageDataSource {

	/**
	 * Load the feature matrix.
	 * @param file
	 * @return
	 */
	public boolean loadFeatureMatrix(String file);
	
	/**
	 * Load the feature matrix.
	 * @return
	 */
	public boolean loadFeatureMatrix();
	
	/**
	 * Load the feature matrix.
	 * @param file
	 * @return
	 */
	public boolean saveFeatureMatrix(String file);
	
	/**
	 * Load the feature matrix.
	 * @return
	 */
	public boolean saveFeatureMatrix();
	
	/**
	 * Load the vector matrix.
	 * @param file
	 * @return
	 */
	public boolean loadVectorMatrix(String file);
	
	/**
	 * Load the vector matrix.
	 * @return
	 */
	public boolean loadVectorMatrix();
	
	/**
	 * Load the vector matrix.
	 * @param file
	 * @return
	 */
	public boolean saveVectorMatrix(String file);
	
	/**
	 * Load the vector matrix.
	 * @return
	 */
	public boolean saveVectorMatrix();
	
	/**
	 * Number of principle components to use
	 * @return
	 */
	public int getNumberOfComponents();
	
	/**
	 * Number of principle components to use
	 * @param n
	 * @return
	 */
	public void setNumberOfComponents(int n);
	
	/**
	 * Vector matrix file
	 * @return
	 */
	public String getVectorMatrixFile();
	
	/**
	 * Vector matrix file.
	 * @param f
	 */
	public void setVectorMatrixFile(String f);
	
	/**
	 * Feature matrix file
	 * @return
	 */
	public String getFeatureMatrixFile();
	
	/**
	 * Feature matrix file
	 * @param f
	 */
	public void setFeatureMatrixFile(String f);
	
	/**
	 * The resulting feature matrix
	 * @return
	 */
	public IMatrix<Double> getFeatureMatrix();
	
	/**
	 * The resulting feature matrix
	 * @param mat
	 */
	public void setFeatureMatrix(IMatrix<Double> mat);
	
	/**
	 * The resulting vector matrix
	 * @return
	 */
	public IMatrix<Double> getVectorMatrix();
	
	/**
	 * The resulting vector matrix
	 * @param mat
	 */
	public void setVectorMatrix(IMatrix<Double> mat);
	
}
