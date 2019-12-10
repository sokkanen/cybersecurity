package sec.project.repository;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class AdminPwdRepository {

    private Connection connection;

    public AdminPwdRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
            String query = "CREATE TABLE Password (id integer, pwd varchar(255))";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.executeUpdate();
            query = "INSERT INTO Password (id, pwd) VALUES (1, 'password')";
            stmt = connection.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void change(String pwd){
        try {
            String query = "UPDATE Password SET pwd = ? WHERE id = 1;";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, pwd);
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public String getAdminPassword(){
        try{
            String pass = "";
            String query = "SELECT pwd FROM Password WHERE id = 1";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                pass = rs.getString("pwd");
            }
            return pass;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
