package controllers;

import dao.ComputersDAO;
import dao.OrdersDAO;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
    
    public void supplyComputer(Computer computer) {
        Computer newComputer = new Computer(createUniqueId(), createRandomString(),
                createRandomString(), getRandomRam(), createRandomString(),
                new Random().nextDouble() * 10000, false, 0);
        computersList.add(computersList.indexOf(computer), newComputer);
        new ComputersDAO().addNewComputer(newComputer);
    }
    
    public void remove(Computer computer) {
        int orderCount = order.size(); 
        boolean exist = false; 
        for(int i = 0; i < orderCount && !exist; i++) {
            if(order.get(i).getId().equals(computer.getId())) exist = true; 
        }
        if(!exist) {
            computersList.remove(computer); 
            new ComputersDAO().remove(computer); 
        }
    }
    
    public List<Computer> getOrder() { return order; }
    
    public void setOrder(List<Computer> order) { this.order = order; }
    
    private Long createUniqueId() {
        Long id = 0l; 
        int computersCount = computersList.size(); 
        for(int i = 0; i < computersCount; i++) {
            if(!id.equals(computersList.get(i).getId())) return id; 
            id++;
        }
        return id; 
    }
    
    private String createRandomString() {
        Random rand = new Random(); 
        int length = rand.nextInt(16) + 1;
        String text = "";
        for(int i = 0; i <= length; i++) {
            text += (char)(rand.nextBoolean() ? rand.nextInt(26) + 97 : rand.nextInt(10) + 48);
        }
        return text; 
    }
    
    private int getRandomRam() {
        int[] rams = new int[]{2, 4, 6, 16};
        return rams[new Random().nextInt(4)];
    }
}
