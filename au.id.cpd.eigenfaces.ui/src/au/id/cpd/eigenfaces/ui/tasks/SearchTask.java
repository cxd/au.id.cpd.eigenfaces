/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.id.cpd.eigenfaces.ui.tasks;

import java.awt.*;
import java.io.*;
import java.util.*;

import au.id.cpd.eigenfaces.ui.*;
import au.id.cpd.algorithms.data.*;
import au.id.cpd.algorithms.classifier.*;
import au.id.cpd.eigenfaces.data.*;
import au.id.cpd.eigenfaces.data.operators.*;
import org.jdesktop.application.*;

/**
 *
 * @author cd
 */
public class SearchTask extends Task<SearchQuery, ImageDataModel> {

    private Model model;
    
    private File searchFile;
    
    private IMatrix<Double> imageData;

    public SearchTask(Application app) {
        super(app);
    }

    public SearchTask(Application app, Model m, File search) {
        super(app);
        model = m;
        searchFile = search;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    
    public IMatrix<Double> getImageData() {
        return imageData;
    }

    @Override
    protected SearchQuery doInBackground() throws Exception {
        long startTime = Calendar.getInstance().getTimeInMillis();
        ImageDataModel dataModel = new ImageDataModel(
                model.getImageOutputFile().getAbsolutePath(), 
                model.getFeatureFile().getAbsolutePath(), 
                model.getVectorFile().getAbsolutePath(), 
                model.getNumComponents());
        dataModel.setNumberOfComponents(model.getNumComponents());

        dataModel.loadStoredImageData();
        dataModel.loadImageDataLabels(model.getLabelOutputFile().getAbsolutePath());
        dataModel.loadFeatureMatrix();
        dataModel.loadVectorMatrix();
        imageData = dataModel.getImageData();

        PrincipleComponentsClassifier classifier = new PrincipleComponentsClassifier();

        SearchQuery query = new SearchQuery();
        query.loadQueryFromImageFile(searchFile.getAbsolutePath(), model.getDimension().width, model.getDimension().height);

        SearchAdapter searcher = new SearchAdapter(classifier, dataModel, query);
        if (searcher.adapt() == null) return null;

        dataModel.getFeatureMatrix().close();
        dataModel.getVectorMatrix().close();
        dataModel.getImageData().close();

        long endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("search took: " + (endTime - startTime) + " ms");

        System.out.println("Match Index: " + query.getIndex());
        System.out.println("Match Label: " + query.getLabel());
        System.out.println("Match Distance: " + query.getDistance());
    
        return query;
        
    }
}
