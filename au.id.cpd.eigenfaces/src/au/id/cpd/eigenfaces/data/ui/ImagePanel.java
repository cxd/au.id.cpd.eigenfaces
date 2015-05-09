/**
 * 
 */
package au.id.cpd.eigenfaces.data.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.awt.image.*;


import au.id.cpd.algorithms.data.*;
/**
 * @author cd
 *
 */
public class ImagePanel extends JPanel implements ActionListener {
	
	

	private BufferedImage image;
	private JScrollPane scrollPane;
	private JTextField indexField;
	private JButton loadBtn;
	private JLabel label;
	private IMatrix<Double> matrix;
	/**
	 * Image panel used to display an image.
	 */
	public ImagePanel() {
		init();
	}
	
	public void init() {
		this.setLayout(new BorderLayout());
		scrollPane = new JScrollPane();
		this.add(scrollPane, BorderLayout.CENTER);
		label = new JLabel("Enter Row Index:");
		indexField = new JTextField();
		loadBtn = new JButton("Show Image");
		label.setVisible(false);
		indexField.setVisible(false);
		indexField.setPreferredSize(new Dimension(50, 20));
		loadBtn.setVisible(false);
		loadBtn.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(label);
		panel.add(indexField);
		panel.add(loadBtn);
		this.add(panel, BorderLayout.NORTH);
	}
	
	public void updateImageData(IMatrix<Double> data) {
		label.setVisible(true);
		indexField.setVisible(true);
		loadBtn.setVisible(true);
		matrix = data;
		this.updateUI();
	}
	
	public void updateImage(BufferedImage im) {
		image = im;
		updateImage();
	}
	
	public void updateImage() {
		if (scrollPane != null) {
			this.remove(scrollPane);
		}
		JPanel imagePanel = new JPanel() {
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (image != null) {
					TexturePaint texture = new TexturePaint(image, new Rectangle(0, 0, image.getWidth(), image.getHeight()));
					Graphics2D g2 = (Graphics2D)g;
					g2.setPaint(texture);
					g2.fillRect(0, 0, image.getWidth(), image.getHeight());
				}
			}		
		};
		
		
		scrollPane = new JScrollPane(imagePanel);
		this.add(scrollPane, BorderLayout.CENTER);
		this.updateUI();
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			int row = Integer.parseInt(indexField.getText());
			if ((row >= 0) && (row < matrix.getSize().getRows())) {
				java.util.List<Double> rowData = matrix.getRow(row);
				int length = rowData.size();
				double dim = Math.sqrt(length);
				IMatrix<Double> data = new Matrix<Double>((int)dim, (int)dim);
				data.addAll(rowData);
				image = new BufferedImage((int)dim, (int)dim, BufferedImage.TYPE_INT_RGB);
				for(int i=0;i<data.getSize().getRows();i++) {
					for(int j=0;j<data.getSize().getCols();j++) {
						int rgb = data.get(i, j).intValue();
						image.setRGB(i, j, rgb);
					}
				}
				image = rescale(image, this.getWidth(), this.getHeight());
				this.updateImage();
			} else {
				
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Scale the supplied image to the supplied width and height.
	 * @param srcImage
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage rescale(BufferedImage srcImage, int w, int h) {
		Image im = srcImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = newImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(im, 0, 0, null);
		g.dispose();
		return newImage;
	}
	
	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * @return the matrix
	 */
	public IMatrix<Double> getMatrix() {
		return matrix;
	}

	/**
	 * @param matrix the matrix to set
	 */
	public void setMatrix(IMatrix<Double> matrix) {
		this.matrix = matrix;
	}
}
