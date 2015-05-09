package au.id.cpd.eigenfaces.test;


import java.util.Calendar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import au.id.cpd.eigenfaces.data.*;
import au.id.cpd.eigenfaces.data.operators.*;
import au.id.cpd.algorithms.classifier.*;

public class TestImageModel {

	// 20x20 pixel image.
	private static String MAT_FILE = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/test_matrix_30x30.jmat";
	
	private static String FEATURE_MAT_FILE = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/test_features.jmat";
	private static String VECTOR_MAT_FILE = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/test_vectors.jmat";

	private static String RESULT_MAT_FILE = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/test_results.txt";

	private static String SEARCH_FILE = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/data/jules2.jpg";
	
	private static String LABEL_FILE = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/test_labels.ser";
	
	private static int WIDTH = 30;
	private static int HEIGHT = 30;
	
	private static int NUM_COMPONENTS = 10;
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLearn() {
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		IImageDataModel model = new ImageDataModel();
		assertTrue(model.loadStoredImageData(MAT_FILE));
		model.setNumberOfComponents(NUM_COMPONENTS);

		//assertTrue(model.loadImageDataLabels(LABEL_FILE));
		
		PrincipleComponentsClassifier classifier = new PrincipleComponentsClassifier();
		
		LearningAdapter learner = new LearningAdapter(model, model, classifier);
		
		assertTrue(learner.adapt() != null);
		assertTrue(model.saveFeatureMatrix(FEATURE_MAT_FILE));
		assertTrue(model.saveVectorMatrix(VECTOR_MAT_FILE));
		
		model.getFeatureMatrix().close();
		model.getVectorMatrix().close();
		model.getImageData().close();
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		System.out.println("learn took: " + (endTime - startTime) + " ms");

	}
	
	@Test
	public void testSearch() {
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		ImageDataModel model = new ImageDataModel(MAT_FILE, FEATURE_MAT_FILE, VECTOR_MAT_FILE, NUM_COMPONENTS);
		model.setNumberOfComponents(NUM_COMPONENTS);
		
		assertTrue(model.loadStoredImageData());
		assertTrue(model.loadImageDataLabels(LABEL_FILE));
		assertTrue(model.loadFeatureMatrix());
		assertTrue(model.loadVectorMatrix());
		
		PrincipleComponentsClassifier classifier = new PrincipleComponentsClassifier();
		
		SearchQuery query = new SearchQuery();
		assertTrue(query.loadQueryFromImageFile(SEARCH_FILE, WIDTH, HEIGHT));
		
		SearchAdapter searcher = new SearchAdapter(classifier, model, query);
		assertTrue(searcher.adapt() != null);
		
		model.getFeatureMatrix().close();
		model.getVectorMatrix().close();
		model.getImageData().close();
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		System.out.println("search took: " + (endTime - startTime) + " ms");

		System.out.println("Match Index: " + query.getIndex());
		System.out.println("Match Label: " + query.getLabel());
		System.out.println("Match Distance: " + query.getDistance());
		
	}
}
