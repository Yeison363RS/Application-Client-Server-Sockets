package models;

import java.util.ArrayList;
import java.util.Comparator;

import models.NodeTypes.Attendee;
import models.NodeTypes.NodeConference;

public class MyTree extends Tree<NodeConference> {
	private final Comparator<NodeGeneric<NodeConference>> wordComparable = new Comparator<NodeGeneric<NodeConference>>() {
		@Override
		public int compare(NodeGeneric<NodeConference> element, NodeGeneric<NodeConference> anotherElement) {
			return element.getData().getNameData().equalsIgnoreCase(anotherElement.getData().getNameData()) ? 0 : -1;
		}
	};
	
	public MyTree(NodeConference rootData) {
		super(rootData);
	}
	public void deleteNodeConference(NodeConference dataNode) {
		deleteNode(dataNode,wordComparable);
	}
	public void addNodeConferences(NodeConference dataNode,NodeConference dataFather) {
		if(dataFather.addNodeChild(dataNode)) {
			add(wordComparable, dataNode, dataFather);				
		}
	}
	public NodeGeneric<NodeConference> getNodeSearch(String data) {
		NodeConference node=new Attendee(data);
		NodeGeneric<NodeConference> nodeConference =search(node, wordComparable);
		return nodeConference;
	}
	private NodeGeneric<NodeConference> jump(NodeGeneric<NodeConference> actual, NodeConference data) {
		NodeGeneric<NodeConference> newNode = new NodeGeneric<>(data);
		int index = getIndex(newNode, actual.getChildren());
		if (index >= 0) {
			return actual.getChildren().get(index);
		} else {
			return null;
		}
	}
	public void printAll() {
		printAll(root);
	}

	private void printAll(NodeGeneric<NodeConference> actual) {
		System.out.println(actual.getData().getNameData());
		for (NodeGeneric<NodeConference> child : actual.getChildren()) {
			printAll(child);
		}
	}
	public int getIndex(NodeGeneric<NodeConference> node, ArrayList<NodeGeneric<NodeConference>> list) {
		int index = 0;
		for (NodeGeneric<NodeConference> nodeAux : list) {
			if (wordComparable.compare(node, nodeAux) == 0) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
}
