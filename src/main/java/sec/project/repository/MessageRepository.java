package sec.project.repository;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepository {
    private Connection connection;

    public MessageRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
            String query = "CREATE TABLE Message (msg varchar(255))";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void save(String message){
        try {
            String query = "INSERT INTO Message (msg) VALUES (?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, message);
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<String> findAll(){
        try{
            List<String> messages = new ArrayList<>();
            String query = "SELECT * FROM Message";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                messages.add(rs.getString("msg"));
            }
            return messages;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
