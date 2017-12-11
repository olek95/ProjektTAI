package controllers;

import dao.RegistrationDAO;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import to.User;

@ManagedBean
@ViewScoped
public class RegistrationViewController {

    public RegistrationViewController() {
    }
    
    public void createUser(String username, String password) {
        String message = validate(username, password); 
        if(message.isEmpty()) {
            RegistrationDAO regDAO = new RegistrationDAO(); 
            regDAO.createUser(new User(username, password));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        }
    }
    
    private String validate(String username, String password) {
        if(username.isEmpty() || password.isEmpty()) 
            return getProperText("errorEmptyFields");
        else if(new RegistrationDAO().exists(username))
            return getProperText("errorUserDuplication");
        return "";
    }
    
    private String getProperText(String key) {
        return ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(key);
    }
}
