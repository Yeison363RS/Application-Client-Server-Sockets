package servers.Connections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ProfileConnection {
	private Socket clientSocket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdClient() {
		return idClient;
	}
	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}
	private String name;
	private String idClient;
	public ProfileConnection(String name,String idClient,Socket socketClient) throws IOException {
		this.clientSocket=socketClient;
		this.name=name;
		this.idClient=idClient;
		createChanels();
	}
	public void createChanels() throws IOException {
		this.dataIn=new DataInputStream(clientSocket.getInputStream());
		this.dataOut=new DataOutputStream(clientSocket.getOutputStream());
	}
	public boolean checkTheConnection() {
		return this.clientSocket.isClosed();
	}
	public String readRequest() throws IOException {
		return dataIn.readUTF();
	}
	public void sendNotification(String notification) {
		try {
			dataOut.writeUTF(notification);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean isAvaliableChanel() throws IOException {
		return (this.dataIn.available()>0)?true:false;
	}
	
	
}
