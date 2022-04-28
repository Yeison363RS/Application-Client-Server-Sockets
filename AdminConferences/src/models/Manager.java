package models;

import models.NodeTypes.Attendee;
import models.NodeTypes.Conference;
import models.NodeTypes.Lecturer;
import models.NodeTypes.NodeConference;
import models.NodeTypes.RootConference;
import models.NodeTypes.Topic;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class Manager {
	private MyTree tree;

	public Manager() {
		this.tree = new MyTree(new RootConference("root"));
	}

	public void execute() {
		tree.addNodeConferences(new Topic("java"), tree.getRoot().getData());
		tree.addNodeConferences(new Topic("c++"), tree.getRoot().getData());
		tree.addNodeConferences(new Topic("java_Es"), new Topic("java"));
		tree.addNodeConferences(new Topic("C++_Grafos"), new Topic("c++"));
		tree.addNodeConferences(new Conference("Conferencia_Grafos"), new Topic("C++_Grafos"));
		tree.addNodeConferences(new Conference("Conferencia_Es"), new Topic("java_Es"));
		tree.addNodeConferences(new Lecturer("juan David"), new Conference("Conferencia_Es"));
		tree.addNodeConferences(new Attendee("leonardo"), new Conference("Conferencia_Grafos"));
		tree.addNodeConferences(new Attendee("Juan"), new Conference("Conferencia_Es"));
	}

	public void addNodeConference(NodeConference idNewChild, NodeConference idFaher) {
		tree.addNodeConferences(idNewChild, idFaher);
	}

	public NodeConference getDataNodeFather(String idData) {
		return tree.getNodeSearch(idData).getData();
	}
	
	
	public void generateReport(String idDataConference) {
		tree.getNodeSearch(idDataConference);
	}

	public NodeConference getDataIdNodeConference(String idDataConference, String typeNode) {
		switch (typeNode) {
		case "Conference":
			return new Conference(idDataConference);
		case "Attendee":
			return new Attendee(idDataConference);
		case "Lecturer":
			return new Lecturer(idDataConference);
		case "Topic":
			return new Topic(idDataConference);
		default:
			return new Topic(idDataConference);
		}
	}

	public void deleteNodeConference(String dataText) {
		Topic dataNode = new Topic(dataText);
		tree.deleteNodeConference(dataNode);
	}

	public NodeGeneric<NodeConference> getTreeConference() {
		return tree.getRoot();
	}
	
}
