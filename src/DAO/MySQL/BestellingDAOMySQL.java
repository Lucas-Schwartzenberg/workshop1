
package DAO.MySQL;

import ConnectionPools.*;
import POJO.*;
import interfaceDAO.BestellingDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BestellingDAOMySQL implements BestellingDAO{
    public Bestelling createBestelling(Bestelling bestelling) {

        String query = "INSERT INTO Bestelling (klant_id) values (?)";
      

        try(Connection con = ConnectionPool.getConnection()){
        
            PreparedStatement stmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            //Set values for INSERT-part of the statement
                       
            stmt.setInt(1, bestelling.getKlantID());

           
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.isBeforeFirst()){
                resultSet.next();
                bestelling.setBestellingID(resultSet.getInt(1)); //wijs door db gegenereerde id toe aan klant
            }
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println("createBestelling error");
            e.printStackTrace();
        }
        return bestelling;
    }
    
    public Bestelling getBestellingById(int BestellingId){
        
        Bestelling bestelling = new Bestelling();
        try(Connection con = new DBConnector().getConnection();){
            PreparedStatement stmt = con.prepareStatement("select * from Bestelling where bestelling_id = ?");
            stmt.setInt(1, BestellingId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                bestelling.setKlantID(rs.getInt("klant_id"));
                bestelling.setBestellingID(rs.getInt("bestelling_id"));
            }
        }
        catch(SQLException | ClassNotFoundException  e){
            e.printStackTrace();
        }
        return bestelling;
    }

    public ArrayList<Bestelling> getAllBestelling(){
        ArrayList<Bestelling> bestellingLijst = new ArrayList<Bestelling>();
        try(Connection con = new DBConnector().getConnection();){
            PreparedStatement stmt = con.prepareStatement("select * from Bestelling");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                //Bestelling bestelling = new Bestelling.BestellingBuilder().bestellingID(rs.getInt("bestelling_id")).klantID(rs.getInt("klant_id")).build();
                Bestelling bestelling = new Bestelling();
                bestelling.setBestellingID(rs.getInt("bestelling_id"));
                bestelling.setKlantID(rs.getInt("klant_id"));
                bestellingLijst.add(bestelling);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return bestellingLijst;
    }
    public ArrayList<Bestelling> getBestellingByKlantId(int klantId){
        ArrayList<Bestelling> bestellingLijst = new ArrayList<Bestelling>();
        try(Connection con = new DBConnector().getConnection();){
            PreparedStatement stmt = con.prepareStatement("select * from bestelling where klant_id = ?");
            stmt.setInt(1, klantId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){    
                Bestelling bestelling = new Bestelling.BestellingBuilder().bestellingID(rs.getInt("bestelling_id")).klantID(rs.getInt("klant_id")).build();
                bestellingLijst.add(bestelling);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return bestellingLijst;
    }

    public void updateBestelling(Bestelling bestelling){
        String query =  "UPDATE Bestelling SET klant_id=? WHERE bestelling_id = ?;";

        try(Connection con = new DBConnector().getConnection();){
   
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, bestelling.getKlantID());
            stmt.setInt(2, bestelling.getBestellingID());
            
            stmt.executeUpdate();
        }
        catch(SQLException | ClassNotFoundException  e){
            e.printStackTrace();
        }
    }
    
    public void deleteBestelling(int bestelling_id){
        String sql = "DELETE FROM bestelling WHERE bestelling_id=?";
        try(Connection con = new DBConnector().getConnection();){
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, bestelling_id);
            stmt.executeUpdate();
        }
        catch(SQLException | ClassNotFoundException  e){
            e.printStackTrace();
        }
    }
}
