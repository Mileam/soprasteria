package model;

public class Secretaire extends Compte{

	private String login;
	private String password;

	public Secretaire(Integer id, String login, String password) {
		super(id);
		this.login = login;
		this.password = password;
	}

	public Secretaire(Integer id) {
		super(id);
	}



	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Secretaire [id=" + id + "]";
	}
		
}
