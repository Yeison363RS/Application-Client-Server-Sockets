package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelOptionsConference extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String EMPTY_STRING = "";
	private static final String TXT_BTN_SEARCH = "Buscar";
	private JTextArea txtNameNode;
	private JComboBox<String> comboTypeNode;
	private JPanel panelChooser;
	private JTextArea txImg;

	public PanelOptionsConference(ActionListener actionLis) {
		this.setBackground(PanelLoad.COLOR_BACK);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		createComponents();
		createModelComboAddNodeConference();
		setSettingsButtons(actionLis);
	}
	private void createJFile() {
		this.add(panelChooser);
		JFileChooser fc=new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.png", "png","*.jpg","jpg");
		fc.setFileFilter(filtro);
		int seleccion=fc.showOpenDialog(panelChooser);
		if(seleccion==JFileChooser.APPROVE_OPTION){
		    File fichero=fc.getSelectedFile();
		    txImg.setText(fichero.getAbsolutePath());
		}
		reUpdate();
	}
	public void clearPanelCreate() {
		this.txImg.setText(EMPTY_STRING);
	}
	public String getTextPathImage() {
		return this.txImg.getText();
	}
	public void reUpdate() {
		this.revalidate();
		this.repaint();
	}
	public void createModelComboAddNodeConference() {
		DefaultComboBoxModel<String> modelTypeNode = new DefaultComboBoxModel<String>();
		ArrayList<String> list=new ArrayList<String>(Arrays.asList("Attendee", "Conference", "Topic", "Lecturer"));
		for (String  option : list) {
			modelTypeNode.addElement(option);
		}
		this.comboTypeNode.removeAllItems();
		this.comboTypeNode.setModel(modelTypeNode);
		this.add(comboTypeNode);
	}

	public void createComponents() {
		this.comboTypeNode = new JComboBox<String>();
		this.txtNameNode = new JTextArea();
		txImg = new JTextArea();
		this.panelChooser=new JPanel();	
		}

	public String getNameNode() {
		return this.txtNameNode.getText();
	}

	public String getTypeNodeConference() {
		return comboTypeNode.getSelectedItem().toString();
	}

	public void setSettingsButtons(ActionListener actionLis) {
		this.add(new JLabel("imagen solo para Conferencista"));
		this.add(txImg);
		MyButton btnSearch = new MyButton(TXT_BTN_SEARCH, TXT_BTN_SEARCH, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createJFile();
			}
		}, PanelLoad.COLOR_MY_PINK, FrameUser.MY_FONT_BTN);
		this.add(btnSearch);
		this.add(new JLabel("nombre nodo"));
		this.add(txtNameNode);
		this.add(new MyButton(FrameUser.ADD_NODE, FrameUser.ADD_NODE, actionLis, PanelLoad.COLOR_MY_GREEN,
				FrameUser.MY_FONT_BTN));
		this.add(new MyButton(FrameUser.DELETE_NODE_CONFERENCE, FrameUser.DELETE_NODE_CONFERENCE,
				actionLis, PanelLoad.COLOR_MY_GREEN, FrameUser.MY_FONT_BTN));
		this.add(new MyButton(FrameUser.REPORT_NODE_CONFERENCE, FrameUser.REPORT_NODE_CONFERENCE,
				actionLis, PanelLoad.COLOR_MY_GREEN, FrameUser.MY_FONT_BTN));
	}
}
