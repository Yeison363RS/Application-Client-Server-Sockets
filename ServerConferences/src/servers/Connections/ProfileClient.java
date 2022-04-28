package servers.Connections;


public class ProfileClient {
	private String name;
	private String password;
	private String idClient;
	public ProfileClient(String name,String pass,String idClient){
		this.name=name;
		this.idClient=idClient;
	}
	public ProfileClient(String name){
		this.name=name;
		this.password="admin";
		this.idClient="admin";
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdClient() {
		return idClient;
	}
}
