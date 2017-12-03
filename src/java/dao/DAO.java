package dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

public class DAO {
    protected Connection connection; 
    public DAO() {
        try {
            Context ctx = new InitialContext(); 
            DataSource datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/projekt");
            connection = datasource.getConnection();
        }catch(Exception ex) {
            writeError(ex);
           try {
               connection.close();
           }catch(SQLException ex2) {
               writeError(ex2);
           }
        }
    }
    
    protected void writeError(Exception ex) {
        FacesContext facesContext = FacesContext.getCurrentInstance(); 
        ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
        facesContext.getExternalContext().getContext();
        servletContext.log(servletContext.getContextPath() + ":" + ex.toString());
        facesContext.addMessage(null, new FacesMessage(ex.toString()));
    }
}
