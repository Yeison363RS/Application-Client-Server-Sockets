package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class MyButton extends JButton{

	private static final long serialVersionUID = 1L;
	
	public MyButton(String name,String comand,ActionListener action,Color color,Font font) {
		init(name,comand,action,color,font);
	}
	public void init(String name,String comand,ActionListener action,Color color,Font font) {
		this.setName(name);
		this.setText(name);
		this.setActionCommand(comand);
		this.addActionListener(action);
		this.setBackground(color);
		this.setFont(font);
		this.setBorder(null);
		this.setFocusPainted(false);
	}
}
