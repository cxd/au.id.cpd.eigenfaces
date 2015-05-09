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
public class ImageDataLoader implements IConstrainedAdapter<IImageDataSource, IImageDataSource, IMatrix<Double> > {

	/**
	 * Image data source property.
	 */
	private IImageDataSource dataSource;
	
	/**
	 * A constrained loader that performs one task.
	 * Load data and populate the image data source.
	 * 
	 */
	public ImageDataLoader() {
		
	}
	
	/**
	 * Construct and define an image data source
	 * from within this object.
	 * @param d - directory
	 * @param w - width
	 * @param h - height
	 */
	public ImageDataLoader(String d, int w, int h) {
		dataSource = new ImageDataSource(d, w, h);
	}
	
	/**
	 * In order to load a data source we need to have
	 * specified width, height and string file for data source.
	 * @see au.id.cpd.algorithms.patterns.IConstraint#Apply(java.lang.Object)
	 */
	public boolean apply(IImageDataSource d) {
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
		boolean result = ( (dataSource.getWidth() != 0) &&
						(dataSource.getHeight() != 0) &&
						(dataSource.getDirectory() != null) );
		if (result) {
		File f = new File(dataSource.getDirectory());
		result = f.exists();
		}
		return result;
	}
	
	/**
	 * Domain property
	 * @return
	 */
	public IImageDataSource getDomain() {
		return dataSource;
	}
	
	
	/**
	 * Domain property.
	 * @param d
	 */
	public void setDomain(IImageDataSource d) {
		dataSource = d;
	}
	
	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt()
	 */
	public IMatrix<Double> adapt() {
		if (!apply(dataSource)) return null;
		ImageDirectoryLoader loader = new ImageDirectoryLoader(dataSource.getWidth(), dataSource.getHeight(), dataSource.getDirectory());
		MatrixBuffer<Double> data = (MatrixBuffer<Double>)loader.loadImages();
		dataSource.setImageData(data);
		dataSource.setImageDataLabels(loader.getLabels());
		return data;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt(java.lang.Object)
	 */
	public IMatrix<Double> adapt(IImageDataSource s) {
		dataSource = s;
		return adapt();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getProduct()
	 */
	public IMatrix<Double> getProduct() {
		if (dataSource != null)
			return dataSource.getImageData();
		return null;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getSource()
	 */
	public IImageDataSource getSource() {
		return dataSource;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setProduct(java.lang.Object)
	 */
	public void setProduct(IMatrix<Double> p) {
		if (dataSource != null)
			dataSource.setImageData(p);
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setSource(java.lang.Object)
	 */
	public void setSource(IImageDataSource s) {
		dataSource = s;
	}

}
