package model;

import java.util.ArrayList;
import java.util.List;

import dao.DAOVisite;
import test.Test;

public class Medecin extends Compte{

	private String login;
	private String password;
	private int salle;
	private List<Visite> lotVisite = new ArrayList();

	public Medecin(Integer id, String login, String password, int salle,
			List<Visite> lotVisite) {
		super(id);
		this.login = login;
		this.password = password;
		this.salle = salle;
		this.lotVisite = lotVisite;
	}


	public Medecin(Integer id, String login, String password) {
		super(id);
		this.login = login;
		this.password = password;
	}

	public Medecin(Integer id) {
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

	public int getSalle() {
		return salle;
	}

	public void setSalle(int salle) {
		this.salle = salle;
	}

	public List<Visite> getLotVisite() {
		return lotVisite;
	}

	public void setLotVisite(List<Visite> lotVisite) {
		this.lotVisite = lotVisite;
	}

	public void addToListVisite(Patient p)
	{
		Visite v = new Visite(p,this);
		lotVisite.add(v);
		if(lotVisite.size() >=2)
		{
			Test.sauveVisite();
		}
	}

	@Override
	public String toString() {
		return "Medecin [id=" + id + ", salle=" + salle + ", lotVisite=" + lotVisite + "]";
	}
}
