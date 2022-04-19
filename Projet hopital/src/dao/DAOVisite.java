package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Medecin;
import model.Patient;
import model.Visite;

public class DAOVisite implements IDAO<Visite,Integer> {

	@Override
	public Visite findById(Integer id) {
		Visite v = null;
		DAOPatient daoP=new DAOPatient();
		DAOCompte daoC = new DAOCompte();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);
			PreparedStatement ps = conn.prepareStatement("SELECT * from visite where id=?");
			ps.setInt(1, id);


			ResultSet rs = ps.executeQuery();

			while(rs.next()) 
			{
				Medecin m = (Medecin) daoC.findById(rs.getInt("id_medecin"));
				Patient p = daoP.findById(rs.getInt("id_patient"));
				
				v = new Visite(rs.getInt("numero"),p,m,rs.getInt("salle"), rs.getInt("prix"),LocalDate.parse(rs.getString("date_visite")));
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	public List<Visite> findVisitesByIdPatient(Integer id) {
		List<Visite> visites = new ArrayList();
		DAOPatient daoP=new DAOPatient();
		DAOCompte daoC = new DAOCompte();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);
			PreparedStatement ps = conn.prepareStatement("SELECT * from visite where id_patient=?");
			ps.setInt(1, id);


			ResultSet rs = ps.executeQuery();

			while(rs.next()) 
			{
				Medecin m = (Medecin) daoC.findById(rs.getInt("id_medecin"));
				Patient p = daoP.findById(rs.getInt("id_patient"));
				
				Visite v = new Visite(rs.getInt("numero"),p,m,rs.getInt("salle"), rs.getInt("prix"),LocalDate.parse(rs.getString("date_visite")));
				visites.add(v);
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return visites;
	}
	@Override
	public List<Visite> findAll() {
		List<Visite> visites = new ArrayList();
		DAOPatient daoP=new DAOPatient();
		DAOCompte daoC = new DAOCompte();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);
			PreparedStatement ps = conn.prepareStatement("SELECT * from visite");

			ResultSet rs = ps.executeQuery();

			while(rs.next()) 
			{

				Medecin m = (Medecin) daoC.findById(rs.getInt("id_medecin"));
				Patient p = daoP.findById(rs.getInt("id_patient"));
				
				Visite v = new Visite(rs.getInt("numero"),p,m,rs.getInt("salle"), rs.getInt("prix"),LocalDate.parse(rs.getString("date_visite")));
				visites.add(v);
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return visites;
	}

	@Override
	public void insert(Visite v) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);
			PreparedStatement ps = conn.prepareStatement("INSERT into visite (id_patient,id_medecin, prix, salle, date_visite) VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,v.getPatient().getId());
			ps.setInt(2,v.getMedecin().getId());
			ps.setInt(3,v.getPrix());
			ps.setInt(4,v.getSalle());
			ps.setString(5,v.getDateVisite().toString());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) 
			{
				v.setNumeroVisite(rs.getInt(1));
			}


			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Visite v) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);	
			PreparedStatement ps = conn.prepareStatement("UPDATE visite set id_patient=?,id_medecin=?,prix=?, salle=?, date_visite=? where numero=?");
			ps.setInt(1,v.getPatient().getId());
			ps.setInt(2,v.getMedecin().getId());
			ps.setInt(3,v.getPrix());
			ps.setInt(4,v.getSalle());
			ps.setString(5,v.getDateVisite().toString());
			ps.setInt(6, v.getNumeroVisite());
			
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Integer numeroVisite) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM visite where numero=?");
			ps.setInt(1,numeroVisite);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}





}
