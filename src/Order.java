import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private List<Pizza> pizzas;
    private String status;

    public Order(int id, int customerId, List<Pizza> pizzas, String status) {
        this.id = id;
        this.customerId = customerId;
        this.pizzas = pizzas;
        this.status = status;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public String getStatus() {
        return status;
    }
}