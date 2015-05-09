/**
 * 
 */
package au.id.cpd.eigenfaces.data.operators;

import java.util.*;
import au.id.cpd.algorithms.data.*;
import au.id.cpd.algorithms.patterns.*;
import au.id.cpd.algorithms.classifier.*;
import au.id.cpd.algorithms.data.ITuple;
import au.id.cpd.eigenfaces.data.*;

/**
 * @author cd
 *
 */
public class SearchAdapter implements IConstrainedAdapter<PrincipleComponentsClassifier, IImageDataModel, SearchQuery> {

	/**
	 * An instance of the principle components classifier.
	 */
	private PrincipleComponentsClassifier learner;
	
	/**
	 * The resulting image data model.
	 */
	private IImageDataModel model;
	
	/**
	 * The query instance.
	 */
	private SearchQuery query;
	
	public SearchAdapter() {
		
	}
	
	/**
	 * Create a search adapter with required components.
	 * @param c
	 * @param m
	 * @param q
	 */
	public SearchAdapter(PrincipleComponentsClassifier c, IImageDataModel m, SearchQuery q) {
		learner = c;
		model = m;
		query = q;
	}
	
	
	
	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt()
	 */
	public SearchQuery adapt() {
		if (!apply())
			return null;
		learner.setTrainingSet(model.getImageData());
		learner.setFeatureMatrix(model.getFeatureMatrix());
		learner.setSortedEigenVectors(model.getVectorMatrix());
		learner.setNumComponents(model.getNumberOfComponents());
		
		query.setResult(learner.search(query.getQuery()));
		query.setIndex(learner.getMatchIndex());
		query.setDistance(learner.getMatchDistance());
		
		List<ITuple<Integer,String>> labelSet = new ArrayList<ITuple<Integer,String>>();
		for(ITuple<Integer, String> label : model.getImageDataLabels()) {
			labelSet.add(label);
			if (label.compareTo(learner.getMatchIndex()) == 0) {
				query.setLabel(label.getValue());
			}
		}
		
		IMatrix<Double> distances = learner.getDistanceTable();
		query.getDistances().clear();
		for(int i=0;i<distances.getSize().getRows();i++) {
			ITuple<String,Double> tuple = new Tuple<String,Double>(labelSet.get(i).getValue(), distances.get(i, 0).doubleValue());
			query.getDistances().add(tuple);
		}
		
		return query;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt(java.lang.Object)
	 */
	public SearchQuery adapt(IImageDataModel s) {
		model = s;
		return adapt();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getProduct()
	 */
	public SearchQuery getProduct() {
		return query;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getSource()
	 */
	public IImageDataModel getSource() {
		return model;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setProduct(java.lang.Object)
	 */
	public void setProduct(SearchQuery p) {
		query = p;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setSource(java.lang.Object)
	 */
	public void setSource(IImageDataModel s) {
		model = s;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#apply()
	 */
	public boolean apply() {
		boolean result = ( (learner != null) &&
							( model != null) &&
							(query != null) );
		if (!result) 
			return false;
		
		result = ( (query.getQuery() != null) &&
				   (model.getFeatureMatrix() != null) &&
				   (model.getVectorMatrix() != null) &&
				   (model.getImageData() != null) );
		
		return result;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#apply(java.lang.Object)
	 */
	public boolean apply(PrincipleComponentsClassifier domain) {
		learner = domain;
		return apply();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#getDomain()
	 */
	public PrincipleComponentsClassifier getDomain() {
		return learner;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#setDomain(java.lang.Object)
	 */
	public void setDomain(PrincipleComponentsClassifier d) {
		learner = d;
	}

}
