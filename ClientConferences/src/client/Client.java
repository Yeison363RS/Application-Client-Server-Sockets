package client;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

import views.IObsever;

public class Client {
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private Socket clientSocket;
	private Socket clientConection;
	private IObsever observer;
	private Thread threadRead;
	private boolean run;

	public Client(IObsever observer) {
		this.observer = observer;
		run = true;
	}

	public void getConecction(String datasClient) {
		try {
			this.clientConection = new Socket("localhost", 5001);
			DataOutputStream dataO = new DataOutputStream(clientConection.getOutputStream());
			dataO.writeUTF(datasClient);
			DataInputStream dataI = new DataInputStream(clientConection.getInputStream());
			observer.readResponse(dataI.readUTF());
			readAllConection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getService() {
		try {
			this.clientSocket = new Socket("localhost", 5000);
			this.dataOut = new DataOutputStream(clientSocket.getOutputStream());
			this.dataIn = new DataInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDatasClient(String datasClient) throws IOException {
		DataOutputStream dataOup = new DataOutputStream(clientConection.getOutputStream());
		dataOup.writeUTF(datasClient);
	}

	public void readAllConection() {
		this.threadRead = new Thread(new Runnable() {
			@Override
			public void run() {
				DataInputStream dataInC;
				try {
					dataInC = new DataInputStream(clientConection.getInputStream());
					while (run) {
						if (dataInC.available() > 0) {
							readNotification(dataInC);
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e1) {
				}
			}
		});
		this.threadRead.start();
	}

	public void sendMediaClient(File file) throws IOException {
		if (file.exists()) {
			byte[] buffer = new byte[4096];
			OutputStream stream = clientSocket.getOutputStream();
			FileInputStream input = new FileInputStream(file);
			int read = 0;
			while ((read = input.read(buffer)) != -1) {
				stream.write(buffer, 0, read);
			}
			input.close();
		}
	}
	public void getReport(String requestReport) throws IOException {
		getService();
		dataOut.writeUTF(requestReport);
		byte[] buffer = new byte[4096];
		try {
			Thread.currentThread();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		InputStream stream = clientSocket.getInputStream();
		FileOutputStream outPut = new FileOutputStream(new File("report.pdf"));
		int read = 0;
		while (stream.available() != 0 && (read = stream.read(buffer)) != -1) {
			outPut.write(buffer, 0, read);
		}
		this.clientSocket.close();
	}
	public ImageIcon getFile(String requestFile, String extencion) {
		getService();
		if (extencion.equalsIgnoreCase("jpg") || extencion.equalsIgnoreCase("png")) {
			try {
				return getImageRequest(requestFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public ImageIcon getImageRequest(String requestFile) throws IOException {
		dataOut.writeUTF(requestFile);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		try {
			Thread.currentThread();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clientSocket.getInputStream();
		int read = 0;
		while (dataIn.available() != 0 && (read = dataIn.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		out.close();
		return new ImageIcon(out.toByteArray());
	}

	public void readNotification(DataInputStream dataInp) {
		try {
			observer.readNotification(dataInp.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readResponseService() {
		try {
			observer.readResponse(dataIn.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendRequestServiceSave(String xmlComplianceRequest, File file) throws IOException {
		getService();
		dataOut.writeUTF(xmlComplianceRequest);
		if (file != null && file.exists()) {
			sendMediaClient(file);
		}
		readResponseService();
		this.clientSocket.close();
	}

}
