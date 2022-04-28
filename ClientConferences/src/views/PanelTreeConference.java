package views;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import models.NodeGeneric;
import models.NodeTypes.NodeConference;

public class PanelTreeConference extends JScrollPane{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private Object nodeCoference;
	private RenderTreeConference myRender;
	public PanelTreeConference() {
		myRender=new RenderTreeConference();
	}
	public void createTree(NodeGeneric<NodeConference> nodeRoot) {
		this.tree=new JTree();
		NodeGraphic root = new NodeGraphic();
		root.setUserObject(nodeRoot.getData());
		addNodeJtree(nodeRoot,root);
		DefaultTreeModel model=new DefaultTreeModel(root);
		tree.setModel(model);
		tree.setCellRenderer(myRender);
		this.setViewportView(tree);
		setSettingsTree();
	}
	public void addNodeJtree(NodeGeneric<NodeConference> nodeRoot,DefaultMutableTreeNode nodeFather) {
		ArrayList<NodeGeneric<NodeConference>> listChilds = nodeRoot.getChildren();
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
					setNodeCoference(node.getUserObject());
				}
			}
		});
	}
	public String getNodeSelected() {
		NodeConference node=(NodeConference)nodeCoference;
		return node.getNameData();
	}
	public void setNodeCoference(Object nodeCoference) {
		this.nodeCoference = nodeCoference;
	}
}
