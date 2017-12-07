package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import to.User;

public class RegistrationDAO extends DAO{
    public void createUser(User user) {
        try {
            System.out.println(123);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users(username, password) VALUES(?,?)");
            String username = user.getUsername();
            statement.setString(1, username);
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            statement = connection.prepareStatement("INSERT INTO roles(username, role_name) VALUES(?, 'students')");
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException ex) {
            writeError(ex);
        }finally{
            try {
                if(connection != null) connection.close();
            }catch(SQLException ex) {
                writeError(ex);
            }
        }
    }
}
