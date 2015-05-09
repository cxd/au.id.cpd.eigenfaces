/**
 * 
 */
package au.id.cpd.eigenfaces.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import au.id.cpd.eigenfaces.data.*;

/**
 * @author cd
 *
 */
public class TestImageLoaderAdapter {

	private static String FILE_NAME = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/data/amber1.gif";
	private static String DIRECTORY = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/data/";
	private static String MAT_FILE = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/test_matrix_30x30.jmat";
	private static String LABEL_FILE = "/Users/cd/workspace/au.id.cpd.algorithms/model/faces/test_labels.ser";
	
	private static int WIDTH = 30;
	private static int HEIGHT =30;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link au.id.cpd.eigenfaces.data.ImageDataSource#loadImageData(java.lang.String, int, int)}.
	 */
	@Test
	public void testLoadImageDataStringIntInt() {
		ImageDataSource data = new ImageDataSource(DIRECTORY, WIDTH, HEIGHT);
		assertTrue(data.loadImageData());
		assertTrue(data.saveImageData(MAT_FILE));
		assertTrue(data.saveImageDataLabels(LABEL_FILE));
	}

}
