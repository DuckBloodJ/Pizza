import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


public class Order {
    private int id;
    private int customerId;
    private List<Product> products;
    private String status;
    private LocalDateTime orderTime;
    private String postalCode;
    private double totalPrice;

    public Order(int id, int customerId, List<Product> products, String status,LocalDateTime orderTime, String postalCode) {
        this.id = id;
        this.customerId = customerId;
        this.products = products;
        this.status = status;
        this.orderTime = orderTime;
        this.postalCode = postalCode;
        calcPrice();
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    private void calcPrice(){
        for(int i= 0; i < products.size();i++){
            totalPrice += products.get(i).getPrice();
        }
    }
    public double getPrice(){
        return totalPrice;
    }
    public String getPostalCode(){
        return postalCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<Product> getPizzas() {
        return products;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String update){
        status = update;
    }
    public boolean isCancelable() {
        return LocalDateTime.now().isBefore(orderTime.plusMinutes(5));
    }

    public void cancelOrder() {
        if (isCancelable()) {
            this.status = "cancelled";
        } else {
            System.out.println("Order can no longer be cancelled.");
        }
    }
    public LocalDateTime getOrderTime(){
        return orderTime;
    }
    public int getPizzaCount(){
        int pizzaCount = 0;
        for (Product pizzas : products) {
            if (pizzas instanceof Pizza) {
                pizzaCount++;
            }
        }
        return pizzaCount;
    }
}