package controllers;

import javax.faces.context.FacesContext;

public class MainSessionController {

    public MainSessionController() {
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/welcome.xhtml?faces-redirect=true";
    }
    
}
