package to;

public class Computer {
    private long id;
    private String model, producer, color; 
    int ram;
    double price;
    boolean edited; 
    private int quantity;
    public Computer(Long id, String model, String producer, int ram, String color, double price, boolean edited, int quantity) {
        this.id = id; 
        this.model = model;
        this.producer = producer; 
        this.ram = ram;
        this.color = color; 
        this.price = price; 
        this.edited = edited; 
        this.quantity = quantity; 
    }
    
    public Long getId() { return id; }
    
    public void setId(Long id) { this.id = id; }
    
    public String getModel() { return model; }
    
    public void setModel(String model) { this.model = model; }
    
    public String getProducer() { return producer; }
    
    public void setProducer(String producer) { this.producer = producer; }
    
    public int getRam() { return ram; }
    
    public void setRam(int ram) { this.ram = ram; }
    
    public String getColor() { return color; }
    
    public void setColor(String color) { this.color = color; }
    
    public double getPrice() { return price; }
    
    public void setPrice(double price) { this.price = price; }
    
    public boolean isEdited() { return edited; }
    
    public void setEdited(boolean edited) { this.edited = edited; }
    
    public int getQuantity() { return quantity; }
    
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
