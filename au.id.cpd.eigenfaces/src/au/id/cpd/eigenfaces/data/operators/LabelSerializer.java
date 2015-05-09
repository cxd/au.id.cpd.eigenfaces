/**
 * 
 */
package au.id.cpd.eigenfaces.data.operators;

import java.io.*;

import au.id.cpd.algorithms.patterns.*;
import au.id.cpd.algorithms.data.*;
/**
 * @author cd
 *
 */
public class LabelSerializer implements IConstrainedAdapter<String, ITupleCollection<Integer, String>, ObjectOutputStream> {

	private String file;
	
	private ITupleCollection<Integer, String> tuples;

	private ObjectOutputStream oos;

	/**
	 * Default
	 */
	public LabelSerializer() {
		
	}
	
	/**
	 * Construct with file.
	 * @param f
	 */
	public LabelSerializer(String f) {
		file = f;
	}
	
	/**
	 * Construct with file.
	 * @param f
	 */
	public LabelSerializer(String f, ITupleCollection<Integer, String> l) {
		file = f;
		tuples = l;
	}
	
	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#apply()
	 */
	public boolean apply() {
		return (file != null);
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#apply(java.lang.Object)
	 */
	public boolean apply(String domain) {
		file = domain;
		return (file != null);
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#getDomain()
	 */
	public String getDomain() {
		return file;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#setDomain(java.lang.Object)
	 */
	public void setDomain(String d) {
		file = d;
	}

		
	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt()
	 */
	public ObjectOutputStream adapt() {
		if (!apply()) 
			return null;
		if (tuples == null)
			return null;
		try {
			FileOutputStream os = new FileOutputStream(file);
			oos = new ObjectOutputStream(os);
			oos.writeObject(tuples);
			return oos;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt(java.lang.Object)
	 */
	public ObjectOutputStream adapt(ITupleCollection<Integer, String> s) {
		tuples = s;
		return adapt();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getProduct()
	 */
	public ObjectOutputStream getProduct() {
		return oos;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getSource()
	 */
	public ITupleCollection<Integer, String> getSource() {
		return tuples;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setProduct(java.lang.Object)
	 */
	public void setProduct(ObjectOutputStream p) {
		oos = p;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setSource(java.lang.Object)
	 */
	public void setSource(ITupleCollection<Integer, String> s) {
		tuples = s;
	}

}
