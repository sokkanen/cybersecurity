package sec.project.repository;

import org.springframework.stereotype.Repository;
import sec.project.domain.Signup;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class SignupRepository {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private Connection connection;

    public SignupRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
            String query = "CREATE TABLE SIGNUP (name varchar(255), address varchar(255))";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.executeUpdate();
            logger.info("SQL operation OK");
        } catch (SQLException e){
            logger.info("SQL operation failed");
            System.out.println(e.getMessage());
        }
    }

    public void save(Signup signup){
        try {
            String query = "INSERT INTO Signup (name, address) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, signup.getName());
            stmt.setString(2, signup.getAddress());
            stmt.executeUpdate();
            logger.info("SQL operation OK");
        } catch (SQLException e){
            logger.info("SQL operation failed");
            System.out.println(e.getMessage());
        }
    }

    public List<Signup> findSignup(String name){
        try {
            List<Signup> signups = new ArrayList<>();
            String query = "SELECT * FROM Signup s WHERE s.name = '" + name + "';";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                signups.add(new Signup(rs.getString("name"), rs.getString("address")));
            }
            logger.info("SQL operation OK");
            return signups;
        } catch (SQLException e){
            logger.info("SQL operation failed");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Signup> findAll(){
        try{
            List<Signup> signups = new ArrayList<>();
            String query = "SELECT * FROM Signup";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                signups.add(new Signup(rs.getString("name"), rs.getString("address")));
            }
            logger.info("SQL operation OK");
            return signups;
        } catch (SQLException e){
            logger.info("SQL operation failed");
            System.out.println(e.getMessage());
        }
        return null;
    }

}
