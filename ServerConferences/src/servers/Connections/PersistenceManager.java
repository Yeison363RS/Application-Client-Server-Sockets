package servers.Connections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import models.NodeGeneric;
import models.NodeTypes.Attendee;
import models.NodeTypes.Conference;
import models.NodeTypes.EnumConference;
import models.NodeTypes.Lecturer;
import models.NodeTypes.NodeConference;
import models.NodeTypes.Topic;
import models.NodesPermision.PermissionNode;

public class PersistenceManager {

	private static final String TAG_STATE_CONECCTION = "stateConecction";
	private static final String TAG_ID_CLIENT = "idClient";
	private static final String TAG_PATH_IMAGE = "path_image";

	private static final String LIST_USERS_TXT = "listUsers.txt";
	private static final String NAME_USER = "name_user";
	private static final String TAG_ID = "id";
	private static final String TYPE_REQUEST_ELEMENT = "TypeRequest";
	private static final String DB_ID = "./db/id.json";
	private static final String TAG_PATH_FILE = "pathFile";

	private static final String TAG_FORMAT_RESPONSE = "formatResponse";
	private static final String PASSWORD_USER = "Password";
	private static final String TAG_DATAS_REQUEST = "datasRequest";
	private static final String TAG_RESPONSE_BOOLEAN = "respBoolean";

	private static final String TAG_ROOT = "ROOT_TREE";
	private static final String TAG_ID_NODE_XML = "ID_NODE_XML";
	private static final String TAG_DATAS_NODE_XML = "DATAS_NODE_XML";
	private static final String TAG_TYPE_NODE_XML = "TYPE_NODE_XML";
	private static final String TAG_NODE_CHILD = "NODE_CHILD";
	private static final String TAG_NODE_FATHER = "NODE_FATHER";

	public PersistenceManager() {

	}
	public String getTreePermissionXml(NodeGeneric<PermissionNode> nodeRoot)
			throws ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootXml = doc.createElement(TAG_ROOT);
		addNodeTreePermissionDocXml(rootXml, nodeRoot, doc);
		doc.appendChild(rootXml);
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

	public void addNodeTreePermissionDocXml(Node nodeXml, NodeGeneric<PermissionNode> nodeActual, Document doc) {
		addDataNodeTreePermission(nodeXml, nodeActual, doc);
		for (int i = 0; i < nodeActual.getChildren().size(); i++) {
			Element nodeChildXml = doc.createElement(TAG_NODE_CHILD);
			nodeXml.appendChild(nodeChildXml);
			addNodeTreePermissionDocXml(nodeChildXml, nodeActual.getChildren().get(i), doc);
		}
	}

	public void addDataNodeTreePermission(Node nodeXml, NodeGeneric<PermissionNode> nodeActual, Document doc) {
		Element dataNode = doc.createElement(TAG_DATAS_NODE_XML);
		Element idNode = doc.createElement(TAG_ID_NODE_XML);
		Element typeNode = doc.createElement(TAG_TYPE_NODE_XML);
		typeNode.appendChild(doc.createTextNode(nodeActual.getData().getDataPermision().name()));
		idNode.appendChild(doc.createTextNode(nodeActual.getData().getIdNodePermission()));
		dataNode.appendChild(typeNode);
		dataNode.appendChild(idNode);
		nodeXml.appendChild(dataNode);
	}

	public String getTreeXml(NodeGeneric<NodeConference> nodeRoot)
			throws ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootXml = doc.createElement(TAG_ROOT);
		addNodeDocXml(rootXml, nodeRoot, doc);
		doc.appendChild(rootXml);
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

	public void addDataNode(Node nodeXml, NodeGeneric<NodeConference> nodeActual, Document doc) {
		Element dataNode = doc.createElement(TAG_DATAS_NODE_XML);
		Element idNode = doc.createElement(TAG_ID_NODE_XML);
		Element typeNode = doc.createElement(TAG_TYPE_NODE_XML);
		typeNode.appendChild(doc.createTextNode(nodeActual.getData().getTypeNode().name()));
		idNode.appendChild(doc.createTextNode(nodeActual.getData().getNameData()));
		dataNode.appendChild(typeNode);
		dataNode.appendChild(idNode);
		nodeXml.appendChild(dataNode);
	}

	public void addNodeDocXml(Node nodeXml, NodeGeneric<NodeConference> nodeActual, Document doc) {
		addDataNode(nodeXml, nodeActual, doc);
		for (int i = 0; i < nodeActual.getChildren().size(); i++) {
			Element nodeChildXml = doc.createElement(TAG_NODE_CHILD);
			nodeXml.appendChild(nodeChildXml);
			addNodeDocXml(nodeChildXml, nodeActual.getChildren().get(i), doc);
		}
	}

	public String[] getDatasClient(String userName) {
		String[] datas = new String[2];
		Document doc = convertStringToXMLDocument(userName);
		doc.getDocumentElement().normalize();
		datas[0] = doc.getElementsByTagName(NAME_USER).item(0).getTextContent();
		datas[1] = doc.getElementsByTagName(PASSWORD_USER).item(0).getTextContent();
		return datas;
	}

	public ArrayList<String> getListUserId() {
		ArrayList<String> listIdUser=new ArrayList<>();
		try {
			File file = new File(LIST_USERS_TXT);
			if (file.exists()) {
				FileReader fileReader = new FileReader(file);
				BufferedReader reader = new BufferedReader(fileReader);
				String line = null;
				while ((line = reader.readLine()) != null) {
					String[] datas = line.split(",");
					listIdUser.add(datas[0]);
				}
				reader.close();
				fileReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listIdUser;
	}

	public boolean calculateAvalibleNameUser(String nameU, String pasString) {
		try {
			File file = new File(LIST_USERS_TXT);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] datas = line.split(",");
				if (datas[1].equalsIgnoreCase(nameU)) {
					return false;
				}
			}
			reader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean isCorrestUserAndPassword(String nameU, String pasString) {
		try {
			File file = new File(LIST_USERS_TXT);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] datas = line.split(",");
				if (datas[1].equalsIgnoreCase(nameU) && datas[2].equalsIgnoreCase(pasString)) {
					return true;
				}
			}
			reader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String saveNameUser(String name, String pasString) {
		FileWriter filew;
		String idUser;
		idUser = String.valueOf(getIdAvaliable(DB_ID)) + name;
		try {
			File file = new File(LIST_USERS_TXT);
			filew = new FileWriter(file, true);
			BufferedWriter bfw = new BufferedWriter(filew);
			if (file.length() != 0) {
				bfw.newLine();
			}
			bfw.write(idUser + "," + name + "," + pasString);
			bfw.close();
			filew.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return idUser;
	}

	public static long readIdFile(String pathFileId) {
		File file = new File(pathFileId);
		if (!file.exists()) {
			try {
				file.createNewFile();
				return 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (Reader reader = new FileReader(file)) {
			return compareValidateDatas(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	private static long compareValidateDatas(Reader reader) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = "";
		line = bufferedReader.readLine();
		JSONObject id_client = new JSONObject(line);
		long id = (line != null) ? (int) id_client.get(TAG_ID) : 0;
		reader.close();
		bufferedReader.close();
		return id;
	}

	public static void writeIdFile(long id, String pathFileId) {
		JSONObject jsonFile = new JSONObject();
		jsonFile.put(TAG_ID, id);
		try (FileWriter file = new FileWriter(pathFileId, false)) {
			file.write(jsonFile.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long getIdAvaliable(String pathIdFile) {
		long idgenerate = readIdFile(pathIdFile);
		writeIdFile(idgenerate + 1, pathIdFile);
		return idgenerate;
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

	public File getFile(String pathFile) {
		Document doc = convertStringToXMLDocument(pathFile);
		return new File(doc.getElementsByTagName(TAG_PATH_FILE).item(0).getTextContent());
	}

	public ProfileClient getProfileClient(String datasClient) throws IOException {
		Document doc = convertStringToXMLDocument(datasClient);
		doc.getDocumentElement().normalize();
		return generateProfile(doc.getElementsByTagName(NAME_USER).item(0).getTextContent(),
				doc.getElementsByTagName(PASSWORD_USER).item(0).getTextContent());
	}

	public TypeRequest classifyRequest(String typeRequest) {
		Document doc = convertStringToXMLDocument(typeRequest);
		return TypeRequest.valueOf(doc.getElementsByTagName(TYPE_REQUEST_ELEMENT).item(0).getTextContent());
	}

	public ProfileClient generateProfile(String nameClient, String password) {
		try {
			File file = new File(LIST_USERS_TXT);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] datas = line.split(",");
				if (datas[1].equals(nameClient) && datas[2].equals(password)) {
					return new ProfileClient(datas[1], datas[2], datas[0]);
				}
			}
			reader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String createXmlType(TypeRequest type)
			throws ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element formatR = doc.createElement(TAG_FORMAT_RESPONSE);

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
		String xmlString = writer.getBuffer().toString();
		return xmlString;
	}

	public String createResponseBoolean(boolean confir)
			throws TransformerConfigurationException, ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element user = doc.createElement(TAG_RESPONSE_BOOLEAN);
		user.appendChild(doc.createTextNode(String.valueOf(confir)));
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

	public String createResponseConecction(boolean confir, String idClientConecction)
			throws ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element state = doc.createElement(TAG_STATE_CONECCTION);
		Element response = doc.createElement(TAG_RESPONSE_BOOLEAN);
		response.appendChild(doc.createTextNode(String.valueOf(confir)));
		Element idClient = doc.createElement(TAG_ID_CLIENT);
		idClient.appendChild(doc.createTextNode(idClientConecction));
		state.appendChild(response);
		state.appendChild(idClient);
		doc.appendChild(state);

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

	public String xmlComplianceResponse(String request, String datas)
			throws TransformerConfigurationException, TransformerFactoryConfigurationError {
		Document requestDoc = convertStringToXMLDocument(request);
		Document datasDoc = convertStringToXMLDocument(datas);
		NodeList nodelist = requestDoc.getElementsByTagName(TAG_FORMAT_RESPONSE);
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

	public String getExtencionFile(String request) {
		Document doc = convertStringToXMLDocument(request);
		doc.getDocumentElement().normalize();
		return doc.getElementsByTagName(TAG_PATH_IMAGE).item(0).getTextContent();
	}

	public NodeConference getNodeChild(String request) {
		Document doc = convertStringToXMLDocument(request);
		doc.getDocumentElement().normalize();
		NodeList listC = doc.getElementsByTagName(TAG_NODE_CHILD);
		return getNodeData(listC.item(0).getChildNodes().item(1).getTextContent(),
				listC.item(0).getChildNodes().item(0).getTextContent());
	}

	public String getPathImageLecturer(String request) {
		Document doc = convertStringToXMLDocument(request);
		doc.getDocumentElement().normalize();
		NodeList listU = doc.getElementsByTagName(TAG_PATH_IMAGE);
		return listU.item(0).getTextContent();
	}
	public String getIdNodeChild(String request) {
		Document doc = convertStringToXMLDocument(request);
		doc.getDocumentElement().normalize();
		NodeList listU = doc.getElementsByTagName(TAG_ID_NODE_XML);
		return listU.item(0).getTextContent();
	}
	public String getTypeNodePermission(String request) {
		Document doc = convertStringToXMLDocument(request);
		doc.getDocumentElement().normalize();
		NodeList listU = doc.getElementsByTagName(TAG_TYPE_NODE_XML);
		return listU.item(0).getTextContent();
	}
	public String getIdUserClient(String request) {
		Document doc = convertStringToXMLDocument(request);
		doc.getDocumentElement().normalize();
		NodeList listU = doc.getElementsByTagName(TAG_ID_CLIENT);
		return listU.item(0).getTextContent();
	}

	public String getIdNameFather(String request) {
		Document doc = convertStringToXMLDocument(request);
		doc.getDocumentElement().normalize();
		NodeList listF = doc.getElementsByTagName(TAG_NODE_FATHER);
		return listF.item(0).getChildNodes().item(0).getTextContent();
	}

	public NodeConference getNodeData(String typeNode, String dataNode) {
		if (typeNode.equalsIgnoreCase(EnumConference.CONFERENCE.name())) {
			return new Conference(dataNode);
		} else if (typeNode.equalsIgnoreCase(EnumConference.TOPIC.name())) {
			return new Topic(dataNode);
		} else if (typeNode.equalsIgnoreCase(EnumConference.ATTENDEE.name())) {
			return new Attendee(dataNode);
		} else if (typeNode.equalsIgnoreCase(EnumConference.LECTURER.name())) {
			return new Lecturer(dataNode);
		}
		Logger.getGlobal().log(Level.INFO, "no se determino de que tipo es el nodo el que se quiere agregar");
		return null;
	}

	

}
