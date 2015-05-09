package au.id.cpd.eigenfaces.test;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;
import au.id.cpd.algorithms.*;
import au.id.cpd.algorithms.classifier.*;
import au.id.cpd.algorithms.data.*;
import au.id.cpd.algorithms.data.io.*;
import au.id.cpd.eigenfaces.data.*;

public class TestPCM {

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
	
	private static int NUM_IMAGES = 86;
	
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLearn() {
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();
			// we already know the number of images in the directory.
			// we know there are 86 image files.
			MatrixBuffer<Double> mat = new MatrixBuffer<Double>(MAT_FILE);
			
			System.err.println("Loaded Data: " + mat.getRow(0));
			System.err.println("Loaded Data pos [0,0]: " + mat.get(0,0));
			
			
			PrincipleComponentsClassifier learner = new PrincipleComponentsClassifier();
			learner.setTrainingSet(mat);
			learner.setNumComponents(NUM_COMPONENTS);
			learner.learn();
			
			IMatrix<Double> tmp = learner.getFeatureMatrix();
			
			assertTrue(tmp != null);
			
			MatrixBuffer<Double> features = MatrixBuffer.CreateMatrixBuffer(tmp.getSize());
			for(int i=0;i<tmp.getSize().getRows();i++) {
				for(int j=0;j<tmp.getSize().getCols();j++) {
					features.set(i, j, tmp.get(i,j));
				}
			}
			
			features.copyFileTo(FEATURE_MAT_FILE);
			features.close();
			
			tmp = learner.getSortedEigenVectors();
			MatrixBuffer<Double> sortedVectors = MatrixBuffer.CreateMatrixBuffer(tmp.getSize());
			
			for(int i=0;i<tmp.getSize().getRows();i++) {
				for(int j=0;j<tmp.getSize().getCols();j++) {
					sortedVectors.set(i, j, tmp.get(i,j));
				}
			}
			
			sortedVectors.copyFileTo(VECTOR_MAT_FILE);
			sortedVectors.close();
			
			mat.close();
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			System.out.println("learn took: " + (endTime - startTime) + " ms");
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testSearch() {
	
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();

			// we already know the number of images in the directory.
			// we know there are 86 image files.
			MatrixBuffer<Double> mat = new MatrixBuffer<Double>(MAT_FILE);
			
			System.err.println("Matrix Loaded: " + mat.getRow(0));
			
			MatrixBuffer<Double> features = new MatrixBuffer<Double>(FEATURE_MAT_FILE);
			
			System.err.println("Loaded features: " + features.getRow(0));
			
			MatrixBuffer<Double> vectors = new MatrixBuffer<Double>(VECTOR_MAT_FILE);
			
			System.err.println("Loaded vectors: " + vectors.getRow(0));
			
			GrayscaleImageLoader loader = new GrayscaleImageLoader(WIDTH, HEIGHT);
			IMatrix<Double> query = loader.loadRowMatrix(SEARCH_FILE);
			//IMatrix<Double> query = image.reshape(1, WIDTH*HEIGHT);
			//IMatrix<Double> query = new Matrix<Double>(1,mat.getSize().getCols());//image.reshape(1, WIDTH*HEIGHT);
			//query.addAll(mat.getRow(2));
			
			PrincipleComponentsClassifier learner = new PrincipleComponentsClassifier();
			learner.setTrainingSet(mat);
			learner.setNumComponents(10);
			learner.setFeatureMatrix(features);
			learner.setSortedEigenVectors(vectors);
			IMatrix<Double> result = learner.search(query);
			
			IMatrix<Double> out = new Matrix<Double>(2, result.getSize().getCols());
			out.setRow(0, query.getRow(0));
			out.setRow(1, result.getRow(0));
			FileWriter fout = new FileWriter(RESULT_MAT_FILE);
			MatrixWriter writer = new MatrixWriter(fout);
			writer.writeMatrix(out, fout);
			writer.close();
			
			System.out.println("Query: \n" + query);
			System.out.println("Result Index: \n" + learner.getMatchIndex());
			
			TupleCollection<Integer, String> labels = readLabels(LABEL_FILE);
			if (labels != null) {
				for(ITuple<Integer, String> label : labels) {
				if (label.compareTo(learner.getMatchIndex()) == 0) {
					System.out.println("Result Label: " + label.getValue());
				}
				}
			}
			
			
			System.out.println("Result: \n" + result);
			
			
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			System.out.println("search took: " + (endTime - startTime) + " ms");

		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	
	}
	
	private void saveLabels(String file, ITupleCollection<Integer, String> labels) {
		try {
			FileOutputStream os = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(labels);
			oos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private TupleCollection<Integer, String>  readLabels(String file) {
		try {
			FileInputStream is = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(is);
			TupleCollection<Integer, String> labels = (TupleCollection<Integer, String>)ois.readObject();
			ois.close();
			return labels;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
