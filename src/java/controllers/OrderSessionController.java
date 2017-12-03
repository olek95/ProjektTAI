package controllers;

import dao.OrdersDAO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import to.Computer;

@ManagedBean
@ViewScoped
public class OrderSessionController {
    @ManagedProperty(value="#{computersSessionController}") 
    private ComputersSessionController computersController;
    private List<Computer> order, computersToBeRemoved; 
    private OrdersDAO dao;

    /**
     * Creates a new instance of OrderSessionController
     */
    public OrderSessionController() {
        dao = new OrdersDAO();
        computersToBeRemoved = new ArrayList(); 
    }
    
    public List<Computer> getOrder() { return order; }
    
    public void setOrder(List<Computer> order) { this.order = order; }
    
    public void setComputersController(ComputersSessionController computersController) {
        this.computersController = computersController; 
    }
    
    public String getOrderAlias() { return "orderAlias"; }
    
    public void delete(Computer computer) {
        order.remove(computer);
        computersToBeRemoved.add(computer);
    }
    
    public void deleteAll() {
        computersToBeRemoved = order;
        order.clear();
    }
    
    public void save() {
        dao.saveOrder(order, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        int computersToBeRemovedQuantity = computersToBeRemoved.size(); 
        String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        for(int i = 0; i < computersToBeRemovedQuantity; i++) {
            dao.delete(computersToBeRemoved.get(i), username);
        }
        computersToBeRemoved = null;
    }
    
    @PostConstruct
    private void fillOrder() {
        this.order = computersController.getOrder();
    }
    
    @PreDestroy
    private void revert() {
        for(int i = 0; i < computersToBeRemoved.size(); i++) {
            order.add(computersToBeRemoved.get(i));
        }
        computersController.setOrder(order);
    }
}