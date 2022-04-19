package model;

import java.time.LocalDate;

public class Visite {

	private Integer numeroVisite;
	private Patient patient;
	private Medecin medecin;
	private int prix;
	private int salle;
	private LocalDate dateVisite;
	
	public Visite(Integer numeroVisite, Patient patient, Medecin medecin, int prix,int salle, LocalDate dateVisite) {
		this.numeroVisite = numeroVisite;
		this.medecin=medecin;
		this.patient=patient;
		this.salle = salle;
		this.prix = prix;
		this.dateVisite = dateVisite; 
	}


	public Visite(Patient patient, Medecin medecin) {
	
		this.patient = patient;
		this.medecin = medecin;
		this.salle = medecin.getSalle();
		this.prix = 20;
		this.dateVisite = LocalDate.now();
	}



	public int getPrix() {
		return prix;
	}


	public void setPrix(int prix) {
		this.prix = prix;
	}


	public Integer getNumeroVisite() {
		return numeroVisite;
	}

	public void setNumeroVisite(Integer numeroVisite) {
		this.numeroVisite = numeroVisite;
	}

	
	public int getSalle() {
		return salle;
	}

	public void setSalle(int salle) {
		this.salle = salle;
	}


	public Patient getPatient() {
		return patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}


	public Medecin getMedecin() {
		return medecin;
	}


	public void setMedecin(Medecin medecin) {
		this.medecin = medecin;
	}


	public LocalDate getDateVisite() {
		return dateVisite;
	}


	public void setDateVisite(LocalDate dateVisite) {
		this.dateVisite = dateVisite;
	}


	@Override
	public String toString() {
		return "Visite [numeroVisite=" + numeroVisite + ", patient=" + patient + ", medecin=" + medecin + ", prix="
				+ prix + ", salle=" + salle + ", dateVisite=" + dateVisite + "]";
	}


	
}
