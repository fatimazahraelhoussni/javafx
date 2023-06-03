package application;

import java.sql.*;

 
public class StudentDAO {
    
    //pour add
    @SuppressWarnings("unused")
	public void ajouterEtudiant(String id, String Name,String Email ,String Father_Name, String CINC, String Adress,String Contact_NO) throws SQLException   {
    	     	
    	  String sql = "INSERT INTO ensao (ID, Name, Email,Father_Name,CINC,Adress,Contact_NO ) VALUES (?, ?, ?, ?,?,?,?)";

         try (Connection connection = DatabaseConnector.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql)) {
        	 statement.setString(1, id);
             statement.setString(2, Name);
             statement.setString(3, Email);
             statement.setString(4, Father_Name);
             statement.setString(5, CINC);
             statement.setString(6, Adress);
             statement.setString(4, Contact_NO);
             statement.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }
               
    }
    
    //pour update
    public void mettreAJourEtudiant(String id, String Name,String Email ,String Father_Name, String CINC, String Adress,String Contact_NO) {
    	 String sql = "UPDATE ensao SET Name = ?, Email = ?, Father_Name = ?, CINC = ?, Adress = ?, Contact_NO = ? WHERE ID = ?";

         try (Connection connection = DatabaseConnector.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql)) {
             statement.setString(1, Name);
             statement.setString(2, Email);
             statement.setString(3, Father_Name);
             statement.setString(4, CINC);
             statement.setString(5, Adress);
             statement.setString(6, Contact_NO);
             statement.setString(7, id);
             statement.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 

//pour delete

    public void supprimerEtudiant(String id) throws SQLException {
        String sql = "DELETE FROM ensao WHERE ID = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }     
}
