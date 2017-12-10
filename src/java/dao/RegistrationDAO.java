package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import to.User;

public class RegistrationDAO extends DAO{
    public void createUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users(username, password) VALUES(?,?)");
            String username = user.getUsername();
            statement.setString(1, username);
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            statement = connection.prepareStatement("INSERT INTO roles(username, role_name) VALUES(?, 'customer')");
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException ex) {
            writeError(ex);
        }finally{
            close(); 
        }
    }
    
    public boolean exists(String username) {
        boolean exist = false; 
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT username FROM users WHERE username=?");
            statement.setString(1, username);
            exist = statement.executeQuery().next();
        } catch (SQLException ex) {
            writeError(ex);
        }finally{
            close(); 
        }
        return exist; 
    }
}
