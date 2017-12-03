package controllers;

import dao.ComputersDAO;
import dao.OrdersDAO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import to.Computer;

@ManagedBean
@SessionScoped
public class ComputersSessionController {
    private List<Computer> computersList, order = null; 
    public ComputersSessionController() {
        ComputersDAO dao = new ComputersDAO();
        computersList = dao.getAllComputers();
        OrdersDAO orderDAO = new OrdersDAO();
        order = orderDAO.getOrder(FacesContext.getCurrentInstance()
                .getExternalContext().getRemoteUser());
    }
    
    public List<Computer> getComputersList() {
        return computersList; 
    }
    
    public void setComputersList(List<Computer> computersList) {
        this.computersList = computersList; 
    }
    
    public void buy(Computer computer, int quantity) {
        int index = -1;
        for(int i = 0; i < order.size() && index == -1; i++)
            if(order.get(i).getId().equals(computer.getId()))
                index = i;
        computer.setQuantity(quantity);
        if(index == -1) order.add(computer);
        else order.set(index, computer);
    }
    
    public List<Computer> getOrder() { return order; }
    
    public void setOrder(List<Computer> order) { this.order = order; }
}
