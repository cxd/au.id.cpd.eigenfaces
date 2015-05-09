/*
 * ImageModelPanel.java
 *
 * Created on July 26, 2008, 3:18 PM
 */
package au.id.cpd.eigenfaces.ui;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import org.jdesktop.application.*;

import au.id.cpd.eigenfaces.ui.tasks.*;
import au.id.cpd.eigenfaces.data.*;

/**
 *
 * @author  cd
 */
public class ImageModelPanel extends javax.swing.JPanel implements TaskListener<Boolean, ImageDataModel> {

    private File sourceDirectory;
    private File imageOutputFile;
    private File modelOutputFile;
    private File labelOutputFile;
    private File featureOutputFile;
    private File vectorOutputFile;
    private boolean createEnabled;
    private boolean openEnabled;

    /** Creates new form ImageModelPanel */
    public ImageModelPanel() {
        initComponents();
        createEnabled = false;
        openEnabled = false;
    }

    @org.jdesktop.application.Action
    public void chooseSourceDirectory() {
        if (directoryChooser.showDialog(this, "OK") == JFileChooser.APPROVE_OPTION) {
            sourceDirectoryField.setText(directoryChooser.getSelectedFile().getPath());
            sourceDirectory = directoryChooser.getSelectedFile();
        }
    }

    @org.jdesktop.application.Action
    public void chooseDataFile() {
        if (openEnabled) {
            dataFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        }
        if (dataFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            imageOutputFile = dataFileChooser.getSelectedFile();
            exportFileName.setText(imageOutputFile.getPath());
        }
    }

    @org.jdesktop.application.Action
    public void chooseModelFile() {
        if (openEnabled) {
            modelFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        }
        if (modelFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            modelOutputFile = modelFileChooser.getSelectedFile();
            modelFileField.setText(modelOutputFile.getPath());

            if (modelOutputFile.exists()) {
                Model model = Model.loadXmlFile(modelOutputFile);
                sourceDirectoryField.setText(model.getSourceDirectory().getAbsolutePath());
                sourceDirectory = model.getSourceDirectory();
                exportFileName.setText(model.getImageOutputFile().getAbsolutePath());
                imageOutputFile = model.getImageOutputFile();
                labelFileField.setText(model.getLabelOutputFile().getAbsolutePath());
                labelOutputFile = model.getLabelOutputFile();
                featureFileField.setText(model.getFeatureFile().getAbsolutePath());
                featureOutputFile = model.getFeatureFile();
                vectorFileField.setText(model.getVectorFile().getAbsolutePath());
                vectorOutputFile = model.getVectorFile();
            }
        }
    }

    @org.jdesktop.application.Action
    public void chooseLabelFile() {
        if (openEnabled) {
            labelFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        }
        if (labelFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            labelOutputFile = labelFileChooser.getSelectedFile();
            labelFileField.setText(labelOutputFile.getPath());
        }
    }

    @org.jdesktop.application.Action
    public void chooseFeatureFile() {
        if (openEnabled) {
            featureFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        }
        if (featureFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            featureOutputFile = featureFileChooser.getSelectedFile();
            featureFileField.setText(featureOutputFile.getPath());
        }
    }

    @org.jdesktop.application.Action
    public void chooseVectorFile() {
        if (openEnabled) {
            vectorFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        }
        if (vectorFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            vectorOutputFile = vectorFileChooser.getSelectedFile();
            vectorFileField.setText(vectorOutputFile.getPath());
        }
    }

    private boolean validateSourceDirectory() {
        if (sourceDirectory != null) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Please specify a source image directory.");
        return false;
    }

    private boolean validateImageFile() {
        if (imageOutputFile != null) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Please specify an image output file.");
        return false;
    }

    private boolean validateModelFile() {
        if (modelOutputFile != null) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Please specify a model output file.");
        return false;
    }

    private boolean validateLabelFile() {
        if (labelOutputFile != null) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Please specify a label output file.");
        return false;
    }

    private boolean validateFeatureFile() {
        if (featureOutputFile != null) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Please specify a feature output file.");
        return false;
    }

    private boolean validateVectorFile() {
        if (vectorOutputFile != null) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Please specify a vector output file.");
        return false;
    }

    @org.jdesktop.application.Action
    public CreateTask create() {
        if (!createEnabled) {
            return null;
        }
        int width = Integer.parseInt(widthField.getValue().toString());
        int height = Integer.parseInt(heightField.getValue().toString());
        int numComponents = Integer.parseInt(componentsCountField.getText());

        if (!validateSourceDirectory()) {
            return null;
        }
        if (!validateImageFile()) {
            return null;
        }
        if (!validateModelFile()) {
            return null;
        }
        if (!validateLabelFile()) {
            return null;
        }
        if (!validateFeatureFile()) {
            return null;
        }
        if (!validateVectorFile()) {
            return null;
        }
        Model model = new Model(
                sourceDirectory,
                imageOutputFile,
                modelOutputFile,
                labelOutputFile,
                featureOutputFile,
                vectorOutputFile,
                new Dimension(width, height),
                numComponents);

        EigenfacesApplication.getApplication().setModel(model);

        // save the configuration file.
        Model.saveXmlFile(model, modelOutputFile);

        CreateTask task = new CreateTask(
                EigenfacesApplication.getInstance(),
                model);

        createBtn.setEnabled(false);
        createEnabled = false;
        return task;
    }

    @org.jdesktop.application.Action
    public void open() {
        if (!openEnabled) {
            return;
        }
        int width = Integer.parseInt(widthField.getValue().toString());
        int height = Integer.parseInt(heightField.getValue().toString());
        int numComponents = Integer.parseInt(componentsCountField.getText());
        Model model;
        if (!validateModelFile()) {
            return;
        }
        if (!validateSourceDirectory()) {
            return;
        }
        if (!validateImageFile()) {
            return;
        }
        if (!validateLabelFile()) {
            return;
        }
        if (!validateFeatureFile()) {
            return;
        }
        if (!validateVectorFile()) {
            return;
        }
        model = new Model(
                sourceDirectory,
                imageOutputFile,
                modelOutputFile,
                labelOutputFile,
                featureOutputFile,
                vectorOutputFile,
                new Dimension(width, height),
                numComponents);


        EigenfacesApplication.getApplication().setModel(model);
    }

    public boolean isCreateEnabled() {
        return createEnabled;
    }

    public void setCreateEnabled(boolean createEnabled) {
        this.createEnabled = createEnabled;
        createBtn.setEnabled(createEnabled);
        openEnabled = !createEnabled;
        openBtn.setEnabled(openEnabled);
    }

    public boolean isOpenEnabled() {
        return openEnabled;
    }

    public void setOpenEnabled(boolean openEnabled) {
        this.openEnabled = openEnabled;
        openBtn.setEnabled(openEnabled);
        createEnabled = !openEnabled;
        createBtn.setEnabled(createEnabled);
    }

    public void cancelled(TaskEvent<Void> arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void doInBackground(TaskEvent<Void> arg0) {
    }

    public void failed(TaskEvent<Throwable> arg0) {
    }

    public void finished(TaskEvent<Void> arg0) {
    }

    public void interrupted(TaskEvent<InterruptedException> arg0) {
    }

    public void process(TaskEvent<java.util.List<ImageDataModel>> arg0) {
    }

    public void succeeded(TaskEvent<Boolean> arg0) {
        if (arg0.getValue()) {
            setOpenEnabled(openEnabled);
            setCreateEnabled(createEnabled);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        directoryChooser = new javax.swing.JFileChooser();
        dataFileChooser = new javax.swing.JFileChooser();
        modelFileChooser = new javax.swing.JFileChooser();
        labelFileChooser = new javax.swing.JFileChooser();
        featureFileChooser = new javax.swing.JFileChooser();
        vectorFileChooser = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sourceDirectoryField = new javax.swing.JTextField();
        sourceDirBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        exportFileName = new javax.swing.JTextField();
        dataFileBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        widthField = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        heightField = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        modelFileField = new javax.swing.JTextField();
        modelFileBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        labelFileField = new javax.swing.JTextField();
        labelBrowseBtn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        featureFileField = new javax.swing.JTextField();
        featureFileBtn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        vectorFileField = new javax.swing.JTextField();
        vectorFileBtn = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        componentsCountField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        createBtn = new javax.swing.JButton();
        openBtn = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(au.id.cpd.eigenfaces.ui.EigenfacesApplication.class).getContext().getResourceMap(ImageModelPanel.class);
        directoryChooser.setDialogTitle(resourceMap.getString("directoryChooser.dialogTitle")); // NOI18N
        directoryChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.setName("directoryChooser"); // NOI18N

        dataFileChooser.setDialogTitle(resourceMap.getString("dataFileChooser.dialogTitle")); // NOI18N
        dataFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        dataFileChooser.setName("dataFileChooser"); // NOI18N

        modelFileChooser.setDialogTitle(resourceMap.getString("modelFileChooser.dialogTitle")); // NOI18N
        modelFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        modelFileChooser.setName("modelFileChooser"); // NOI18N

        labelFileChooser.setDialogTitle(resourceMap.getString("labelFileChooser.dialogTitle")); // NOI18N
        labelFileChooser.setName("labelFileChooser"); // NOI18N

        featureFileChooser.setDialogTitle(resourceMap.getString("featureFileChooser.dialogTitle")); // NOI18N
        featureFileChooser.setName("featureFileChooser"); // NOI18N

        vectorFileChooser.setDialogTitle(resourceMap.getString("vectorFileChooser.dialogTitle")); // NOI18N
        vectorFileChooser.setName("vectorFileChooser"); // NOI18N

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(10, 3, 2, 2));

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);

        sourceDirectoryField.setText(resourceMap.getString("sourceDirectoryField.text")); // NOI18N
        sourceDirectoryField.setName("sourceDirectoryField"); // NOI18N
        jPanel1.add(sourceDirectoryField);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(au.id.cpd.eigenfaces.ui.EigenfacesApplication.class).getContext().getActionMap(ImageModelPanel.class, this);
        sourceDirBtn.setAction(actionMap.get("chooseSourceDirectory")); // NOI18N
        sourceDirBtn.setText(resourceMap.getString("sourceDirBtn.text")); // NOI18N
        sourceDirBtn.setName("sourceDirBtn"); // NOI18N
        jPanel1.add(sourceDirBtn);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);

        exportFileName.setText(resourceMap.getString("exportFileName.text")); // NOI18N
        exportFileName.setName("exportFileName"); // NOI18N
        jPanel1.add(exportFileName);

        dataFileBtn.setAction(actionMap.get("chooseDataFile")); // NOI18N
        dataFileBtn.setText(resourceMap.getString("dataFileBtn.text")); // NOI18N
        dataFileBtn.setName("dataFileBtn"); // NOI18N
        jPanel1.add(dataFileBtn);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);

        widthField.setName("widthField"); // NOI18N
        widthField.setValue(30);
        jPanel1.add(widthField);

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4);

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel1.add(jLabel5);

        heightField.setName("heightField"); // NOI18N
        heightField.setValue(30);
        jPanel1.add(heightField);

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel1.add(jLabel6);

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel1.add(jLabel7);

        modelFileField.setText(resourceMap.getString("modelFileField.text")); // NOI18N
        modelFileField.setName("modelFileField"); // NOI18N
        jPanel1.add(modelFileField);

        modelFileBtn.setAction(actionMap.get("chooseModelFile")); // NOI18N
        modelFileBtn.setText(resourceMap.getString("modelFileBtn.text")); // NOI18N
        modelFileBtn.setName("modelFileBtn"); // NOI18N
        jPanel1.add(modelFileBtn);

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel1.add(jLabel8);

        labelFileField.setText(resourceMap.getString("labelFileField.text")); // NOI18N
        labelFileField.setName("labelFileField"); // NOI18N
        jPanel1.add(labelFileField);

        labelBrowseBtn.setAction(actionMap.get("chooseLabelFile")); // NOI18N
        labelBrowseBtn.setText(resourceMap.getString("labelBrowseBtn.text")); // NOI18N
        labelBrowseBtn.setName("labelBrowseBtn"); // NOI18N
        jPanel1.add(labelBrowseBtn);

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N
        jPanel1.add(jLabel11);

        featureFileField.setText(resourceMap.getString("featureFileField.text")); // NOI18N
        featureFileField.setName("featureFileField"); // NOI18N
        jPanel1.add(featureFileField);

        featureFileBtn.setAction(actionMap.get("chooseFeatureFile")); // NOI18N
        featureFileBtn.setText(resourceMap.getString("featureFileBtn.text")); // NOI18N
        featureFileBtn.setName("featureFileBtn"); // NOI18N
        jPanel1.add(featureFileBtn);

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        jPanel1.add(jLabel12);

        vectorFileField.setText(resourceMap.getString("vectorFileField.text")); // NOI18N
        vectorFileField.setName("vectorFileField"); // NOI18N
        jPanel1.add(vectorFileField);

        vectorFileBtn.setAction(actionMap.get("chooseVectorFile")); // NOI18N
        vectorFileBtn.setText(resourceMap.getString("vectorFileBtn.text")); // NOI18N
        vectorFileBtn.setName("vectorFileBtn"); // NOI18N
        jPanel1.add(vectorFileBtn);

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        jPanel1.add(jLabel10);

        componentsCountField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        componentsCountField.setText(resourceMap.getString("componentsCountField.text")); // NOI18N
        componentsCountField.setName("componentsCountField"); // NOI18N
        jPanel1.add(componentsCountField);

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N
        jPanel1.add(jLabel13);

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel1.add(jLabel9);

        createBtn.setAction(actionMap.get("create")); // NOI18N
        createBtn.setText(resourceMap.getString("createBtn.text")); // NOI18N
        createBtn.setToolTipText(resourceMap.getString("createBtn.toolTipText")); // NOI18N
        createBtn.setName("createBtn"); // NOI18N
        jPanel1.add(createBtn);

        openBtn.setAction(actionMap.get("open")); // NOI18N
        openBtn.setText(resourceMap.getString("openBtn.text")); // NOI18N
        openBtn.setName("openBtn"); // NOI18N
        jPanel1.add(openBtn);

        add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel1.getAccessibleContext().setAccessibleName(resourceMap.getString("jPanel1.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField componentsCountField;
    private javax.swing.JButton createBtn;
    private javax.swing.JButton dataFileBtn;
    private javax.swing.JFileChooser dataFileChooser;
    private javax.swing.JFileChooser directoryChooser;
    private javax.swing.JTextField exportFileName;
    private javax.swing.JButton featureFileBtn;
    private javax.swing.JFileChooser featureFileChooser;
    private javax.swing.JTextField featureFileField;
    private javax.swing.JSpinner heightField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton labelBrowseBtn;
    private javax.swing.JFileChooser labelFileChooser;
    private javax.swing.JTextField labelFileField;
    private javax.swing.JButton modelFileBtn;
    private javax.swing.JFileChooser modelFileChooser;
    private javax.swing.JTextField modelFileField;
    private javax.swing.JButton openBtn;
    private javax.swing.JButton sourceDirBtn;
    private javax.swing.JTextField sourceDirectoryField;
    private javax.swing.JButton vectorFileBtn;
    private javax.swing.JFileChooser vectorFileChooser;
    private javax.swing.JTextField vectorFileField;
    private javax.swing.JSpinner widthField;
    // End of variables declaration//GEN-END:variables
}
