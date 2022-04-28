package test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import models.Manager;
import models.NodeTypes.Attendee;
import servers.Connections.PersistenceManager;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class TestCompilateTree {
	private Manager manager;
	private PersistenceManager persistence;

	public TestCompilateTree() {
		this.manager = new Manager();
		this.persistence = new PersistenceManager();
	}

	public void printTreeCompilate() {
		try {
			System.out.println(persistence.getTreeXml(manager.getTreeConference()));
		} catch (TransformerConfigurationException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	public void getXmlPermission() {
		try {
			System.out.println(persistence.getTreePermissionXml(manager.getTreePermision()));
		} catch (TransformerConfigurationException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	public void testManagerAvaliable() {
//1		System.out.println(manager.isAvalibleActionUser("1ROOT", manager.getDataNodeFather("Conferencia_Es"), EnumPermission.CREATE));
		manager.addNodeConference(manager.getDataNodeFather("Conferencia_Es"), "1ROOT", new Attendee("yuber"));
		manager.printTree();
		}

	public static void main(String[] args) {
		TestCompilateTree test = new TestCompilateTree();
//2		test.testManagerAvaliable();
//1		test.printTreeCompilate();
		test.getXmlPermission();
	}
}
