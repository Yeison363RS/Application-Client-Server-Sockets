package presenters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import admin.Admin;
import models.PersistenceAdmin;
import models.TypeRequest;
import views.FrameMain;
import views.IObsever;
import views.PanelLoad;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class Presenter implements ActionListener, IObsever {
	private String idClient;
	private FrameMain frame;
	private Admin client;
	private PersistenceAdmin persitence;

	public Presenter() {
		init();
	}

	public void init() {
		this.frame = new FrameMain(this);
		this.client = new Admin(this);
		this.persitence = new PersistenceAdmin();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case PanelLoad.COMAND_CONECT:
			getConecctionClient();
			break;
		case FrameMain.SAVE_BTN_COMAND:
			addNodeTreePermission();
			break;
		case FrameMain.DELETE_NODO_PERMISSION:
			deleteNodePermissionUser();
			break;
		case FrameMain.UPDATE_TREE_PERMISSIONS:
			frame.updatePanelPermissions(persitence.getTreePermision());
			break;
		case FrameMain.UPDATE_TREE_CONFERENCE:
			frame.updatePanelConference(persitence.getTreeConference(),persitence.getListIdUsers());
			break;
		default:
			break;
		}
	}
	public void deleteNodePermissionUser() {
		try {
			client.sendRequestServiceSave(persitence.xmlComplianceRequest(persitence.createXmlType(TypeRequest.DELETE_PERMISSION),
					persitence.createRequestDeleteNode(frame.getIdSelected())),null);
		} catch (TransformerConfigurationException | ParserConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}
	public void addNodeTreePermission() {
		String idNodeConference=frame.getIdNodeSelected();
		String idNodeUser=frame.getIdUser();
		String typePermise=frame.getTypeNodePermission();
		try {
			client.sendRequestServiceSave(persitence.xmlComplianceRequest(persitence.createXmlType(TypeRequest.ADD_PERMISSION),
					persitence.createRequestAddNodePermission(idNodeConference,idNodeUser,typePermise)),null);
		} catch (TransformerConfigurationException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
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
		case GET_TREE_PERMISSIONS:
			frame.updatePanelPermissions(persitence.getTreePermissions(datas));
			break;
		case CONNECTED:
			isConnectedAcept(datas);
			break;
		case ADD_PERMISSION:
			break;
		case DELETE_PERMISSION:
			break;
		default:
			break;
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
			frame.updatePanelConference(persitence.getTreeConference(datas),persitence.getListIdUsers());
			frame.setSettingPanelIdentifier(idClient);
			break;
		case GET_TREE_PERMISSIONS:
			frame.updatePanelPermissions(persitence.getTreePermissions(datas));
			break;
		default:
			break;
		}
	}
}