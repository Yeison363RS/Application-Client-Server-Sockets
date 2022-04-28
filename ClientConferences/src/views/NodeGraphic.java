package views;

import javax.swing.tree.DefaultMutableTreeNode;


public class NodeGraphic extends DefaultMutableTreeNode{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public Object getUserObject() {
		return super.getUserObject();
	}
	public Object getOriginalObject() {
		return super.userObject;
	}
	@Override
	public void setUserObject(Object userObject) {
		super.setUserObject(userObject);
		super.userObject=userObject;
	}
}
