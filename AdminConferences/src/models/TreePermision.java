package models;

import java.util.ArrayList;
import java.util.Comparator;

import models.NodesPermision.EnumPermission;
import models.NodesPermision.PermissionNode;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class TreePermision extends Tree<PermissionNode> {

	private final Comparator<NodeGeneric<PermissionNode>> comparableNode = new Comparator<NodeGeneric<PermissionNode>>() {
		@Override
		public int compare(NodeGeneric<PermissionNode> element, NodeGeneric<PermissionNode> anotherElement) {
			return element.getData().getIdNodePermission()
					.equalsIgnoreCase(anotherElement.getData().getIdNodePermission()) ? 0 : -1;
		}
	};

	public TreePermision(PermissionNode rootData) {
		super(rootData);
	}
	
	public boolean theUserHasPermission(PermissionNode user,PermissionNode nodeConference,EnumPermission typePermision) {
		NodeGeneric<PermissionNode > userNode=search(user, comparableNode);
		ArrayList<NodeGeneric<PermissionNode>> listConfe =userNode.getChildren();		
		for (int i = 0; i < listConfe.size(); i++) {
			if(listConfe.get(i).getData().getIdNodePermission().equalsIgnoreCase(nodeConference.getIdNodePermission())) {
				return getResponsePermission(listConfe.get(i),typePermision);				
			}	
		}
		return false;
	}
	public boolean getResponsePermission(NodeGeneric<PermissionNode> ConferencePart,EnumPermission typePermision){
		ArrayList<NodeGeneric<PermissionNode>> listPermission =ConferencePart.getChildren();
		for (int i = 0; i < listPermission .size(); i++) {
			if(listPermission.get(i).getData().getDataPermision()==typePermision) {
				return true;
			}
		}
		return false;
	}
	public void deleteNodePermission(PermissionNode dataNode) {
		deleteNode(dataNode, comparableNode);
	}

	public void addNodePermissions(PermissionNode dataNode, PermissionNode dataFather) {
		if (dataFather.addChild(dataNode)) {
			add(comparableNode, dataNode, dataFather);
		}
	}
	public ArrayList<String> getListIdUsers() {
		ArrayList<String> listUsers=new ArrayList<String>();
		ArrayList<NodeGeneric<PermissionNode>> listChild=root.getChildren();
		for (int i = 0; i < listChild.size(); i++) {
			listUsers.add(listChild.get(i).getData().getIdData());
		}
		return listUsers;
	}
	public void printAll() {
		printAll(root);
	}

	private void printAll(NodeGeneric<PermissionNode> actual) {
		for (NodeGeneric<PermissionNode> child : actual.getChildren()) {
			printAll(child);
		}
	}
}
