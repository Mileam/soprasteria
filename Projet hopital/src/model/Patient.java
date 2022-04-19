package model;

import java.io.Serializable;

public class Patient implements Serializable{

	private int cpt;
	private Integer id;
	private String nom;
	private String prenom;
	
	public Patient(String nom, String prenom) {
		cpt++;
		this.id = cpt;
		this.nom = nom;
		this.prenom = prenom;
	}

	public Patient(Integer id, String nom, String prenom) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", nom=" + nom + ", prenom=" + prenom + "]";
	}
	
	
}
