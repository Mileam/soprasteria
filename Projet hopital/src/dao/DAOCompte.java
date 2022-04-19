package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Compte;
import model.Medecin;
import model.Secretaire;



public class DAOCompte implements IDAO<Compte,Integer> {

	@Override
	public Compte findById(Integer id) {
		Compte c = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);
			PreparedStatement ps = conn.prepareStatement("SELECT * from Compte where id=?");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) 
			{
				if(rs.getString("type_compte").equals("Secretaire")) 
				{
					c = new Secretaire(rs.getInt("id"),rs.getString("login"),rs.getString("password"));
				}
				else 
				{
					c = new Medecin(rs.getInt("id"), rs.getString("login"), rs.getString("password"));
				}
			}
			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public List<Compte> findAll() {
		List<Compte> comptes = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);
			PreparedStatement ps = conn.prepareStatement("SELECT * from compte");

			ResultSet rs = ps.executeQuery();

			while(rs.next()) 
			{
				if(rs.getString("type_compte").equals("Secretaire")) 
				{
					Compte c = new Secretaire(rs.getInt("id"),rs.getString("login"),rs.getString("password"));
				}
				else 
				{
					Compte c = new Medecin(rs.getInt("id"), rs.getString("login"), rs.getString("password"));
				}
			}
			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return comptes;
	}

	@Override
	public void insert(Compte c) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);	
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Compte (login, password) VALUES (?, ?)");

			ps.setString(1,c.getLogin());
			ps.setString(2,c.getPassword());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) 
			{
				c.setId(rs.getInt(1));
			}

			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}		

	}

	@Override
	public void update(Compte c) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);	
			PreparedStatement ps = conn.prepareStatement("UPDATE compte set login=?,password=?");

			ps.setString(1,c.getLogin());
			ps.setString(2,c.getPassword());

			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Integer id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM compte where id=?");
			ps.setInt(1,id);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Medecin> findAllMedecin() {
		List<Medecin> medecins = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);
			PreparedStatement ps = conn.prepareStatement("SELECT * from Compte where type_Compte = 'Medecin'");

			ResultSet rs = ps.executeQuery();

			while(rs.next()) 
			{
				{
					Medecin m = new Medecin(rs.getInt("id"));
					medecins.add(m);
				}
			}
			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return medecins;
	}


	public Compte seConnecter(String login,String password) 
	{
		Compte c = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);
			PreparedStatement ps = conn.prepareStatement("SELECT * from Compte where login=? and password=?");
			ps.setString(1,login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) 
			{


				if(rs.getString("type_compte").equals("Secretaire")) 
				{
					c = new Secretaire(rs.getInt("id"));
				}
				else 
				{
					c = new Medecin(rs.getInt("id"));
				}
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return c;
	}
}