package views;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class PanelOptions extends JPanel{
	private static final String REPORTE_OPTION_TXT = "reporte";
	private static final String CREAR_OPTION_TXT = "crear";
	private static final String BORRAR_OPTION_TXT = "borrar";
	private static final String ELEGIR_USUARIO_TXT = "Elegir Usuario";
	private static final String TIPO_PERMISO_TXT = "tipo_permiso";
	
	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboUsers;
	private JComboBox<String> comboPermissions;

	public PanelOptions(ActionListener listener) {
		this.setBackground(PanelLoad.COLOR_BACK);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSettings();
		addCompnents(listener);
	}
	public void updateModel(ArrayList<String> listIdUser) {
		createModelComboPermisions();
		createModelComboUsersBox(listIdUser);
	}
	public void createModelComboUsersBox(ArrayList<String> idUsers) {
		DefaultComboBoxModel<String>  modelUsers =new DefaultComboBoxModel<String>();
		for (String idUser : idUsers) {
			modelUsers.addElement(idUser);
		}
		this.comboUsers.removeAllItems();
		this.comboUsers.setModel(modelUsers);
	} 
	public void createModelComboPermisions() {
		DefaultComboBoxModel<String>  modelPermissions =new DefaultComboBoxModel<String>();
		ArrayList<String> listOptions=new ArrayList<String>(Arrays.asList(BORRAR_OPTION_TXT,CREAR_OPTION_TXT,REPORTE_OPTION_TXT));
		for (String option : listOptions) {
			modelPermissions.addElement(option);
		}	
		this.comboPermissions.removeAllItems();
		this.comboPermissions.setModel(modelPermissions);		
	}
	public String getIdUserSelected() {
		return comboUsers.getSelectedItem().toString();
	}
	public String getTypePermission() {
		return comboPermissions.getSelectedItem().toString();
	}
	public void setSettings() {
		this.comboPermissions = new JComboBox<String>();
		this.comboUsers = new JComboBox<String>();
	}

	public void addCompnents(ActionListener listener) {
		this.add(new JLabel(TIPO_PERMISO_TXT));
		this.add(comboPermissions);
		this.add(new JLabel(ELEGIR_USUARIO_TXT));
		this.add(comboUsers);
		this.add(new MyButton(FrameMain.SAVE_BTN_COMAND, FrameMain.SAVE_BTN_COMAND,listener, PanelLoad.COLOR_MY_PURPLE, new Font("arial", Font.BOLD, 16)));
	}
}
