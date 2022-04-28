package test;

import models.PersistenceClient;

public class testTreeCompilate {
	private PersistenceClient persistence;

	public testTreeCompilate() {
		this.persistence = new PersistenceClient();
	}
	public void createTree() {
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ROOT_TREE><DATAS_NODE_XML><TYPE_NODE_XML>CONFERENCE</TYPE_NODE_XML><ID_NODE_XML>root</ID_NODE_XML></DATAS_NODE_XML><NODE_CHILD><DATAS_NODE_XML><TYPE_NODE_XML>TOPIC</TYPE_NODE_XML><ID_NODE_XML>java</ID_NODE_XML></DATAS_NODE_XML><NODE_CHILD><DATAS_NODE_XML><TYPE_NODE_XML>TOPIC</TYPE_NODE_XML><ID_NODE_XML>java_Es</ID_NODE_XML></DATAS_NODE_XML><NODE_CHILD><DATAS_NODE_XML><TYPE_NODE_XML>CONFERENCE</TYPE_NODE_XML><ID_NODE_XML>Conferencia_Es</ID_NODE_XML></DATAS_NODE_XML><NODE_CHILD><DATAS_NODE_XML><TYPE_NODE_XML>LECTURER</TYPE_NODE_XML><ID_NODE_XML>juan David</ID_NODE_XML></DATAS_NODE_XML></NODE_CHILD><NODE_CHILD><DATAS_NODE_XML><TYPE_NODE_XML>ATTENDEE</TYPE_NODE_XML><ID_NODE_XML>Juan</ID_NODE_XML></DATAS_NODE_XML></NODE_CHILD></NODE_CHILD></NODE_CHILD></NODE_CHILD><NODE_CHILD><DATAS_NODE_XML><TYPE_NODE_XML>TOPIC</TYPE_NODE_XML><ID_NODE_XML>c++</ID_NODE_XML></DATAS_NODE_XML><NODE_CHILD><DATAS_NODE_XML><TYPE_NODE_XML>TOPIC</TYPE_NODE_XML><ID_NODE_XML>C++_Grafos</ID_NODE_XML></DATAS_NODE_XML><NODE_CHILD><DATAS_NODE_XML><TYPE_NODE_XML>CONFERENCE</TYPE_NODE_XML><ID_NODE_XML>Conferencia_Grafos</ID_NODE_XML></DATAS_NODE_XML><NODE_CHILD><DATAS_NODE_XML><TYPE_NODE_XML>ATTENDEE</TYPE_NODE_XML><ID_NODE_XML>leonardo</ID_NODE_XML></DATAS_NODE_XML></NODE_CHILD></NODE_CHILD></NODE_CHILD></NODE_CHILD></ROOT_TREE>";
		persistence.getTreeConference(xml);
	}
	public void testGetExtencion() {
		System.out.println(persistence.getExtencionFile("/home/yeison/Downloads/IMAGENES DE FISICA EJER/4.jpeg"));
	}
	public static void main(String[] args) {
		testTreeCompilate test=new testTreeCompilate();
		test.testGetExtencion();
//		test.createTree();
		
	}
}
