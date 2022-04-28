package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class PanelLoad extends JPanel {
	
	private static final String ERROR_CONECCTION_DATAS = "Nombre de usuario o contraseña \n incorrectos";
	private static final long serialVersionUID = 1L;
	public static final String COMAND_CONECT = "conect";
	public static final String COMAND_CREATE = "create";
	private static final String TXT_BTN_CONECT = "Conectar";
	private static final String REQUEST_NAME_USER = "Nombre Usuario";
	private static final String REQUEST_PASSWORD_USER = "Contraseña";
	public static final Color COLOR_BACK = Color.decode("#BDFFF4");
	public static final Color COLOR_MY_GREEN = Color.decode("#54FF73");
	public static final Color COLOR_MY_YELLOW = Color.decode("#EBD060");
	public static final Color COLOR_MY_PINK = Color.decode("#FF7866");
	public static final Color COLOR_MY_PURPLE = Color.decode("#7C51E8");


	private JTextField txtnameUser;
	private JLabel labelCon;
	private JButton btnConect;
	private JPanel panel;
	private JPanel panelP;
	private JTextField txtPassword;
	private ActionListener action;

	public PanelLoad(ActionListener action) {
		this.action=action;
		init();
	}

	public void init() {
		this.setBackground(COLOR_BACK);
		setSettingsPanel();
		setSettingsLabelsAndText();
	}

	public void setSettingsPanel() {
		this.setLayout(new BorderLayout());
		this.panel = new JPanel();
		panel.setBackground(COLOR_BACK);
		this.panelP = new JPanel();
		panelP.setBackground(COLOR_BACK);
		this.panelP.setLayout(new BorderLayout());
		this.panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		this.add(panel,BorderLayout.NORTH);
	}

	public String getNameUserLoad() {
		return this.txtnameUser.getText();
	}
	public String getPassword() {
		return this.txtPassword.getText();
	}

	public void setSettingsLabelsAndText() {
		this.labelCon = new JLabel();
		this.txtnameUser = new JTextField();
		txtnameUser.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		this.txtPassword = new JTextField();
		txtnameUser.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		setSettingsButtonsComp();
		addComponents();
	}
	public void setSettingsButtonsComp() {
		this.btnConect = new JButton(TXT_BTN_CONECT);
		this.btnConect.setActionCommand(COMAND_CONECT);
		btnConect.addActionListener(action);
		btnConect.setBackground(COLOR_MY_PINK);
	}

	public void addComponents() {
		this.panel.add(new JLabel(REQUEST_NAME_USER));
		this.panel.add(txtnameUser);
		this.panel.add(new JLabel(REQUEST_PASSWORD_USER));
		this.panel.add(txtPassword);
		this.panel.add(btnConect);
		this.panelP.add(labelCon, BorderLayout.NORTH);
		this.add(panelP, BorderLayout.SOUTH);
	}
	public void clearForm() {
		this.txtnameUser.setText("");
		this.txtPassword.setText("");
	}
	public void upDateListConexions(ArrayList<String> listConections) {
		for (int i = 0; i < listConections.size(); i++) {
			System.out.println(listConections.get(i));
		}
	}
	public void showMenssageConetion() {
		this.labelCon.setBackground(Color.RED);
		this.labelCon.setText(ERROR_CONECCTION_DATAS);
	}

}
