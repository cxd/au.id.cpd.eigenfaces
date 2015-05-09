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
public class LabelDeserializer implements IConstrainedAdapter<String, ObjectInputStream, ITupleCollection<Integer, String>> {

	/**
	 * Object input stream from which to read the tuples.
	 */
	private ObjectInputStream ois;
	/**
	 * Resulting product
	 */
	private ITupleCollection<Integer, String> tuples;
	/**
	 * file containing the serialized tuples
	 */
	private String file;
	
	/**
	 * Default
	 */
	public LabelDeserializer() {
		
	}
	
	/**
	 * construct with path to file.
	 * @param f
	 */
	public LabelDeserializer(String f) {
		file = f;
	}
	
	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#apply()
	 */
	public boolean apply() {
		if (file == null) return false;
		File f = new File(file);
		return f.exists();
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IConstraint#apply(java.lang.Object)
	 */
	public boolean apply(String domain) {
		file = domain;
		return apply();
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
	public ITupleCollection<Integer, String> adapt() {
		try {
			FileInputStream is = new FileInputStream(file);
			ois = new ObjectInputStream(is);
			tuples = (TupleCollection<Integer, String>)ois.readObject();
			ois.close();
			return tuples;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#adapt(java.lang.Object)
	 */
	public ITupleCollection<Integer, String> adapt(ObjectInputStream s) {
		ois = s;
		try {
			tuples = (TupleCollection<Integer, String>)ois.readObject();
			ois.close();
			return tuples;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getProduct()
	 */
	public ITupleCollection<Integer, String> getProduct() {
		return tuples;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#getSource()
	 */
	public ObjectInputStream getSource() {
		return ois;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setProduct(java.lang.Object)
	 */
	public void setProduct(ITupleCollection<Integer, String> p) {
		tuples = p;
	}

	/* (non-Javadoc)
	 * @see au.id.cpd.algorithms.patterns.IAdapter#setSource(java.lang.Object)
	 */
	public void setSource(ObjectInputStream s) {
		ois = s;
	}

}
