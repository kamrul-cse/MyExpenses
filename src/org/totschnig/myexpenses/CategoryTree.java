package org.totschnig.myexpenses;

import java.util.HashMap;

/**
 * simple two level category tree, used for storing categories extracted from Grisbi XML
 * guarantees that children are always added through root
 *
 */
public class CategoryTree {
  private HashMap<Integer,CategoryTree> children;
  private String label;
  private int total;
  private boolean rootP;

  public CategoryTree(String label) {
    this(label,true);
  }
  public CategoryTree(String label, boolean rootP) {
    children = new HashMap<Integer,CategoryTree>();
    this.setLabel(label);
    total = 0;
    this.rootP = rootP;
  }
  
  /**
   * @param label
   * @param id
   * @param parent
   * adds a new CategoryTree under parent with a given label and id
   * This operation is only allowed for the root tree, it is not allowed to add directly to
   * subtrees (throws {@link UnsupportedOperationException}). If parent is 0, a top level 
   * category tree is created. If there is no parent with id parent, the method returns without
   * creating a CategoryTree
   */
  public boolean add(String label, Integer id, Integer parent) {
    if (!rootP) {
      throw new UnsupportedOperationException();
    }
    if (parent == 0) {
      addChild(label,id);
    } else {
      CategoryTree parentCat = children.get(parent);
      if (parentCat == null) {
        return false;
      }
      parentCat.addChild(label, id);
    }
    total++;
    return true;
  }
  private void addChild(String label, Integer id) {
    children.put(id,new CategoryTree(label,false));
  }
  
  public HashMap<Integer,CategoryTree> children() {
    return children;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
  public int getTotal() {
    return total;
  }
}