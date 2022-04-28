package models;

import java.util.ArrayList;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class NodeGeneric<T> {
	private T nameData;
	private ArrayList<NodeGeneric<T>> children;

	public void addChild(NodeGeneric<T> node) {
		children.add(node);
	};

	public NodeGeneric() {
		children = new ArrayList<NodeGeneric<T>>();
	}

	public NodeGeneric(T data) {
		this.nameData = data;
		children = new ArrayList<NodeGeneric<T>>();
	}

	public void addNode(NodeGeneric<T> node) {
		children.add(node);
	}

	public ArrayList<NodeGeneric<T>> getChildren() {
		return children;
	}

	public void deleteChild(int index) {
		this.children.remove(index);
	}

	public T getData() {
		return nameData;
	}
}