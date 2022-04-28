package presenters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import client.Client;
import models.PersistenceClient;
import models.TypeRequest;
import models.NodeTypes.EnumConference;
import models.NodeTypes.NodeConference;
import views.FrameUser;
import views.IObsever;
import views.PanelCreateUser;
import views.PanelLoad;

public class Presenter implements ActionListener, IObsever {
	private static final String DENEGATE_JERARQUIA = "No se puede agregar nodo mala jerarquia";
	private static final String DENEGATE_ACTION_USER_TXT = "no tiene permiso para esta accion";
	private static final String PROCESS_END_TXT = "proceso realizado";
	private static final String DENEGATE_PERMISSION_REPORT = "no tiene permitido generar reporte";
	private static final String TXT_GENERATE_INFORM = "informe generado revisar carpeta raiz";
	private String idClient;
	private FrameUser frame;
	private Client client;
	private PersistenceClient persitence;

	public Presenter() {
		init();
	}

	public void init() {
		this.frame = new FrameUser(this);
		this.client = new Client(this);
		this.persitence = new PersistenceClient();
	}

	public void saveUser() {
		try {
			try {
				client.sendRequestServiceSave(
						persitence.xmlComplianceRequest(persitence.createXmlType(TypeRequest.NEW_CLIENT),
								persitence.createUserNameXml(frame.getNameUser(), frame.getPasswordUser())),
						null);
			} catch (TransformerConfigurationException | ParserConfigurationException e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case FrameUser.COMMAND_SAVE:
			saveUser();
			break;
		case PanelLoad.COMAND_CREATE:
			frame.createPanelCreate();
			break;
		case PanelLoad.COMAND_CONECT:
			getConecctionClient();
			break;
		case FrameUser.ADD_NODE:
			addNodeTreeConference();
			break;
		case FrameUser.DELETE_NODE_CONFERENCE:
			deleteNode();
			break;
		case FrameUser.REPORT_NODE_CONFERENCE:
			getReportNode();
			break;
		case PanelCreateUser.CREATE_CREATE:
			frame.createPanelLoad();
			break;
		default:
			break;
		}
	}
	private void getReportNode() {
		NodeConference child=persitence.getDataNodeFather(frame.getIdNodeFather());
		try {
			client.sendRequestServiceSave(persitence.xmlComplianceRequest(persitence.createXmlType(TypeRequest.GET_REPORT), persitence.createRequestNodeDelete(child,idClient)),null);
		} catch (TransformerConfigurationException | ParserConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteNode() {
		NodeConference child=persitence.getDataNodeFather(frame.getIdNodeFather());
		try {
			client.sendRequestServiceSave(persitence.xmlComplianceRequest(persitence.createXmlType(TypeRequest.DELETE), persitence.createRequestNodeDelete(child,idClient)),null);
		} catch (TransformerConfigurationException | ParserConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}
	public void addNodeTreeConference() {
		NodeConference father=persitence.getDataNodeFather(frame.getIdNodeFather());
		NodeConference child=persitence.getDataIdNodeConference(frame.getDataNewNode(), frame.getOptionNodeSelect());
		if(father.addNodeChild(child)) {
			try {
				if(child.getTypeNode()==EnumConference.LECTURER) {
					client.sendRequestServiceSave(persitence.xmlComplianceRequest(persitence.createXmlType(TypeRequest.ADD_NODE)
							,persitence.createRequestAddNode(father,child,this.idClient,child.getNameData()+"."+persitence.getExtencionFile(frame.getPathImage()))), new File(frame.getPathImage()));
				}else {
					client.sendRequestServiceSave(persitence.xmlComplianceRequest(persitence.createXmlType(TypeRequest.ADD_NODE)
							,persitence.createRequestAddNode(father,child,this.idClient,"")), null);
				}
			} catch (TransformerConfigurationException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}			
		}else {
			JOptionPane.showMessageDialog(null, DENEGATE_JERARQUIA);
		}
	}


	public void getConecctionClient() {
		try {
			client.getConecction(persitence.createUserNameXml(frame.getNameUserLoad(), frame.getPasswordUser()));
		} catch (TransformerConfigurationException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void readResponse(String responseXml) {
		seletTypeRequest(persitence.getTypeResponse(responseXml), responseXml);
	}

	public void seletTypeRequest(TypeRequest typeRequest, String datas) {
		switch (typeRequest) {
		case NEW_CLIENT:
			frame.showMessageSave(persitence.readResponseBoolean(datas));
			break;
		case CONNECTED:
			isConnectedAcept(datas);
			break;
		case ADD_NODE:
			showPermission(persitence.readResponseBoolean(datas));
			break;
		case DELETE:
			showPermission(persitence.readResponseBoolean(datas));
			break;
		case GET_REPORT:
			getReportPdf(persitence.readResponseBoolean(datas));
			break;
		default:
			break;
		}
	}
	public void showPermission(boolean confirm) {
		if(!confirm) {
			JOptionPane.showMessageDialog(null, DENEGATE_ACTION_USER_TXT);			
		}
		JOptionPane.showMessageDialog(null, PROCESS_END_TXT);
	}
	public void getReportPdf(boolean confirm) {
		NodeConference child=persitence.getDataNodeFather(frame.getIdNodeFather());
		if(confirm) {
			try {
				client.getReport(persitence.xmlComplianceRequest(persitence.createXmlType(TypeRequest.GET_FILE), persitence.createRequestNodeDelete(child, idClient)));
			} catch (TransformerConfigurationException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, TXT_GENERATE_INFORM);
		}else {
			JOptionPane.showMessageDialog(null, DENEGATE_PERMISSION_REPORT);
		}
	}
	public void isConnectedAcept(String datas) {
		if (persitence.readResponseBoolean(datas)) {
			this.idClient = persitence.getIdClient(datas);
			frame.setSettingPanelIdentifier(idClient);
		}
		frame.conecctedServer(persitence.readResponseBoolean(datas));

	}

	@Override
	public void readNotification(String nofication) {
		seletTypeNotification(persitence.getTypeResponse(nofication), nofication);
	}

	public void seletTypeNotification(TypeRequest typeRequest, String datas) {
		switch (typeRequest) {
		case GET_TREE_CONFERENCE:
			frame.updatePanelConference(persitence.getTreeConference(datas));
			break;
		default:
			break;
		}
	}
}