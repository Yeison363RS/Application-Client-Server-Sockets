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

public class FrameUser extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Font MY_FONT_BTN = new Font("arial", Font.BOLD, 16);
	public static final String DELETE_NODE_CONFERENCE = "borrar";
	public static final String REPORT_NODE_CONFERENCE = "report";
	public static final Font MY_IDENTIFIER = new Font("arial", Font.BOLD, 20);
	public static final String ADD_NODE = "agregar";
	public static final String COMMAND_SAVE = "guardar";

	public static final int WIDTH = 500;
	public static final int HEIGHT = 400;
	private JPanel panelContent;
	private PanelCreateUser panelCreate;
	private PanelLoad panelLoad;
	private ActionListener listener;
	private PanelOptionsConference panelOptions;
	private PanelTreeConference panelTree;
	

	public FrameUser(ActionListener actionLis) {
		this.listener = actionLis;
		init();
	}

	public String getNameUser() {
		return panelCreate.getNameUser();
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
			this.panelContent.add(panelTree, BorderLayout.CENTER);
			addPanelOptions();
			this.revalidate();
			this.repaint();
			return;
		}
		panelLoad.showMenssageConetion();
	}
	public void showMessageSave(boolean optionM) {
		panelCreate.showMessageSave(optionM);
	}

	public String getNameUserLoad() {
		return panelLoad.getNameUserLoad();
	}

	public void createPanelCreate() {
		this.panelContent.removeAll();
		this.panelLoad.setVisible(false);
		panelCreate.clearForm();
		panelCreate.setVisible(true);
		this.panelContent.add(panelCreate, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}

	public void createPanelLoad() {
		this.panelContent.removeAll();
		this.panelCreate.setVisible(false);
		panelLoad.clearForm();
		this.panelLoad.setVisible(true);
		this.panelContent.add(panelLoad, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}

	public String getPasswordUser() {
		if (panelCreate.isVisible()) {
			return panelCreate.getPassword();
		}
		return panelLoad.getPassword();
	}

	public String getIdNodeFather() {
		return panelTree.getNodeSelected();
	}

	public void init() {
		this.panelCreate = new PanelCreateUser(this.listener);
		this.panelLoad = new PanelLoad(this.listener);
		setSettingsFrame();
		this.setVisible(true);
	}

	public void setSettingsFrame() {
		setSettingsPanelContent();
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void createComponents() {
		this.panelTree = new PanelTreeConference();
		this.panelContent = new JPanel();
		panelContent.setBackground(PanelLoad.COLOR_BACK);
		this.panelOptions = new PanelOptionsConference(this.listener);
	}

	public void setSettingsPanelContent() {
		createComponents();
		this.panelContent.setLayout(new BorderLayout());
		addPanelOptions();
		panelContent.add(panelTree, BorderLayout.CENTER);
		this.add(panelContent, BorderLayout.CENTER);
		createPanelCreate();
	}

	public void updatePanelConference(NodeGeneric<NodeConference> node, ArrayList<String> idUsers) {
		this.panelContent.removeAll();
		panelTree.createTree(node);
		this.panelContent.add(panelTree, BorderLayout.CENTER);
		addPanelOptions();
		this.revalidate();
		this.repaint();
	}

	public String getOptionNodeSelect() {
		return panelOptions.getTypeNodeConference();
	}

	public String getDataNewNode() {
		return this.panelOptions.getNameNode();
	}

	public void addPanelOptions() {
		this.panelContent.add(panelOptions, BorderLayout.WEST);
	}

	public void updatePanelConference(NodeGeneric<NodeConference> treeConference) {
		panelTree.createTree(treeConference);
	}

	public String getPathImage() {
		return panelOptions.getTextPathImage();
	}
}
