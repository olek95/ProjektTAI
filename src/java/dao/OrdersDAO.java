package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import to.Computer;
public class OrdersDAO extends DAO{
    public void saveOrder(List<Computer> order, String username, boolean shouldBeClosed) {
        try {
            for(int i = 0; i < order.size(); i++) {
                Computer orderedComputer = order.get(i);
                if(isExist(orderedComputer, username)) 
                    updateOrder(orderedComputer, username);
                else insertOrder(orderedComputer, username);
            }
        } catch (SQLException ex) {
            writeError(ex);
        }finally{
            if(shouldBeClosed) close(); 
        }
    }
    
    public List<Computer> getOrder(String username) {
        List<Computer> order = null;
        try {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT id, model, producer, ram, color, price, quanity from computers, orders WHERE id = product_id AND username=?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            order = new ArrayList(); 
            while(rs.next()) {
                order.add(new Computer(rs.getLong(1), rs.getString(2), 
                        rs.getString(3), rs.getInt(4), rs.getString(5), 
                        rs.getDouble(6), false, rs.getInt(7)));
            }
        }catch(SQLException ex) {
            writeError(ex);
        } finally {
            close(); 
            return order; 
        }
    }
    
    public void delete(Computer computer, String username, boolean shouldBeClosed) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE username=? AND product_id=?");
            statement.setString(1, username);
            statement.setLong(2, computer.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            writeError(ex);
        }finally{
            if(shouldBeClosed) close(); 
        }
    }
    
    private boolean isExist(Computer computer, String username) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE username=? AND product_id=?");
        statement.setString(1, username);
        statement.setLong(2, computer.getId());
        return statement.executeQuery().next();
    }
    
    private void insertOrder(Computer computer, String username) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?)");
        statement.setString(1, username);
        statement.setLong(2, computer.getId());
        statement.setInt(3, computer.getQuantity());
        statement.executeUpdate();
    }
    
    private void updateOrder(Computer computer, String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE orders SET quanity=? WHERE username=? AND product_id=?");
        statement.setInt(1, computer.getQuantity());
        statement.setString(2, username);
        statement.setLong(3, computer.getId());
        statement.executeUpdate();
    }
    
}
