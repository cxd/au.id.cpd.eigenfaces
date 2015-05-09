/**
 * 
 */
package au.id.cpd.eigenfaces.data.operators;

import java.io.*;
import au.id.cpd.algorithms.data.*;
import au.id.cpd.algorithms.patterns.*;
import au.id.cpd.eigenfaces.data.*;
/**
 * @author cd
 *
 */
public class VectorMatrixLoader implements IConstrainedAdapter<IImageDataModel, IImageDataModel, IMatrix<Double> > {

	/**
	 * Image data source property.
	 */
	private IImageDataModel dataSource;
	
	/**
	 * A constrained loader that performs one task.
	 * Load data and populate the image data source.
	 * 
	 */
	public VectorMatrixLoader() {
		
	}
	
	/**
	 * Construct and define an image data source
	 * from within this object.
	 * @param d - directory
	 * @param w - width
	 * @param h - height
	 */
	public VectorMatrixLoader(String d, String f, String v, int n) {
		dataSource = new ImageDataModel(d, f, v, n);
	}
	
	/**
	 * In order to load a data source we need to have
	 * specified width, height and string file for data source.
	 * @see au.id.cpd.algorithms.patterns.IConstraint#Apply(java.lang.Object)
	 */
	public boolean apply(IImageDataModel d) {
		if (d == null) return false;
		dataSource = d;
		return apply();
	}

	/**
	 * Apply Constraint C
	 * implies the Domain D is already defined.
	 * @return
	 */
	public boolean apply() {
		boolean result = (dataSource.getVectorMatrixFile() != null);
		if (result) {
		File f = new File(dataSource.getVectorMatrixFile());
		result = f.exists();
		}
		return result;
	}
	
	/**
	 * Domain property
	 * @return
	 */
	public IImageDataModel getDomain() {
		return dataSource;
	}
	
	
	/**
	 * Domain property.
	 * @param d
	 */
	public void setDomain(IImageDataModel d) {
		dataSource = d;
	}
	
	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt()
	 */
	public IMatrix<Double> adapt() {
		if (!apply(dataSource)) return null;
		try {
			MatrixBuffer<Double> mat = new MatrixBuffer<Double>(dataSource.getVectorMatrixFile());
			dataSource.setVectorMatrix(mat);
			return mat;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt(java.lang.Object)
	 */
	public IMatrix<Double> adapt(IImageDataModel s) {
		dataSource = s;
		return adapt();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getProduct()
	 */
	public IMatrix<Double> getProduct() {
		if (dataSource != null)
			return dataSource.getVectorMatrix();
		return null;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getSource()
	 */
	public IImageDataModel getSource() {
		return dataSource;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setProduct(java.lang.Object)
	 */
	public void setProduct(IMatrix<Double> p) {
		if (dataSource != null)
			dataSource.setVectorMatrix(p);
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setSource(java.lang.Object)
	 */
	public void setSource(IImageDataModel s) {
		dataSource = s;
	}

}
