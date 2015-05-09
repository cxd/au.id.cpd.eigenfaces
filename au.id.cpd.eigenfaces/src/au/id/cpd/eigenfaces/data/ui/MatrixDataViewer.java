/**
 * 
 */
package au.id.cpd.eigenfaces.data.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

import au.id.cpd.algorithms.*;
import au.id.cpd.algorithms.data.*;


/**
 * @author cd
 *
 */
public class MatrixDataViewer extends JFrame implements Runnable {

	private JMenuBar menu;
	private ImagePanel imagePanel;
	
	private static MatrixDataViewer instance;
	
	private MatrixDataViewer() {
		init();
	}
	
	public static MatrixDataViewer getInstance() {
		if (instance == null) {
			instance = new MatrixDataViewer();
		}
		return instance;
	}
	
	public void init() {
		this.setTitle("View Image Matrix");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(320, 240);
		this.getContentPane().setLayout(new BorderLayout());
		
		menu = new JMenuBar();
		JMenu file = new JMenu("File");
		file.add(new OpenAction()).setMnemonic('o');
		file.add(new CloseAction()).setMnemonic('x');
		menu.add(file);
		
		this.setJMenuBar(menu);
		
		imagePanel = new ImagePanel();
		this.getContentPane().add(imagePanel, BorderLayout.CENTER);
	}
	
	public void updateImageData(File file) {
		try {
			MatrixBuffer<Double> mat = new MatrixBuffer<Double>(file.getAbsoluteFile());
			imagePanel.updateImageData(mat);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		setVisible(true);
	}
	
	public class OpenAction extends AbstractAction {
		
		public OpenAction() {
			super("Open");
		}
		
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter() {
				/* (non-Javadoc)
				 * @see javax.swing.filechooser.FileFilter#getDescription()
				 */
				@Override
				public String getDescription() {
					return "MatrixBuffer Files | *.jmat";
				}

				/* (non-Javadoc)
				 * @see java.io.FileFilter#accept(java.io.File)
				 */
				public boolean accept(File pathname) {
					if (pathname.isDirectory()) 
						return true;
					if (pathname.getName().indexOf(".jmat") >= 0)
						return true;
					return false;
				}
			};
			chooser.setFileFilter(filter);
			int result = chooser.showOpenDialog(MatrixDataViewer.getInstance());
			if ( (chooser.getSelectedFile() != null) && (result == JFileChooser.APPROVE_OPTION) ) {
				MatrixDataViewer.getInstance().updateImageData(chooser.getSelectedFile());
			}
		}
	}
	
	public class CloseAction extends AbstractAction {
		
		public CloseAction() {
			super("Close");
		}
		
		public void actionPerformed(ActionEvent e) {
			MatrixDataViewer.getInstance().setVisible(false);
			System.exit(0);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread t = new Thread(MatrixDataViewer.getInstance());
		t.run();
	}

}
