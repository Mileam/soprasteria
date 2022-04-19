package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import dao.DAOCompte;
import dao.DAOPatient;
import dao.DAOVisite;
import model.Compte;
import model.Medecin;
import model.Patient;
import model.Secretaire;
import model.Visite;

public class Test {

	static LinkedList<Patient> fileAttente = new LinkedList();
	static Compte connected=null;

	static DAOVisite daoVisite = new DAOVisite();
	static DAOCompte daoCompte = new DAOCompte();
	static DAOPatient daoPatient = new DAOPatient();

	public static String saisieString(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextLine();
	}

	public static int saisieInt(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextInt();
	}

	public static void ecrireObjet() 
	{

		File monFichier = new File("fileAttente.txt");

		try (FileOutputStream fos = new FileOutputStream(monFichier);
				ObjectOutputStream oos = new ObjectOutputStream(fos);)
		{
			oos.writeObject(fileAttente);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void lireObjet()
	{

		File monFichier = new File("fileAttente.txt");
		if (monFichier.exists())
		{
			try (FileInputStream fis = new FileInputStream(monFichier);
					ObjectInputStream ois = new ObjectInputStream(fis);)
			{
				fileAttente = (LinkedList<Patient>) ois.readObject();

			}
			catch (Exception e) {e.printStackTrace();}
		}
	}

	public static void menuPrincipal()
	{
		System.out.println("\n----------Menu Principal----------");
		System.out.println("1- Se connecter");
		System.out.println("2- Quitter");
		int choix = saisieInt("Choix du menu :");

		switch(choix) 
		{
		case 1 : menuSeConnecter();break;
		case 2 : System.out.println("A bientot !");break;
		}
		System.exit(0);
	}
	public static void menuSeConnecter() 
	{
		String login= saisieString("Saisir votre login");
		String password=saisieString("Saisir votre password");

		connected = daoCompte.seConnecter(login,password);
		lireObjet();
		if(connected instanceof Secretaire) 
		{
			menuSecretaire();
		}
		else if(connected instanceof Medecin) 
		{
			int choixSalle = saisieInt("Saisir la salle de consultations (1 ou 2)");
			((Medecin) connected).setSalle(choixSalle);
			menuMedecin();

		}
		else 
		{
			System.out.println("Identifiants invalides");
		}
		menuPrincipal();

	}


	public static void menuSecretaire() 
	{
		System.out.println("\n*******Menu Secrétaire*******");
	//	System.out.println("1- Creer un patient");
		System.out.println("1- Ajouter un patient à la file d'attente");
		System.out.println("2- Afficher la file d'attente");
		System.out.println("3- Partir en pause");
		System.out.println("4- Rechercher toutes les visites d'un patient");

		int choix = saisieInt("Choix du menu :");
		switch(choix) 
		{
		//case 1 : creerPatient();break;
		//case 2 : ajouterPatientFileAttente();break;
		case 1 : ajouterPatient();break;
		case 2 : afficherFileAttente();break;
		case 3 : partirPause();break;
		case 4 : rechercheVisiteParPatient();break;

		}
		menuSecretaire();
	}


	private static void ajouterPatient() {
		int id = saisieInt("Saisir l'id du Patient");	
		Patient p = daoPatient.findById(id);
		if(p==null) 
		{
			System.out.println("Le patient n'a jamais été enregistré");

			String nom = saisieString("Saisir le nom du patient");
			String prenom = saisieString("Saisir le prenom du patient");

			p = new Patient(id,nom, prenom);

			// ajoute à la BDD
			daoPatient.insert(p);
		}
		
		fileAttente.add(p);
		
	}

	public static void creerPatient() 
	{
		System.out.println("Le patient n'a jamais été enregistré");

		String nom = saisieString("Saisir le nom du patient");
		String prenom = saisieString("Saisir le prenom du patient");

		Patient p = new Patient(nom, prenom);

		// ajoute à la BDD
		daoPatient.insert(p);
	}

	public static void ajouterPatientFileAttente() 
	{	
		for(Patient p : daoPatient.findAll())
		{
			System.out.println(p);
		}
		int id = saisieInt("Saisir l'id du Patient");	
		Patient p = daoPatient.findById(id);

		if(p == null) 
		{
			System.out.println("id non reconnu");
			menuSecretaire();
		}
		else
		{
			fileAttente.add(p);
		}

	}

	public static void afficherFileAttente() 
	{
		if(fileAttente.isEmpty())
		{
			System.out.println("Aucun patient à la file d'attente");
		}
		for(Patient p  : fileAttente) 
		{
			System.out.println(p);
		}

	}


	public static void partirPause() 
	{
		// se déconnecter -> appeler la menu seConnecter
		ecrireObjet();
		System.out.println("Pause, nous revenons bientot");
		menuPrincipal();

	}	
	
	public static void rechercheVisiteParPatient()
	{
		
		int id = saisieInt("Saisir l'id du Patient");	
		List<Visite> visites=daoVisite.findVisitesByIdPatient(id);
		if(visites.isEmpty()) {System.out.println("Vous n'avez pas encrore de visiste, dromage");}
		
		for(Visite v : visites)
		{
			System.out.println(v);
		}
	}

	public static void menuMedecin() 
	{
		System.out.println("\n~~~~~~~Menu Médecin~~~~~~~");
		System.out.println("1- Afficher la liste d'attente");
		System.out.println("2- Recevoir un Patient");
		System.out.println("3- Sauvegarder Visite");
		System.out.println("4- Retour au menu Principal");

		int choix = saisieInt("Choix du menu");
		switch(choix) 
		{
		case 1 : afficherFileAttente();break;
		case 2 : recevoirPatient();break;
		case 3 : sauveVisite();break;
		case 4 : menuPrincipal(); break;
		}

		menuMedecin();

	}

	public static void recevoirPatient() 
	{	
		if(fileAttente.isEmpty()) 
		{
			System.out.println("La liste est vide, aucun patient à recevoir");
			menuMedecin();
		}	
		System.out.println("Reception du patient : "+ fileAttente.peekFirst());
		Patient p = fileAttente.pollFirst();
		ecrireObjet();

		// si la liste de visite size = 10 
		// -> envoie dans la base (DAO) et on reset la liste
		((Medecin) connected).addToListVisite(p);

	}

	public static void sauveVisite() 
	{	
		for(Visite visit : ((Medecin) connected).getLotVisite())
		{
			daoVisite.insert(visit);
		}
		((Medecin) connected).getLotVisite().clear();
	}


	public static void main(String[] args) {

		// Menu se connecter (qui détermine si médecin / secrétaire)


		// une fois connecter:
		// si medecin emmène vers menu médecin
		// si secrétaire emmène vers menu secrétaire



		// Menu médecin
		// recevoirPatient
		// voir fileAttente
		// sauveVisite()
		// si la liste de visite size = 10 
		// -> envoie dans la base (DAO) et on reset la liste


		// Menu secrétaire
		// creerPatient
		// ajouter à la file d'attente le patient
		// afficher la file d'attente
		// Partie en pause (se déconnecter)



		menuPrincipal();		

	}

}
