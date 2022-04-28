package views;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;


import models.NodeTypes.NodeConference;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class RenderTreeConference extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 1L;
	
	public RenderTreeConference() {
	}
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
		boolean leaf, int row, boolean hasFocus) {
		NodeConference nodeConference=(NodeConference) ((NodeGraphic) value).getOriginalObject();
		super.getTreeCellRendererComponent(tree, nodeConference.getNameData(), selected, expanded, leaf, row, hasFocus);
		ImageIcon image=new ImageIcon(nodeConference.getPathIcon()); 		
		setIcon(image);
		return this;
	}
}
