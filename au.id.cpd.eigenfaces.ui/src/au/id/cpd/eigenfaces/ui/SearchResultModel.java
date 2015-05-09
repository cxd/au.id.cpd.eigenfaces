/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package au.id.cpd.eigenfaces.ui;

import java.util.*;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import au.id.cpd.algorithms.data.*;
import au.id.cpd.eigenfaces.data.*;

/**
 *
 * @author cd
 */
public class SearchResultModel implements TableModel {

    private SearchQuery query;

   
    
    
    public SearchResultModel() {
        
    }
    
    public SearchResultModel(SearchQuery result) {
        query  = result;
    }
    
    public SearchQuery getQuery() {
        return query;
    }

    public void setQuery(SearchQuery query) {
        this.query = query;
    }
    
    public void addTableModelListener(TableModelListener l) {
    
    }

    /**
     * This model only supports 2 columns.
     * @param columnIndex
     * @return
     */
    public Class<?> getColumnClass(int columnIndex) {
        if (query == null) {
            return Void.TYPE;
        }
        if (columnIndex == 0) {
            return String.class.getClass();
        } else if (columnIndex == 1) {
            return Double.class.getClass();
        }
        return Void.TYPE;
    }

    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) return "Result Label";
        else if (columnIndex == 1) return "Distance";
        return "";
    }

    public int getRowCount() {
        if (query == null) return 0;
        return query.getDistances().size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (query == null) return null;
        if ( (rowIndex < 0) || (rowIndex > query.getDistances().size() - 1) ) return null;
        if (columnIndex == 0) {
            return query.getDistances().get(rowIndex).getKey().toString();
        } else if (columnIndex == 1) {
            return query.getDistances().get(rowIndex).getValue();
        }
        return null;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
       return false;
    }

    public void removeTableModelListener(TableModelListener l) {
       
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }

}
