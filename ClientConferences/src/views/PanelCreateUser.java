package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelCreateUser extends JPanel {
	public static final String CREATE_CREATE = "createPLog";
	private static final String CUENTA = "ya tengo cuenta";
	/**
	 * 
	 */
	private static final String MESSAGGE_NOT_SAVED = "Error Usuario registrado";
	private static final String MESSAGGE_SAVED_SUCCESSFUL = "Se ha guardado el usuario";
	private static final long serialVersionUID = 1L;
	private static final String TXT_BTN_SAVE = "Guardar";
	private static final String REQUEST_NAME_USER = "Escribe nombre de usuario";
	private static final String REQUEST_PASSWORD_USER = "Escribe una contraseña";
	private JTextField txtnameUser;
	private JTextField txtPassword;
	private JLabel labelCon;
	private MyButton btnSend;
	private MyButton btnLoad;
	private JPanel panel;
	private JPanel panelP;
	private ActionListener action;

	public PanelCreateUser(ActionListener action) {
		this.action=action;
		init();
	}

	public void init() {
		setSettingsPanel();
		setSettingsButtons();
		setSettiingsButton();
		addComponents();
	}

	public void setSettingsPanel() {
		this.setLayout(new BorderLayout());
		this.panel = new JPanel();
		panel.setBackground(PanelLoad.COLOR_BACK);
		this.panelP = new JPanel();
		panelP.setBackground(PanelLoad.COLOR_BACK);
		this.panelP.setLayout(new BorderLayout());
		this.panel.setLayout(new GridLayout(6, 1));
		this.add(panel, BorderLayout.NORTH);
	}

	public String getNameUser() {
		return this.txtnameUser.getText();
	}

	public void setSettingsButtons() {
		this.labelCon = new JLabel();
		this.txtnameUser = new JTextField();
		txtnameUser.setBorder(BorderFactory.createLineBorder(PanelLoad.COLOR_BACK));
		txtPassword=new JTextField();
		txtPassword.setBorder(BorderFactory.createLineBorder(PanelLoad.COLOR_BACK));
		this.btnSend =new MyButton(TXT_BTN_SAVE, FrameUser.COMMAND_SAVE, action,
				PanelLoad.COLOR_MY_GREEN,FrameUser.MY_FONT_BTN);
	}
	public void setSettiingsButton(){
		this.btnLoad = new MyButton(CUENTA, CREATE_CREATE,
				action, panelP.getBackground(), FrameUser.MY_FONT_BTN);
		this.btnLoad.setToolTipText(CUENTA);
		this.btnLoad.setBorder(null);
		this.btnLoad.setOpaque(true);
		this.btnLoad.setForeground(Color.BLACK);
		btnLoad.addActionListener(action);
	}
	public void addComponents() {
		this.panel.add(new JLabel(REQUEST_NAME_USER));
		this.panel.add(txtnameUser);
		this.panel.add(new JLabel(REQUEST_PASSWORD_USER));
		this.panel.add(txtPassword);
		this.panel.add(btnSend);
		this.panel.add(btnLoad);
		this.panelP.add(labelCon, BorderLayout.NORTH);
		this.add(panelP, BorderLayout.CENTER);
	}
	public void clearForm() {
		this.txtnameUser.setText("");
		this.txtPassword.setText("");
	}
	public String getPassword() {
		return this.txtPassword.getText();
	}
	public void showMessageSave(boolean confirm) {
		if (confirm) {
			this.labelCon.setText(MESSAGGE_SAVED_SUCCESSFUL);
			this.panelP.setBackground(Color.GREEN);
		} else {
			this.labelCon.setText(MESSAGGE_NOT_SAVED);
			this.panelP.setBackground(Color.RED);
		}
		this.panelP.repaint();
	}
}
