/**
 * 
 */
package au.id.cpd.eigenfaces.data;

import java.util.*;
import au.id.cpd.algorithms.data.*;

/**
 * The search result contains
 * the resulting record that is associated with the query.
 * @author cd
 *
 */
public class SearchQuery {

	/**
	 * An image file used to retrieve the query.
	 */
	private String imageFile;
	
	/**
	 * Row vector of query data.
	 */
	private IMatrix<Double> query;
	
	/**
	 * Row vector of closest match data
	 */
	private IMatrix<Double> result;
	
	/**
	 * Distance from closest match.
	 */
	private double distance;
	
	/**
	 * Index of the closest match.
	 */
	private int index;
	
	/**
	 * Label corresponding to the closest match.
	 */
	private String label;
	
	/** 
	 * A set of distances corresponding to the image labels.
	 */
	private List<ITuple<String,Double>> distances;
	
	/**
	 * default
	 */
	public SearchQuery() {
		distances = new ArrayList<ITuple<String,Double>>();
	}
	
	/**
	 * Create with the query 
	 * @param query
	 */
	public SearchQuery(IMatrix<Double> q) {
		this();
		query = q;
	}
	
	/**
	 * Load a query from the supplied image file.
	 * @param f - file name of image
	 * @param width - width of image
	 * @param height - height of image.
	 * @return true if successful false otherwise.
	 */
	public boolean loadQueryFromImageFile(String f, int width, int height) {
		imageFile = f;
		GrayscaleImageLoader loader = new GrayscaleImageLoader(width, height);
		query = loader.loadRowMatrix(imageFile);
		return (query != null);
	}

	/**
	 * @return the query
	 */
	public IMatrix<Double> getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(IMatrix<Double> query) {
		this.query = query;
	}

	/**
	 * @return the result
	 */
	public IMatrix<Double> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(IMatrix<Double> result) {
		this.result = result;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the imageFile
	 */
	public String getImageFile() {
		return imageFile;
	}

	/**
	 * @param imageFile the imageFile to set
	 */
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	/**
	 * @return the distances
	 */
	public List<ITuple<String, Double>> getDistances() {
		return distances;
	}

	/**
	 * @param distances the distances to set
	 */
	public void setDistances(List<ITuple<String, Double>> distances) {
		this.distances = distances;
	}
	
	
	
}
