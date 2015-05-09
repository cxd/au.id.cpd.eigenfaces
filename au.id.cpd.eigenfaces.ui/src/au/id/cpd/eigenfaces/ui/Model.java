/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.id.cpd.eigenfaces.ui;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.xml.bind.*;

import au.id.cpd.algorithms.data.*;
import au.id.cpd.algorithms.classifier.*;
import au.id.cpd.eigenfaces.data.*;
import au.id.cpd.eigenfaces.data.operators.*;

/**
 * Model contains current view state.
 * @author cd
 */
public class Model implements Serializable {

    private transient File sourceDirectory;
    private transient File imageOutputFile;
    private transient File modelOutputFile;
    private transient File labelOutputFile;
    private transient File featureFile;
    private transient File vectorFile;
    private Dimension dimension;
    private int numComponents;
    private transient MatrixBuffer<Double> data;
    private transient boolean processed;
    private static JAXBContext ctx;
    

    static {
        try {
            ctx = JAXBContext.newInstance(au.id.cpd.eigenfaces.ui.xml.Model.class.getPackage().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Model() {
        processed = false;
    }

    public Model(
            String srcDirectory,
            String imageFile,
            String labelFile,
            String featFile,
            String vecFile) {
        sourceDirectory = new File(srcDirectory);
        imageOutputFile = new File(imageFile);
        labelOutputFile = new File(labelFile);
        featureFile = new File(featFile);
        vectorFile = new File(vecFile);
    }

    public Model(
            File sourceDirectory,
            File imageOutputFile,
            File modelOutputFile,
            File labelOutputFile,
            File featureOutputFile,
            File vectorOutputFile,
            Dimension dim,
            int numComponents) {
        this();
        this.sourceDirectory = sourceDirectory;
        this.imageOutputFile = imageOutputFile;
        this.modelOutputFile = modelOutputFile;
        this.labelOutputFile = labelOutputFile;
        this.featureFile = featureOutputFile;
        this.vectorFile = vectorOutputFile;
        this.dimension = dim;
        this.numComponents = numComponents;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public File getFeatureFile() {
        return featureFile;
    }

    public void setFeatureFile(File featureFile) {
        this.featureFile = featureFile;
    }

    public int getNumComponents() {
        return numComponents;
    }

    public void setNumComponents(int numComponents) {
        this.numComponents = numComponents;
    }

    public File getVectorFile() {
        return vectorFile;
    }

    public void setVectorFile(File vectorFile) {
        this.vectorFile = vectorFile;
    }

    public MatrixBuffer<Double> getData() {
        return data;
    }

    public void setData(MatrixBuffer<Double> data) {
        this.data = data;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public File getImageOutputFile() {
        return imageOutputFile;
    }

    public void setImageOutputFile(File imageOutputFile) {
        this.imageOutputFile = imageOutputFile;
    }

    public File getLabelOutputFile() {
        return labelOutputFile;
    }

    public void setLabelOutputFile(File labelOutputFile) {
        this.labelOutputFile = labelOutputFile;
    }

    public File getModelOutputFile() {
        return modelOutputFile;
    }

    public void setModelOutputFile(File modelOutputFile) {
        this.modelOutputFile = modelOutputFile;
    }

    public File getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(File sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    /**
     * Serialize to xml file.
     * TODO: debug xml serialization.
     * @param xmlFile
     * @return
     */
    public static Model loadXmlFile(File xmlFile) {
        try {
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            au.id.cpd.eigenfaces.ui.xml.Model model = (au.id.cpd.eigenfaces.ui.xml.Model) unmarshaller.unmarshal(xmlFile);
            Model result = new Model(
                    model.getSourceDirectory(),
                    model.getImageOutputFile(),
                    model.getLabelOutputFile(),
                    model.getFeatureOutputFile(),
                    model.getVectorOutputFile());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read from xml serialized file.
     * TODO: debug xml serialization.
     * @param source
     * @param xmlFile
     */
    public static void saveXmlFile(Model source, File xmlFile) {
        try {
            Marshaller marshaller = ctx.createMarshaller();
            au.id.cpd.eigenfaces.ui.xml.Model model = (new au.id.cpd.eigenfaces.ui.xml.ObjectFactory()).createModel();
            model.setSourceDirectory(source.getSourceDirectory().getAbsolutePath());
            model.setImageOutputFile(source.getImageOutputFile().getAbsolutePath());
            model.setLabelOutputFile(source.getLabelOutputFile().getAbsolutePath());
            model.setFeatureOutputFile(source.getFeatureFile().getAbsolutePath());
            model.setVectorOutputFile(source.getVectorFile().getAbsolutePath());
            FileOutputStream fout = new FileOutputStream(xmlFile);
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(model, fout);
            fout.flush();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
