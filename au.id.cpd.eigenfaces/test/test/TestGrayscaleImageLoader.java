package au.id.cpd.eigenfaces.test;

import java.io.*;

import au.id.cpd.algorithms.data.*;
import au.id.cpd.algorithms.data.io.*;
import au.id.cpd.eigenfaces.data.*;
import au.id.cpd.eigenfaces.data.operators.*;
import junit.framework.TestCase;

public class TestGrayscaleImageLoader extends TestCase {

	private static String FILE_NAME = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/data/amber1.gif";
	private static String DIRECTORY = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/data/";
	private static String MAT_FILE = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/test_matrix_30x30.jmat";
	private static String LABEL_FILE = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/test_labels.ser";
	
	private static int WIDTH = 30;
	private static int HEIGHT =30;
	
	protected void setUp() throws Exception {
	}

	public void testLoadBytes() {
		GrayscaleImageLoader loader = new GrayscaleImageLoader(WIDTH, HEIGHT);
		byte[] bytes = loader.loadBytes(FILE_NAME);
		assertTrue(bytes != null);
		System.out.println("Length: " + bytes.length);
	}
	
	public void testLoadMatrix() {
		GrayscaleImageLoader loader = new GrayscaleImageLoader(WIDTH, HEIGHT);
		IMatrix<Double> m = loader.loadMatrix(FILE_NAME);
		assertTrue(m != null);
		System.out.println("Size: " + m.getSize());
		//System.out.println(m);
	}
	
	public void testLoadDirectory() {
		ImageDataLoader loader = new ImageDataLoader(DIRECTORY, WIDTH, HEIGHT);
		MatrixBuffer<Double> data = (MatrixBuffer<Double>)loader.adapt();
		assertTrue(data != null);
		try {
			
			System.err.println("Data: " + data.getRow(0));
			
			System.err.println("Data 0,0: " + data.get(0,0));
			
			data.copyFileTo(MAT_FILE);
			
			data.close();
			
			saveLabels(LABEL_FILE, loader.getSource().getImageDataLabels());
			
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
	

}
