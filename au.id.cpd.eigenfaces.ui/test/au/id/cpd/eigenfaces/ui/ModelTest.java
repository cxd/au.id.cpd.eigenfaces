/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package au.id.cpd.eigenfaces.ui;

import au.id.cpd.algorithms.data.MatrixBuffer;
import java.awt.Dimension;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cd
 */
public class ModelTest {

    public ModelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadXmlFile method, of class Model.
     */
    @Test
    public void testBLoadXmlFile() {
        System.out.println("loadXmlFile");
        File xmlFile = new File("test_model.xml");
        Model result = Model.loadXmlFile(xmlFile);
        assertTrue("Model is null", result != null);
        assertTrue(result.getSourceDirectory().getName().indexOf("source") >= 0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of saveXmlFile method, of class Model.
     */
    @Test
    public void testASaveXmlFile() {
        System.out.println("saveXmlFile");
        Model source = new Model();
        File xmlFile = new File("test_model.xml");
        source.setDimension(new Dimension(8,8));
        source.setFeatureFile(new File("test_feature.jmat"));
        source.setImageOutputFile(new File("test_image.jmat"));
        source.setLabelOutputFile(new File("test_label.jmat"));
        source.setSourceDirectory(new File("source"));
        source.setModelOutputFile(xmlFile);
        source.setNumComponents(10);
        source.setVectorFile(new File("test_vector.jmat"));
        
        Model.saveXmlFile(source, xmlFile);
        assertTrue("XML File does not exist.", xmlFile.exists());
    }

}