package controllers;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

/**
 *
 * @author olek1
 */
@ManagedBean
@ApplicationScoped
public class ApplicationController {
    private Date startDate;

    public ApplicationController() {
        startDate = new Date();
    }
    
    public String getStartDate() {
        return startDate.toString();
    }
}
