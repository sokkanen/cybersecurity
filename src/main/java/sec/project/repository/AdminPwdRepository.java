package sec.project.repository;

import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.logging.Logger;

@Repository
public class AdminPwdRepository {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

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
            logger.info("SQL operation OK");
        } catch (SQLException e){
            logger.info("SQL operation failed");
            System.out.println(e.getMessage());
        }
    }

    public void change(String pwd){
        try {
            String query = "UPDATE Password SET pwd = ? WHERE id = 1;";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, pwd);
            stmt.executeUpdate();
            logger.info("SQL operation OK");
        } catch (SQLException e){
            logger.info("SQL operation failed");
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
            logger.info("SQL operation OK");
            return pass;
        } catch (SQLException e){
            logger.info("SQL operation failed");
            logger.warning("");
            logger.severe("");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
