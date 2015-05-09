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
public class CreateTask extends Task<Boolean, ImageDataLoader> {

    private Model model;
   
    public CreateTask(Application app) {
        super(app);
    }

    public CreateTask(
            Application app,
            Model m) {
        super(app);
        this.model = m;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {

            ImageDataLoader loader = new ImageDataLoader(model.getSourceDirectory().getAbsolutePath(), 
                    model.getDimension().width, model.getDimension().height);
            model.setData((MatrixBuffer<Double>) loader.adapt());
            model.getData().copyFileTo(model.getImageOutputFile().getAbsolutePath());
            model.getData().close();
            saveLabels(model.getLabelOutputFile().getAbsolutePath(), loader.getSource().getImageDataLabels());
            // train the PCM.
            boolean flag = learn();
            EigenfacesApplication.getApplication().getModel().setProcessed(flag);
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveLabels(String file, ITupleCollection<Integer, String> labels) throws Exception {
        FileOutputStream os = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(labels);
        oos.close();
    }

    private boolean learn() {
        long startTime = Calendar.getInstance().getTimeInMillis();

        IImageDataModel dataModel = new ImageDataModel();
        if (!dataModel.loadStoredImageData(model.getImageOutputFile().getAbsolutePath())) {
            return false;
        }
        dataModel.setNumberOfComponents(model.getNumComponents());

        //assertTrue(model.loadImageDataLabels(LABEL_FILE));

        PrincipleComponentsClassifier classifier = new PrincipleComponentsClassifier();

        LearningAdapter learner = new LearningAdapter(dataModel, dataModel, classifier);

        if (learner.adapt() == null) {
            return false;
        }
        dataModel.saveFeatureMatrix(model.getFeatureFile().getAbsolutePath());
        dataModel.saveVectorMatrix(model.getVectorFile().getAbsolutePath());

        dataModel.getFeatureMatrix().close();
        dataModel.getVectorMatrix().close();
        dataModel.getImageData().close();

        long endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("learn took: " + (endTime - startTime) + " ms");
        return true;
    }

    
}
