package controllers;

import dao.RegistrationDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import to.User;

@ManagedBean
@ViewScoped
public class RegistrationViewController {

    public RegistrationViewController() {
    }
    
    public void createUser(String username, String password) {
        RegistrationDAO regDAO = new RegistrationDAO(); 
        regDAO.createUser(new User(username, password));
    }
}
