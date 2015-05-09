/**
 * 
 */
package au.id.cpd.eigenfaces.data.operators;

import au.id.cpd.algorithms.patterns.*;
import au.id.cpd.algorithms.classifier.*;
import au.id.cpd.algorithms.data.IMatrix;
import au.id.cpd.algorithms.data.MatrixBuffer;
import au.id.cpd.eigenfaces.data.*;;


/**
 * A learning adapter performs the task
 * of training the principle components classifier.
 * @author cd
 *
 */
public class LearningAdapter implements IConstrainedAdapter<IImageDataSource, PrincipleComponentsClassifier, IImageDataModel> {

	/**
	 * An instance of the principle components classifier.
	 */
	private PrincipleComponentsClassifier learner;
	/**
	 * The image data source.
	 */
	private IImageDataSource data;
	
	/**
	 * The resulting image data model.
	 */
	private IImageDataModel model;
	
	public LearningAdapter() {
		
	}
	
	/**
	 * Construct with
	 * @param s - image data source
	 * @param c - classifier to use.
	 */
	public LearningAdapter(IImageDataSource s, PrincipleComponentsClassifier c) {
		data = s;
		learner = c;
	}
	
	/**
	 * Construct with
	 * @param s - image data source
	 * @param c - classifier to use.
	 */
	public LearningAdapter(IImageDataSource s, IImageDataModel m, PrincipleComponentsClassifier c) {
		data = s;
		model = m;
		learner = c;
	}
	
	/**
	 * Use the learning adapter to train the principle components classifier.
	 * (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt()
	 */
	public IImageDataModel adapt() {
		if (!apply())
			return null;
		if (model == null) {
			model = new ImageDataModel(data);
		}
		learner.setTrainingSet(data.getImageData());
		learner.setNumComponents(model.getNumberOfComponents());
		learner.learn();
		
		IMatrix<Double> tmp = learner.getFeatureMatrix();
		
		MatrixBuffer<Double> features = MatrixBuffer.CreateMatrixBuffer(tmp.getSize());
		for(int i=0;i<tmp.getSize().getRows();i++) {
			for(int j=0;j<tmp.getSize().getCols();j++) {
				features.set(i, j, tmp.get(i,j));
			}
		}
		
		model.setFeatureMatrix(features);
		
		tmp = learner.getSortedEigenVectors();
		MatrixBuffer<Double> sortedVectors = MatrixBuffer.CreateMatrixBuffer(tmp.getSize());
		
		for(int i=0;i<tmp.getSize().getRows();i++) {
			for(int j=0;j<tmp.getSize().getCols();j++) {
				sortedVectors.set(i, j, tmp.get(i,j));
			}
		}
		model.setVectorMatrix(sortedVectors);
		
		return model;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt(java.lang.Object)
	 */
	public IImageDataModel adapt(PrincipleComponentsClassifier s) {
		learner = s;
		return adapt();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getProduct()
	 */
	public IImageDataModel getProduct() {
		return model;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getSource()
	 */
	public PrincipleComponentsClassifier getSource() {
		return learner;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setProduct(java.lang.Object)
	 */
	public void setProduct(IImageDataModel p) {
		model = p;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setSource(java.lang.Object)
	 */
	public void setSource(PrincipleComponentsClassifier s) {
		learner = s;
	}

	/** 
	 * Learning will not take place unless the data source
	 * has a set of images to learn from.
	 * @see au.id.cpd.algorithms.patterns.IConstraint#apply()
	 */
	public boolean apply() {
		if (data == null) return false;
		return (data.getImageData() != null);
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#apply(java.lang.Object)
	 */
	public boolean apply(IImageDataSource domain) {
		data = domain;
		return apply();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#getDomain()
	 */
	public IImageDataSource getDomain() {
		return data;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#setDomain(java.lang.Object)
	 */
	public void setDomain(IImageDataSource d) {
		data = d;
	}
}
