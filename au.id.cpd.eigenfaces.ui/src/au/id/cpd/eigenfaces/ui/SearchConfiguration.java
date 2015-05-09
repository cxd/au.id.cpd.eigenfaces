/*
 * SearchConfiguration.java
 *
 * Created on July 26, 2008, 5:01 PM
 */
package au.id.cpd.eigenfaces.ui;

import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.awt.image.*;
import java.awt.*;
import org.jdesktop.application.*;

import au.id.cpd.algorithms.data.*;
import au.id.cpd.eigenfaces.ui.tasks.*;
import au.id.cpd.eigenfaces.data.*;
import au.id.cpd.eigenfaces.data.operators.*;

/**
 *
 * @author  cd
 */
public class SearchConfiguration extends javax.swing.JPanel implements org.jdesktop.application.TaskListener<SearchQuery, ImageDataModel> {

    private File searchFile;
    private BufferedImage searchImage;
    private BufferedImage resultImage;
    private SearchTask searcher;

    /** Creates new form SearchConfiguration */
    public SearchConfiguration() {
        initComponents();
    }
    
    private void init() {
        splitPanel.setDividerLocation(50.0);
        
    }

    @org.jdesktop.application.Action
    public void chooseSearchImage() {
        if (searchFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            searchFile = searchFileChooser.getSelectedFile();
            searchFileField.setText(searchFile.getPath());
            // display the search image.
            try {
                searchImage = ImageIO.read(searchFile);
                ((Graphics2D) searchCanvas.getGraphics()).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                searchCanvas.getGraphics().drawImage(searchImage, 0, 0, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @org.jdesktop.application.Action
    public SearchTask search() {
        if (searchFile != null) {
            resultImage = null;
            searcher = new SearchTask(EigenfacesApplication.getApplication(),
                    EigenfacesApplication.getApplication().getModel(),
                    searchFile);
            searcher.addTaskListener(this);
            return searcher;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (searchImage != null) {
            searchCanvas.getGraphics().drawImage(searchImage, 0, 0, null);
        }
        if (resultImage != null) {
           TexturePaint texture = new TexturePaint(resultImage, new Rectangle(0, 0, searchImage.getWidth(), searchImage.getHeight()));
	   Graphics2D g2 = (Graphics2D)resultCanvas.getGraphics();
	   g2.setPaint(texture);
	   g2.fillRect(0, 0, searchImage.getWidth(), searchImage.getHeight());
           
        }
    }

    public void cancelled(TaskEvent<Void> arg0) {
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

    /**
     * Display the resulting image on success.
     * @param arg0
     */
    public void succeeded(TaskEvent<SearchQuery> arg0) {
        SearchQuery query = arg0.getValue();
        // convert the result to an image.
        int row = query.getIndex();
        Model model = EigenfacesApplication.getApplication().getModel();

        ImageDataModel dataModel = new ImageDataModel(
                model.getImageOutputFile().getAbsolutePath(), 
                model.getFeatureFile().getAbsolutePath(), 
                model.getVectorFile().getAbsolutePath(), 
                model.getNumComponents());
        dataModel.setNumberOfComponents(model.getNumComponents());

        dataModel.loadStoredImageData();
        dataModel.loadImageDataLabels(model.getLabelOutputFile().getAbsolutePath());
        IMatrix<Double> imageData = dataModel.getImageData();
        
        if ((row >= 0) && (row < imageData.getSize().getRows())) {
            java.util.List<Double> rowData = imageData.getRow(row);
            int length = rowData.size();
            double dim = Math.sqrt(length);
            IMatrix<Double> data = new Matrix<Double>((int) dim, (int) dim);
            data.addAll(rowData);
            data = data.transform();
            resultImage = new BufferedImage((int) dim, (int) dim, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < data.getSize().getRows(); i++) {
                for (int j = 0; j < data.getSize().getCols(); j++) {
                    int rgb = data.get(i, j).intValue();
                    resultImage.setRGB(i, j, rgb);
                }
            }
            ((Graphics2D) resultCanvas.getGraphics()).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            
            TexturePaint texture = new TexturePaint(resultImage, new Rectangle(0, 0, searchImage.getWidth(), searchImage.getHeight()));
	    Graphics2D g2 = (Graphics2D)resultCanvas.getGraphics();
	   g2.setPaint(texture);
	   g2.fillRect(0, 0, searchImage.getWidth(), searchImage.getHeight());
           
           matchResultLabel.setText(query.getLabel());
           matchResultDistance.setText(""+query.getDistance());
           
           SearchResultModel resultModel = new SearchResultModel(query);
           resultTable.setModel(resultModel);
           resultTable.updateUI();
           
           // resultCanvas.getGraphics().drawImage(resultImage, 0, 0, null);
        }
        imageData.close();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchFileChooser = new javax.swing.JFileChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchFileField = new javax.swing.JTextField();
        searchFileBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        matchResultLabel = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        matchResultDistance = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        splitPanel = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        searchCanvas = new java.awt.Canvas();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultCanvas = new java.awt.Canvas();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(au.id.cpd.eigenfaces.ui.EigenfacesApplication.class).getContext().getResourceMap(SearchConfiguration.class);
        searchFileChooser.setDialogTitle(resourceMap.getString("searchFileChooser.dialogTitle")); // NOI18N
        searchFileChooser.setName("searchFileChooser"); // NOI18N

        setMinimumSize(new java.awt.Dimension(640, 480));
        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jScrollPane3.setName("jScrollPane3"); // NOI18N
        jScrollPane3.setPreferredSize(new java.awt.Dimension(375, 100));

        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Result Label", "Distance"
            }
        ));
        resultTable.setName("resultTable"); // NOI18N
        jScrollPane3.setViewportView(resultTable);

        add(jScrollPane3, java.awt.BorderLayout.SOUTH);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(6, 3));

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);

        searchFileField.setText(resourceMap.getString("searchFileField.text")); // NOI18N
        searchFileField.setName("searchFileField"); // NOI18N
        jPanel1.add(searchFileField);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(au.id.cpd.eigenfaces.ui.EigenfacesApplication.class).getContext().getActionMap(SearchConfiguration.class, this);
        searchFileBtn.setAction(actionMap.get("chooseSearchImage")); // NOI18N
        searchFileBtn.setText(resourceMap.getString("searchFileBtn.text")); // NOI18N
        searchFileBtn.setName("searchFileBtn"); // NOI18N
        jPanel1.add(searchFileBtn);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);

        searchBtn.setAction(actionMap.get("search")); // NOI18N
        searchBtn.setText(resourceMap.getString("searchBtn.text")); // NOI18N
        searchBtn.setName("searchBtn"); // NOI18N
        jPanel1.add(searchBtn);

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4);

        matchResultLabel.setText(resourceMap.getString("matchResultLabel.text")); // NOI18N
        matchResultLabel.setName("matchResultLabel"); // NOI18N
        jPanel1.add(matchResultLabel);

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel1.add(jLabel5);

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel1.add(jLabel6);

        matchResultDistance.setText(resourceMap.getString("matchResultDistance.text")); // NOI18N
        matchResultDistance.setName("matchResultDistance"); // NOI18N
        jPanel1.add(matchResultDistance);

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel1.add(jLabel7);

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel1.add(jLabel8);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.BorderLayout());

        splitPanel.setDividerLocation(150);
        splitPanel.setName("splitPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        searchCanvas.setName("searchCanvas"); // NOI18N
        jScrollPane1.setViewportView(searchCanvas);

        splitPanel.setLeftComponent(jScrollPane1);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        resultCanvas.setName("resultCanvas"); // NOI18N
        jScrollPane2.setViewportView(resultCanvas);

        splitPanel.setRightComponent(jScrollPane2);

        jPanel2.add(splitPanel, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField matchResultDistance;
    private javax.swing.JTextField matchResultLabel;
    private java.awt.Canvas resultCanvas;
    private javax.swing.JTable resultTable;
    private javax.swing.JButton searchBtn;
    private java.awt.Canvas searchCanvas;
    private javax.swing.JButton searchFileBtn;
    private javax.swing.JFileChooser searchFileChooser;
    private javax.swing.JTextField searchFileField;
    private javax.swing.JSplitPane splitPanel;
    // End of variables declaration//GEN-END:variables
}
