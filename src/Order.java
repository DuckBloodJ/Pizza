import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private List<Product> products;
    private String status;

    public Order(int id, int customerId, List<Product> products, String status) {
        this.id = id;
        this.customerId = customerId;
        this.products = products;
        this.status = status;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getStatus() {
        return status;
    }
    public void updateStatus(String update ){
        status = update;
    }
}