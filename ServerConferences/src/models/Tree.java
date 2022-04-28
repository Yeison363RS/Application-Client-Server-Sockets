package models;

import java.util.ArrayList;
import java.util.Comparator;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class Tree<T> {

	protected NodeGeneric<T> root;

	public Tree(T rootData) {
		root = new NodeGeneric<T>(rootData);
	}
	public void add(Comparator<NodeGeneric<T>> ComparableNode, T data, T dataFather) {
		NodeGeneric<T> nodeFather=search(dataFather,ComparableNode);
		NodeGeneric<T> newNode = new NodeGeneric<T>(data);
		if(nodeFather!=null) {
			int index = getIndexPositionChilden(nodeFather.getChildren(), newNode, ComparableNode);
			if (index < 0) {
				nodeFather.addChild(newNode);
			}			
		}
	}
	
	public void print() {
		print(root);
	}

	private void print(NodeGeneric<T> actual) {
		System.out.println(actual);
		for (NodeGeneric<T> child : actual.getChildren()) {
			print(child);
		}
	}

	public NodeGeneric<T> getRoot() {
		return root;
	}

	public NodeGeneric<T> search(T data,Comparator<NodeGeneric<T>> wordComparable) {
		return search(root, data,wordComparable);
	}

	private NodeGeneric<T> search(NodeGeneric<T> actual, T data,Comparator<NodeGeneric<T>> wordComparable) {
		if (wordComparable.compare(actual,new NodeGeneric<T>(data))==0) {
			return actual;
		}
		for (NodeGeneric<T> child : actual.getChildren()) {
			NodeGeneric<T> result = search(child, data,wordComparable);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public ArrayList<NodeGeneric<T>> searchAll(T data) {
		ArrayList<NodeGeneric<T>> results = new ArrayList<>();
		search(root, data, results);
		return results;
	}
	public void deleteNode(T dataNode,Comparator<NodeGeneric<T>> nodeComparator){
		NodeGeneric<T> nodeChild=new NodeGeneric<T>(dataNode);
		NodeGeneric<T> father=getFatherOf(nodeChild,nodeComparator);
		if(father!=null) {
			int index = getIndexPositionChilden(father.getChildren(), nodeChild, nodeComparator);
			if (index >= 0) {
				father.deleteChild(index);
			}			
		}
	}
	public int getIndexPositionChilden(ArrayList<NodeGeneric<T>> listChild,NodeGeneric<T> nodeSearch,Comparator<NodeGeneric<T>> nodeComparator) {
		for (int i = 0; i < listChild.size(); i++) {
			if(nodeComparator.compare(nodeSearch, listChild.get(i))==0){
				return i;
			}
		}
		return -1;
	}
	private void search(NodeGeneric<T> actual, T data, ArrayList<NodeGeneric<T>> results) {
		if (actual.getData().equals(data)) {
			results.add(actual);
		}
		for (NodeGeneric<T> child : actual.getChildren()) {
			search(child, data, results);
		}
	}

	public NodeGeneric<T> getFatherOf(NodeGeneric<T> nodeChild,Comparator<NodeGeneric<T>> comparator) {
		return getFatherOf(root, nodeChild,comparator);
	}

	public NodeGeneric<T> getFatherOf(NodeGeneric<T> base, NodeGeneric<T> nodeChild,Comparator<NodeGeneric<T>> comparator) {
		for (NodeGeneric<T> child : base.getChildren()) {
			if (comparator.compare(child,nodeChild)==0) {
				return base;
			} else {
				NodeGeneric<T> father = getFatherOf(child, nodeChild,comparator);
				if (father != null) {
					return father;
				}
			}
		}
		return null;
	}
}
