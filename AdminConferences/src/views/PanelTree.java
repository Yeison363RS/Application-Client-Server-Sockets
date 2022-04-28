package views;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import models.NodeGeneric;
import models.NodesPermision.PermissionNode;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class PanelTree extends JScrollPane{

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private Object nodePermission;
	private RenderTreePermission myRender;

	public PanelTree() {
		this.setBackground(PanelLoad.COLOR_BACK);
		this.myRender=new RenderTreePermission();
	}
	public void createTree(NodeGeneric<PermissionNode> nodeRoot) {
		this.tree=new JTree();
		NodeGraphic rootNode = new NodeGraphic();
		rootNode.setUserObject(nodeRoot.getData());
		addNodeJtree(nodeRoot,rootNode);
		DefaultTreeModel model=new DefaultTreeModel(rootNode);
		this.tree.setModel(model);
		this.tree.setCellRenderer(myRender);
		this.setViewportView(tree);
		setSettingsTree();
	}
	public void addNodeJtree(NodeGeneric<PermissionNode> nodeRoot,DefaultMutableTreeNode nodeFather) {
		ArrayList<NodeGeneric<PermissionNode>> listChilds = nodeRoot.getChildren();
		for (int i = 0; i < listChilds.size(); i++) {
			NodeGraphic nodeChild = new NodeGraphic();
			nodeChild.setUserObject(listChilds.get(i).getData());
			nodeFather.add(nodeChild);
			addNodeJtree(listChilds.get(i),nodeChild);
		}
	}
	public void setSettingsTree() {
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();
				if(node!=null) {
//					System.out.println("imprimiendo"+node.getUserObject());
					nodePermission=node.getUserObject();
				}
			}
		});
	}
	public void setNodeCoference(Object nodePermission) {
		this.nodePermission = nodePermission;
	}
	public String getNodeSelected() {
		PermissionNode node=(PermissionNode)nodePermission;
//		System.out.println("node seleasda:"+node.getIdNodePermission());
		return node.getIdNodePermission();
	}
}
