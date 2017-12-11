package controllers;

import dao.OrdersDAO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import to.Computer;

@ManagedBean
@ViewScoped
public class OrderViewController {
    @ManagedProperty(value="#{computersSessionController}") 
    private ComputersSessionController computersController;
    private List<Computer> order, computersToBeRemoved, initialOrder; 
    private boolean saved; 

    /**
     * Creates a new instance of OrderSessionController
     */
    public OrderViewController() {
        OrdersDAO dao = new OrdersDAO();
        computersToBeRemoved = new ArrayList(); 
        initialOrder = new ArrayList();
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
        saved = false; 
    }
    
    public void deleteAll() {
        computersToBeRemoved = order;
        order.clear();
        saved = false; 
    }
    
    public void changeQuantity(Computer computer) {
        saved = false; 
    }
    
    public void save() {
        OrdersDAO dao = new OrdersDAO();
        dao.saveOrder(order, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser(), false);
        int computersToBeRemovedQuantity = computersToBeRemoved.size(); 
        String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        for(int i = 0; i < computersToBeRemovedQuantity; i++) {
            dao.delete(computersToBeRemoved.get(i), username, i == (computersToBeRemovedQuantity - 1));
        }
        computersToBeRemoved.clear();
        saved = true; 
    }
    
    public String back() {
        return "/welcome.xhtml?faces-redirect=true";
    }
    
    @PostConstruct
    private void fillOrder() {
        this.order = computersController.getOrder();
        int orderCount = order.size();
        for(int i = 0; i < orderCount; i++) {
            Computer computer = order.get(i);
            initialOrder.add(new Computer(computer.getId(), computer.getModel(), 
                    computer.getProducer(), computer.getRam(), computer.getColor(),
                    computer.getPrice(), computer.isEdited(), computer.getQuantity()));
        }
    }
    
    @PreDestroy
    private void restore() {
        if(!saved) computersController.setOrder(initialOrder);
    }
}
