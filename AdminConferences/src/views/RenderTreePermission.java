package views;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import models.NodesPermision.PermissionNode;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class RenderTreePermission extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of Render Tree */
	public RenderTreePermission() {

	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		PermissionNode nodePermission = (PermissionNode) ((NodeGraphic) value).getOriginalObject();
		super.getTreeCellRendererComponent(tree, nodePermission.getIdNodePermission(), selected, expanded, leaf, row, hasFocus);
		ImageIcon image = new ImageIcon(nodePermission.getPathIcon());
		setIcon(image);
		return this;
	}
}
