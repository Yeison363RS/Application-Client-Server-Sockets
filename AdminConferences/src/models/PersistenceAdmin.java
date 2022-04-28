package models;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import models.NodeTypes.*;
import models.NodesPermision.ConferencePermission;
import models.NodesPermision.EnumPermission;
import models.NodesPermision.Permission;
import models.NodesPermision.PermissionNode;
import models.NodesPermision.RootPermissions;
import models.NodesPermision.TopicPermision;
import models.NodesPermision.UserPermission;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class PersistenceAdmin {
	private static final String TAG_USER = "user";
	private static final String TAG_DATAS_REQUEST = "datasRequest";
	private static final String TAG_FORMAT_REQUEST = "formatRequest";
	private static final String TYPE_REQUEST_ELEMENT = "TypeRequest";
	private static final String PASSWORD_USER = "Password";
	private static final String NAME_USER = "name_user";
	private static final String TAG_RESPONSE_BOOLEAN = "respBoolean";
	private static final String TAG_ID_CLIENT = "idClient";
	private static final String TAG_PATH_IMAGE = "path_image";
	private static final String TAG_ROOT = "ROOT_TREE";
	private static final String TAG_PATH_FILE = "pathFile";
	private static final String TAG_NODE_CHILD = "NODE_CHILD";
	private static final String TAG_NODE_FATHER = "NODE_FATHER";
	private static final String TAG_ID_NODE_XML = "ID_NODE_XML";
	private static final String TAG_TYPE_NODE_XML = "TYPE_NODE_XML";
	private static final String TAG_PAIR_NODES = "NODOS_ALTER";

	private MyTree tree;
	private TreePermision treePermision;

	public void addDataNodeConferenceAtXml(NodeConference Father, NodeConference Child, Document doc, String idNameUser,
			String pathImage) {
		Element nodesChange = doc.createElement(TAG_PAIR_NODES);

		Element idUser = doc.createElement(TAG_ID_CLIENT);
		idUser.appendChild(doc.createTextNode(idNameUser));

		Element nodeFather = doc.createElement(TAG_NODE_FATHER);
		Element idNodeF = doc.createElement(TAG_ID_NODE_XML);
		idNodeF.appendChild(doc.createTextNode(Father.getNameData()));
		Element typeNodeF = doc.createElement(TAG_TYPE_NODE_XML);
		typeNodeF.appendChild(doc.createTextNode(Father.getTypeNode().name()));
		nodeFather.appendChild(idNodeF);
		nodeFather.appendChild(typeNodeF);

		Element nodeChild = doc.createElement(TAG_NODE_CHILD);
		Element idNodeC = doc.createElement(TAG_ID_NODE_XML);
		idNodeC.appendChild(doc.createTextNode(Child.getNameData()));
		Element typeNodeC = doc.createElement(TAG_TYPE_NODE_XML);
		typeNodeC.appendChild(doc.createTextNode(Child.getTypeNode().name()));
		nodeChild.appendChild(idNodeC);
		nodeChild.appendChild(typeNodeC);
		Element nodePath = doc.createElement(TAG_PATH_IMAGE);
		nodePath.appendChild(doc.createTextNode(pathImage));
		nodesChange.appendChild(nodeFather);
		nodesChange.appendChild(idUser);
		nodesChange.appendChild(nodeChild);
		nodesChange.appendChild(nodePath);
		doc.appendChild(nodesChange);

	}
	public NodeGeneric<PermissionNode> getTreePermision() {
		return treePermision.getRoot();
	}
	public String createRequestDeleteNode(String idNodePermission) throws ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element idNodeC = doc.createElement(TAG_ID_NODE_XML);
		idNodeC.appendChild(doc.createTextNode(idNodePermission));
		doc.appendChild(idNodeC);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.getBuffer().toString();
	}
	public String createRequestAddNodePermission(String idNodeConference, String idNodeUser, String typePermise) throws ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element nodesChange = doc.createElement(TAG_PAIR_NODES);
		Element idUser = doc.createElement(TAG_ID_CLIENT);
		idUser.appendChild(doc.createTextNode(idNodeUser));

		Element nodeChild = doc.createElement(TAG_NODE_CHILD);
		Element idNodeC = doc.createElement(TAG_ID_NODE_XML);
		idNodeC.appendChild(doc.createTextNode(idNodeConference));
		Element typeNodeC = doc.createElement(TAG_TYPE_NODE_XML);
		typeNodeC.appendChild(doc.createTextNode(typePermise));
		nodeChild.appendChild(idNodeC);
		nodeChild.appendChild(typeNodeC);
		nodesChange.appendChild(idUser);
		nodesChange.appendChild(nodeChild);
		doc.appendChild(nodesChange);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.getBuffer().toString();
	}

	public String createRequestNodeDelete(NodeConference child, String idNameUser)
			throws ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element nodesChange = doc.createElement(TAG_PAIR_NODES);
		Element idUser = doc.createElement(TAG_ID_CLIENT);
		idUser.appendChild(doc.createTextNode(idNameUser));

		Element nodeChild = doc.createElement(TAG_NODE_CHILD);
		Element idNodeC = doc.createElement(TAG_ID_NODE_XML);
		idNodeC.appendChild(doc.createTextNode(child.getNameData()));
		Element typeNodeC = doc.createElement(TAG_TYPE_NODE_XML);
		typeNodeC.appendChild(doc.createTextNode(child.getTypeNode().name()));
		nodeChild.appendChild(idNodeC);
		nodeChild.appendChild(typeNodeC);
		nodesChange.appendChild(idUser);
		nodesChange.appendChild(nodeChild);
		doc.appendChild(nodesChange);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.getBuffer().toString();
	}

	public String createRequestAddNode(NodeConference Father, NodeConference Child, String idNameUser, String pathImage)
			throws ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		addDataNodeConferenceAtXml(Father, Child, doc, idNameUser, pathImage);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.getBuffer().toString();
	}

	public PersistenceAdmin() {
		this.tree = new MyTree(new RootConference("root"));
		this.treePermision = new TreePermision(new RootPermissions());
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
	public void printTree() {
		treePermision.printAll();
//		tree.printAll();
	}
	public NodeGeneric<NodeConference> getTreeConference() {
		return tree.getRoot();
	}

	public NodeGeneric<PermissionNode> getTreePermissions(String datas) {
		Document doc = convertStringToXMLDocument(datas);
		NodeList list = doc.getElementsByTagName(TAG_ROOT);
		Node rootXmlNode = list.item(0);
		this.treePermision = new TreePermision(new RootPermissions());
		NodeGeneric<PermissionNode> nodeRoot = treePermision.getRoot();
		createTreePermissions(nodeRoot, rootXmlNode);
		return nodeRoot;
	}
	public ArrayList<String> getListIdUsers() {
		return treePermision.getListIdUsers();
	}
	public PermissionNode getNodePermissionData(Node nodeActual,Node nodeFather) {
		if (nodeActual.getChildNodes().item(0).getChildNodes().item(0).getTextContent()
				.equalsIgnoreCase(EnumPermission.CONFERENCE.name())) {
			return new ConferencePermission(
					nodeActual.getChildNodes().item(0).getChildNodes().item(1).getTextContent());
		} else if (nodeActual.getChildNodes().item(0).getChildNodes().item(0).getTextContent()
				.equalsIgnoreCase(EnumPermission.TOPIC.name())) {
			return new TopicPermision(nodeActual.getChildNodes().item(0).getChildNodes().item(1).getTextContent());
		} else if (nodeActual.getChildNodes().item(0).getChildNodes().item(0).getTextContent()
				.equalsIgnoreCase(EnumPermission.USER.name())) {
			return new UserPermission(nodeActual.getChildNodes().item(0).getChildNodes().item(1).getTextContent());
		} else if (nodeActual.getChildNodes().item(0).getChildNodes().item(0).getTextContent()
				.equalsIgnoreCase(EnumPermission.CREATE.name())) {
			return new Permission(nodeActual.getChildNodes().item(0).getChildNodes().item(1).getTextContent(),
					EnumPermission.CREATE);
		} else if (nodeActual.getChildNodes().item(0).getChildNodes().item(0).getTextContent()
				.equalsIgnoreCase(EnumPermission.REPORT.name())) {
			return new Permission(nodeActual.getChildNodes().item(0).getChildNodes().item(1).getTextContent(),
					EnumPermission.REPORT);
		} else if (nodeActual.getChildNodes().item(0).getChildNodes().item(0).getTextContent()
				.equalsIgnoreCase(EnumPermission.DELETE.name())) {
			return new Permission(nodeActual.getChildNodes().item(0).getChildNodes().item(1).getTextContent(),
					EnumPermission.DELETE);
		}
		System.out.println("no se determino de que tipo de permiso es");
		return null;
	}

	public void createTreePermissions(NodeGeneric<PermissionNode> nodeFather, Node nodeActualXml) {
		for (int i = 1; i < nodeActualXml.getChildNodes().getLength(); i++) {
			NodeGeneric<PermissionNode> newNode = new NodeGeneric<PermissionNode>(
					getNodePermissionData(nodeActualXml.getChildNodes().item(i),nodeActualXml));
			nodeFather.addChild(newNode);
			createTreePermissions(newNode, nodeActualXml.getChildNodes().item(i));
		}
	}

	public NodeGeneric<NodeConference> getTreeConference(String treeXml) {
		Document doc = convertStringToXMLDocument(treeXml);
		NodeList list = doc.getElementsByTagName(TAG_ROOT);
		Node rootXmlNode = list.item(0);
		this.tree = new MyTree(
				new RootConference(rootXmlNode.getChildNodes().item(0).getChildNodes().item(1).getTextContent()));
		NodeGeneric<NodeConference> nodeRoot = tree.getRoot();
		createTree(nodeRoot, rootXmlNode);
		return nodeRoot;
	}

	public NodeConference getNodeData(Node nodeActual) {
		if (nodeActual.getChildNodes().item(0).getChildNodes().item(0).getTextContent()
				.equalsIgnoreCase(EnumConference.CONFERENCE.name())) {
			return new Conference(nodeActual.getChildNodes().item(0).getChildNodes().item(1).getTextContent());
		} else if (nodeActual.getChildNodes().item(0).getChildNodes().item(0).getTextContent()
				.equalsIgnoreCase(EnumConference.TOPIC.name())) {
			return new Topic(nodeActual.getChildNodes().item(0).getChildNodes().item(1).getTextContent());
		} else if (nodeActual.getChildNodes().item(0).getChildNodes().item(0).getTextContent()
				.equalsIgnoreCase(EnumConference.ATTENDEE.name())) {
			return new Attendee(nodeActual.getChildNodes().item(0).getChildNodes().item(1).getTextContent());
		} else if (nodeActual.getChildNodes().item(0).getChildNodes().item(0).getTextContent()
				.equalsIgnoreCase(EnumConference.LECTURER.name())) {
			return new Lecturer(nodeActual.getChildNodes().item(0).getChildNodes().item(1).getTextContent());
		}
		System.out.println("no se determino de que tipo es el nodo");
		return null;
	}

	public void createTree(NodeGeneric<NodeConference> nodeFather, Node nodeActualXml) {
		for (int i = 1; i < nodeActualXml.getChildNodes().getLength(); i++) {
			NodeGeneric<NodeConference> newNode = new NodeGeneric<NodeConference>(
					getNodeData(nodeActualXml.getChildNodes().item(i)));
			nodeFather.addChild(newNode);
			createTree(newNode, nodeActualXml.getChildNodes().item(i));
		}
	}

	public String createXmlType(TypeRequest type)
			throws ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element formatR = doc.createElement(TAG_FORMAT_REQUEST);

		Element typeRe = doc.createElement(TYPE_REQUEST_ELEMENT);
		typeRe.appendChild(doc.createTextNode(type.name()));
		Element datas = doc.createElement(TAG_DATAS_REQUEST);
		formatR.appendChild(typeRe);
		formatR.appendChild(datas);
		doc.appendChild(formatR);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.getBuffer().toString();
	}

	public String getExtencionFile(String path) {
		String extension = "";
		int i = path.lastIndexOf('.');
		if (i > 0) {
			extension = path.substring(i + 1);
		}
		System.out.println("la extecion extraida es: " + extension);
		return extension;
	}

	public String getIdClient(String datasIdClient) {
		Document doc = convertStringToXMLDocument(datasIdClient);
		return doc.getElementsByTagName(TAG_ID_CLIENT).item(0).getTextContent();
	}


	public String createUserNameXml(String nameUser, String password)
			throws TransformerConfigurationException, ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element user = doc.createElement(TAG_USER);

		Node name = doc.createElement(NAME_USER);
		name.appendChild(doc.createTextNode(nameUser));
		Node pass = doc.createElement(PASSWORD_USER);
		pass.appendChild(doc.createTextNode(password));

		user.appendChild(name);
		user.appendChild(pass);

		doc.appendChild(user);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.getBuffer().toString();
	}

	public String xmlComplianceRequest(String request, String datas) throws TransformerConfigurationException {
		Document requestDoc = convertStringToXMLDocument(request);
		Document datasDoc = convertStringToXMLDocument(datas);
		NodeList nodelist = requestDoc.getElementsByTagName(TAG_FORMAT_REQUEST);

		Node nodeImp = requestDoc.importNode(datasDoc.getChildNodes().item(0), true);
		nodelist.item(0).getChildNodes().item(1).appendChild(nodeImp);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(requestDoc), new StreamResult(writer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.getBuffer().toString();
	}

	private static Document convertStringToXMLDocument(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public TypeRequest getTypeResponse(String typeRequest) {
		Document doc = convertStringToXMLDocument(typeRequest);
		return TypeRequest.valueOf(doc.getElementsByTagName(TYPE_REQUEST_ELEMENT).item(0).getTextContent());
	}

	public boolean readResponseBoolean(String datas) {
		Document doc = convertStringToXMLDocument(datas);
		return Boolean.valueOf(doc.getElementsByTagName(TAG_RESPONSE_BOOLEAN).item(0).getTextContent());
	}

	public File getFile(String pathFile) {
		return new File(pathFile);
	}

	public String createNameFilePath(String pathFile)
			throws ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element pathName = doc.createElement(TAG_PATH_FILE);
		pathName.appendChild(doc.createTextNode(pathFile));
		doc.appendChild(pathName);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.getBuffer().toString();
	}

}
