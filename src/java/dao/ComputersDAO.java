package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import to.Computer;

public class ComputersDAO extends DAO{
    
    public List<Computer> getAllComputers() {
        List<Computer> computers = null;
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM computers");
            computers = new ArrayList();
            while(rs.next()) {
                computers.add(new Computer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4),
                rs.getString(5), rs.getDouble(6), false, 0));
            }
        }catch(SQLException ex) {
            writeError(ex);
        }finally{
            close(); 
        }
        return computers;
    }
    
    public void addNewComputer(Computer computer) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO computers VALUES(?, ?, ?, ?, ?, ?)");
            statement.setLong(1, computer.getId());
            statement.setString(2, computer.getModel());
            statement.setString(3, computer.getProducer());
            statement.setInt(4, computer.getRam());
            statement.setString(5, computer.getColor());
            statement.setDouble(6, computer.getPrice());
            statement.executeUpdate();
        }catch(SQLException ex) {
            writeError(ex);
        }finally{
            close(); 
        }
    }
    
    public void remove(Computer computer) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM computers WHERE id=?");
            statement.setLong(1, computer.getId());
            statement.executeUpdate();
        }catch(SQLException ex) {
            writeError(ex);
        }finally{
            close(); 
        }
    }
}
