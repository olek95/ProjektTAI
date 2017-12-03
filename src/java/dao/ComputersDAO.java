package dao;

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
            try {
                connection.close();
            }catch(SQLException ex) {
                writeError(ex);
            }
        }
        return computers;
    }
}
