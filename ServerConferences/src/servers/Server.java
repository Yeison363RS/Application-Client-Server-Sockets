package servers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import models.Manager;
import models.NodeTypes.EnumConference;
import models.NodeTypes.NodeConference;
import servers.Connections.PersistenceManager;
import servers.Connections.ProfileClient;
import servers.Connections.ProfileConnection;
import servers.Connections.TypeRequest;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class Server {
	private static final String NAME_ADMIN = "2121";
	private DataInputStream dataIn;
	private DataOutputStream dataOut;

	private ServerSocket serverServices;
	private ServerSocket serverConections;
	private ArrayList<ProfileConnection> listConection;
	private Socket connection;

	private Thread threadRead;
	private Thread threadConections;
	private Thread threadConectionsLong;

	private PersistenceManager persistence;
	private Manager manager;
	protected boolean run;

	public Server() {
		this.persistence = new PersistenceManager();
		this.listConection = new ArrayList<ProfileConnection>();
		this.manager = new Manager();
		manager.chargeTreePermissions(persistence.getListUserId());
		this.connection = null;
		run = true;
		createServers();
		receiveConectionsTemp();
		receiveConectionsLong();
		readAllServices();
		readAllConections();
	}

	public void createServers() {
		try {
			serverServices = new ServerSocket(5000);
			serverConections = new ServerSocket(5001);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receiveConectionsTemp() {
		this.threadConections = new Thread(new Runnable() {
			public void run() {
				while (run) {
					try {
						connection = serverServices.accept();
						dataIn = new DataInputStream(connection.getInputStream());
						dataOut = new DataOutputStream(connection.getOutputStream());
						readAllServices();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		threadConections.start();
		Logger.getGlobal().log(Level.INFO, "Server listen on 5000!");
	}

	public void receiveConectionsLong() {
		this.threadConectionsLong = new Thread(new Runnable() {
			public void run() {
				while (run) {
					Socket clientConection;
					try {
						clientConection = serverConections.accept();
						isAvaliableConecction(clientConection);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Logger.getGlobal().log(Level.INFO, String.valueOf(listConection.size()));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		threadConectionsLong.start();
		Logger.getGlobal().log(Level.INFO, "Server listen on 5001!");
	}

	public void isAvaliableConecction(Socket clientConection) {
		try {
			DataInputStream dataI = new DataInputStream(clientConection.getInputStream());
			String datasClient = dataI.readUTF();
			String[] datas = persistence.getDatasClient(datasClient);
			if (isAdmin(datas[0], clientConection)) {
				return;
			}
			if (persistence.isCorrestUserAndPassword(datas[0], datas[1])) {
				ProfileClient profile = persistence.getProfileClient(datasClient);
				listConection.add(new ProfileConnection(profile.getName(), profile.getIdClient(), clientConection));
				nofityConecction(clientConection, true, profile.getIdClient());
				try {
					sendNotificationConnections(persistence.createXmlType(TypeRequest.GET_TREE_CONFERENCE),
							persistence.getTreeXml(manager.getTreeConference()));
				} catch (NumberFormatException | TransformerConfigurationException | ParserConfigurationException e) {
					e.printStackTrace();
				}
			}
			nofityConecction(clientConection, false, "");
		} catch (IOException | TransformerConfigurationException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public boolean isAdmin(String nameUser, Socket clientConection)
			throws IOException, TransformerConfigurationException, ParserConfigurationException {
		if (nameUser.equalsIgnoreCase(NAME_ADMIN)) {
			ProfileClient profile = new ProfileClient(nameUser);
			listConection.add(new ProfileConnection(profile.getName(), profile.getIdClient(), clientConection));
			sendTreePersmissions();
			sendNotificationConnections(persistence.createXmlType(TypeRequest.GET_TREE_CONFERENCE),
					persistence.getTreeXml(manager.getTreeConference()));
			return true;
		}
		return false;
	}

	public void sendTreePersmissions() {
		for (int i = 0; i < listConection.size(); i++) {
			if (listConection.get(i).getName().equalsIgnoreCase(NAME_ADMIN)) {
				try {
					listConection.get(i)
							.sendNotification(persistence.xmlComplianceResponse(
									persistence.createXmlType(TypeRequest.GET_TREE_PERMISSIONS),
									persistence.getTreePermissionXml(manager.getTreePermision())));
				} catch (TransformerConfigurationException | TransformerFactoryConfigurationError
						| ParserConfigurationException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void nofityConecction(Socket profileConection, boolean confirm, String idClientConecction)
			throws IOException {
		DataOutputStream dataOu = new DataOutputStream(profileConection.getOutputStream());
		try {
			dataOu.writeUTF(persistence.xmlComplianceResponse(persistence.createXmlType(TypeRequest.CONNECTED),
					persistence.createResponseConecction(confirm, idClientConecction)));
		} catch (TransformerConfigurationException | IOException | TransformerFactoryConfigurationError
				| ParserConfigurationException e) {
			e.printStackTrace();
		}
	}


	public void readRequestService() throws IOException {
		String request = dataIn.readUTF();
		TypeRequest typeRequest = persistence.classifyRequest(request);
		switch (typeRequest) {
		case NEW_CLIENT:
			saveClientDatas(request);
			break;
		case GET_REPORT:
			requestServiceReportNode(request);
			break;
		case ADD_NODE:
			requestServiceAddNode(request);
			break;
		case GET_FILE:
			sendFileReport(request);
			break;
		case DELETE:
			requestServiceDeleteNode(request);
			break;
		case DELETE_PERMISSION:
			deletePermissionNode(request);
			break;
		case ADD_PERMISSION:
			addNodePermission(request);
			break;
		default:
			break;
		}
	}

	private void deletePermissionNode(String request) {
		manager.deleteNodePermission(persistence.getIdNodeChild(request));
		try {
			sendResponseService(persistence.xmlComplianceResponse(
					persistence.createXmlType(TypeRequest.DELETE_PERMISSION), persistence.createResponseBoolean(true)));
		} catch (TransformerConfigurationException | IOException | TransformerFactoryConfigurationError
				| ParserConfigurationException e) {
			e.printStackTrace();
		}
		sendTreePersmissions();
	}

	private void addNodePermission(String request) {
		manager.addPermission(persistence.getIdUserClient(request), persistence.getIdNodeChild(request),
				manager.getTypePermision(persistence.getTypeNodePermission(request)));
		try {
			sendResponseService(persistence.xmlComplianceResponse(persistence.createXmlType(TypeRequest.ADD_PERMISSION),
					persistence.createResponseBoolean(true)));
		} catch (TransformerConfigurationException | IOException | TransformerFactoryConfigurationError
				| ParserConfigurationException e) {
			e.printStackTrace();
		}
		sendTreePersmissions();
	}

	private void requestServiceReportNode(String request) {
		NodeConference nodeChild = persistence.getNodeChild(request);
		try {
			if (manager.isPermitedReportNode(nodeChild, persistence.getIdUserClient(request))) {
				sendResponseService(persistence.xmlComplianceResponse(persistence.createXmlType(TypeRequest.GET_REPORT),
						persistence.createResponseBoolean(true)));
			} else {
				sendResponseService(persistence.xmlComplianceResponse(persistence.createXmlType(TypeRequest.GET_REPORT),
						persistence.createResponseBoolean(false)));
			}
		} catch (TransformerConfigurationException | IOException | TransformerFactoryConfigurationError
				| ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void sendFileReport(String request) throws IOException {
		NodeConference nodeChild = persistence.getNodeChild(request);
		String path = manager.generateReport(nodeChild.getNameData());
		File file = new File(path);
		if (file.exists()) {
			byte[] buffer = new byte[4096];
			OutputStream stream = connection.getOutputStream();
			FileInputStream input = new FileInputStream(file);
			int read = 0;
			while ((read = input.read(buffer)) != -1) {
				stream.write(buffer, 0, read);
			}
			Logger.getGlobal().log(Level.INFO, "Termino escribir Archivo");
			input.close();
		}
	}

	public void requestServiceDeleteNode(String request) {
		NodeConference nodeChild = persistence.getNodeChild(request);
		try {
			if (manager.deleteNodeConferenceByUser(nodeChild, persistence.getIdUserClient(request))) {
				updateTreeConference();
				sendResponseService(persistence.xmlComplianceResponse(persistence.createXmlType(TypeRequest.DELETE),
						persistence.createResponseBoolean(true)));
			} else {
				sendResponseService(persistence.xmlComplianceResponse(persistence.createXmlType(TypeRequest.DELETE),
						persistence.createResponseBoolean(false)));
			}
		} catch (TransformerConfigurationException | IOException | TransformerFactoryConfigurationError
				| ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void readFileImageLecturer(String pathFile) throws IOException, TransformerConfigurationException,
			TransformerFactoryConfigurationError, ParserConfigurationException {
		byte[] buffer = new byte[4096];
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		InputStream stream = connection.getInputStream();
		FileOutputStream outPut = new FileOutputStream(new File(pathFile));
		int read = 0;
		while (stream.available() != 0 && (read = stream.read(buffer)) != -1) {
			outPut.write(buffer, 0, read);
		}
		outPut.close();
	}

	public void requestServiceAddNode(String request) {
		NodeConference nodeChild = persistence.getNodeChild(request);
		NodeConference nodeFather = manager.getDataNodeFather(persistence.getIdNameFather(request));
		try {
			if (manager.addNodeConference(nodeFather, persistence.getIdUserClient(request), nodeChild)) {
				if (nodeChild.getTypeNode() == EnumConference.LECTURER) {
					System.out.println("inteerasadasda");
					nodeChild.setPathImageLecturer(persistence.getPathImageLecturer(request));
					readFileImageLecturer(persistence.getPathImageLecturer(request));
				}
				sendResponseService(persistence.xmlComplianceResponse(persistence.createXmlType(TypeRequest.ADD_NODE),
						persistence.createResponseBoolean(true)));
				updateTreeConference();
			} else {
				sendResponseService(persistence.xmlComplianceResponse(persistence.createXmlType(TypeRequest.ADD_NODE),
						persistence.createResponseBoolean(false)));
			}
		} catch (TransformerConfigurationException | IOException | TransformerFactoryConfigurationError
				| ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void updateTreeConference() {
		try {
			sendNotificationConnections(persistence.createXmlType(TypeRequest.GET_TREE_CONFERENCE),
					persistence.getTreeXml(manager.getTreeConference()));
		} catch (NumberFormatException | TransformerConfigurationException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void saveClientDatas(String datasClient) throws IOException {
		String[] datas = persistence.getDatasClient(datasClient);
		try {
			if (persistence.calculateAvalibleNameUser(datas[0], datas[1])) {
				manager.addUserTreePermission(persistence.saveNameUser(datas[0], datas[1]));
				sendResponseService(persistence.xmlComplianceResponse(persistence.createXmlType(TypeRequest.NEW_CLIENT),
						persistence.createResponseBoolean(true)));
				sendTreePersmissions();
			} else {
				sendResponseService(persistence.xmlComplianceResponse(persistence.createXmlType(TypeRequest.NEW_CLIENT),
						persistence.createResponseBoolean(false)));
			}
		} catch (TransformerConfigurationException | IOException | TransformerFactoryConfigurationError
				| ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void sendResponseService(String docResponse) throws IOException {
		dataOut.writeUTF(docResponse);
		connection.close();
		this.connection = null;
	}

	public void readAllServices() {
		this.threadRead = new Thread(new Runnable() {
			public void run() {
				try {
					if (connection != null && connection.isConnected()) {
						if (dataIn.available() > 0) {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							readRequestService();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		this.threadRead.start();
	}

	public void readAllConections() {
		this.threadRead = new Thread(new Runnable() {
			public void run() {
				while (run) {
					for (int i = 0; i < listConection.size(); i++) {
						try {
							if (listConection.get(i).isAvaliableChanel()) {
								readRequestConection(i);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		this.threadRead.start();
	}

	public void readRequestConection(int indexConection) throws IOException {
	}

	public void sendNotificationConnections(String notification, String datas) {
		try {
			for (int i = 0; i < listConection.size(); i++) {
				listConection.get(i).sendNotification(persistence.xmlComplianceResponse(notification, datas));
			}
		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
	}
}
