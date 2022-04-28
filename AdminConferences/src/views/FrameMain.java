package views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.NodeGeneric;
import models.NodeTypes.NodeConference;
import models.NodesPermision.EnumPermission;
import models.NodesPermision.PermissionNode;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class FrameMain extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static final String DELETE_NODO_PERMISSION = "Borrar_Nodo";
	public static final Font MY_FONT_BTN = new Font("arial", Font.BOLD, 16);
	public static final Font MY_IDENTIFIER = new Font("arial", Font.BOLD, 20);
	public static final String UPDATE_TREE_PERMISSIONS = "Arbol_permisos";
	public static final String UPDATE_TREE_CONFERENCE = "Arbol_conferencias";
	public static final String SAVE_BTN_COMAND = "Agregar_Permiso";
	public static final int WIDTH = 500;
	public static final int HEIGHT = 400;
	private PanelOptions panelOptions;
	private PanelTree panelPermission;
	private ActionListener listener;
	private PanelLoad panelLoad;

	private PanelTreeConference panelConference;
	private JPanel panelContent;

	public FrameMain(ActionListener listener) {
		this.listener = listener;
		init();
	}

	public EnumPermission getPermission() {
		return getTypePermision(panelOptions.getTypePermission());
	}

	public void createPanelLoad() {
		this.panelContent.removeAll();
		panelLoad.clearForm();
		this.panelLoad.setVisible(true);
		this.panelContent.add(panelLoad, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}

	public String getIdUser() {
		return panelOptions.getIdUserSelected();
	}

	public String getIdNodeSelected() {
		return panelConference.getNodeSelected();
	}
	public String getTypeNodePermission() {
		return panelOptions.getTypePermission();
	}
	public String getIdSelected() {
		return panelPermission.getNodeSelected();
	}

	public EnumPermission getTypePermision(String permission) {
		switch (permission) {
		case "borrar":
			return EnumPermission.DELETE;
		case "crear":
			return EnumPermission.CREATE;
		case "reporte":
			return EnumPermission.REPORT;
		default:
			return EnumPermission.REPORT;
		}
	}

	public void init() {
		setSettingsFrame();
		this.setVisible(true);
	}

	public void updatePanelConference(NodeGeneric<NodeConference> node, ArrayList<String> idUsers) {
		this.panelContent.removeAll();
		setSettingsPanelOptionsConference();
		panelOptions.updateModel(idUsers);
		panelConference.createTree(node);
		this.panelContent.add(panelConference, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	public void setSettingPanelIdentifier(String idPerson) {
		JPanel panelIdentifier=new JPanel();
		panelIdentifier.setBackground(PanelLoad.COLOR_BACK);
		JLabel label=new JLabel(idPerson);
		label.setFont(MY_IDENTIFIER);
		panelIdentifier.add(label);
		this.add(panelIdentifier,BorderLayout.NORTH);
	}
	public void conecctedServer(boolean conected) {
		if (conected) {
			this.panelContent.removeAll();
			this.panelContent.add(panelPermission, BorderLayout.CENTER);
			this.revalidate();
			this.repaint();
			return;
		}
		panelLoad.showMenssageConetion();
	}

	public String getNameUserLoad() {
		return panelLoad.getNameUserLoad();
	}

	public String getPasswordUser() {
		return panelLoad.getPassword();
	}

	public void updatePanelPermissions(NodeGeneric<PermissionNode> node) {
		setSettingsBtns(); 
		this.panelContent.removeAll();
		setSettingsDeletePermission();
		this.panelContent.add(panelPermission, BorderLayout.CENTER);
		panelPermission.createTree(node);
		this.revalidate();
		this.repaint();
	}
	
	public void setSettingsBtns() {
		JPanel panel = new JPanel();
		panel.setBackground(PanelLoad.COLOR_BACK);
		MyButton btnSave = new MyButton(UPDATE_TREE_PERMISSIONS, UPDATE_TREE_PERMISSIONS, listener, PanelLoad.COLOR_MY_YELLOW,
				MY_FONT_BTN);
		MyButton btnUpdateTree = new MyButton(UPDATE_TREE_CONFERENCE, UPDATE_TREE_CONFERENCE, listener,
				PanelLoad.COLOR_MY_YELLOW, MY_FONT_BTN);
		panel.add(btnSave);
		panel.add(btnUpdateTree);
		this.add(panel, BorderLayout.SOUTH);
	}

	public void setSettingsFrame() {
		setSettingsPanelContent();
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void setSettingsPanelOptionsConference() {
		this.panelContent.add(panelOptions, BorderLayout.WEST);
	}

	public void setSettingsDeletePermission() {
		JPanel panel = new JPanel();
		panel.setBackground(PanelLoad.COLOR_BACK);
		panel.add(new MyButton(DELETE_NODO_PERMISSION, DELETE_NODO_PERMISSION, listener, PanelLoad.COLOR_MY_PINK, MY_FONT_BTN));
		this.panelContent.add(panel, BorderLayout.SOUTH);
	}

	public void createComponents() {
		this.panelOptions = new PanelOptions(listener);
		this.panelPermission = new PanelTree();
		this.panelConference = new PanelTreeConference();
		this.panelContent = new JPanel();
		this.panelLoad = new PanelLoad(listener);
	}

	public void setSettingsPanelContent() {
		createComponents();
		this.panelContent.setLayout(new BorderLayout());
		panelContent.add(panelLoad, BorderLayout.CENTER);
		this.add(panelContent, BorderLayout.CENTER);
	}
}
