import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> getAllOrders() {
    List<Order> orders = new ArrayList<>();
    String query = "SELECT orders.*, customers.postalCode FROM orders " +
                   "JOIN customers ON orders.customer_id = customers.id";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int orderId = rs.getInt("id");
            int customerId = rs.getInt("customer_id");
            String status = rs.getString("status");
            String postalCode = rs.getString("postalCode");
            Double totalPrice = rs.getDouble("totalPrice");

            // Fetch pizzas for this order
            PizzaDAO pizzaDAO = new PizzaDAO();
            List<Product> pizzas = pizzaDAO.getAllPizzas(); // Assuming getPizzasByOrderId method

            orders.add(new Order(orderId, customerId, pizzas, status, LocalDateTime.now(), postalCode, (double) (Math.round(totalPrice*100)/100), false));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return orders;
}


    public boolean placeOrder(Order order) {
        String query = "INSERT INTO orders (customer_id, status, products, orderTime, postalCode, totalPrice) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setString(2, order.getStatus());
            stmt.setString(3, order.getProductString(order.getPizzas()));
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(order.getOrderTime()));
            stmt.setString(5, order.getPostalCode());
            stmt.setDouble(6, order.getPrice() + 93.89999999999999);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        addPizzasToOrder(orderId, order.getPizzas());
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addPizzasToOrder(int orderId, List<Product> pizzas) {
        String query = "INSERT INTO pizza_order (order_id, pizza_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Product pizza : pizzas) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, pizza.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Order> getAllPending(){
        List<Order> orders = getAllOrders();
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("Pending")) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }
    public List<Order> getAllBeingDelivered(){
        List<Order> orders = getAllOrders();
        List<Order> beingDeliveredOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("Being Delivered")) {
                beingDeliveredOrders.add(order);
            }
        }
        return beingDeliveredOrders;
    }
    public List<Order> getAllCompleted(){
        List<Order> orders = getAllOrders();
        List<Order> completedOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("Completed")) {
                completedOrders.add(order);
            }
        }
        return completedOrders;
    }
    public void editOrderStatus(String ordersid, String newStatus){
        int orderID = Integer.parseInt(ordersid);
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderID);
            stmt.executeUpdate();
        } catch (SQLException e) {}
    }
    public List<Order> getAllReadyToDeliver(){
        List<Order> orders = getAllOrders();
        List<Order> readyToDeliverOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("Ready to Deliver")) {
                readyToDeliverOrders.add(order);
            }
        }
        return readyToDeliverOrders;
    }
    public List<Order> getFilteredOrders(String postalCode, String gender, Integer minAge) {
        List<Order> filteredOrders = new ArrayList<>();
        String query = "SELECT o.* FROM orders o " +
                       "JOIN customers c ON o.customer_id = c.id " +
                       "WHERE YEAR(o.order_date) = YEAR(CURDATE()) AND MONTH(o.order_date) = MONTH(CURDATE())";
        
        if (!postalCode.isEmpty()) {
            query += " AND c.postal_code = ?";
        }
        if (!gender.isEmpty()) {
            query += " AND c.gender = ?";
        }
        if (minAge != null) {
            query += " AND YEAR(CURDATE()) - YEAR(c.birthdate) >= ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            int paramIndex = 1;
            if (!postalCode.isEmpty()) {
                pstmt.setString(paramIndex++, postalCode);
            }
            if (!gender.isEmpty()) {
                pstmt.setString(paramIndex++, gender);
            }
            if (minAge != null) {
                pstmt.setInt(paramIndex++, minAge);
            }

            ResultSet rs = pstmt.executeQuery("SELECT * FROM orders");
            while (rs.next()) {
                while (rs.next()) {
                    int orderId = rs.getInt("id");
                    int customerId = rs.getInt("customer_id");
                    String status = rs.getString("status");
                    Double price = rs.getDouble("totalPrice");
        
                    // Fetch pizzas for this order
                    PizzaDAO pizzaDAO = new PizzaDAO();
                    List<Product> pizzas = pizzaDAO.getAllPizzas(); // Assuming getPizzasByOrderId method
        
                    filteredOrders.add(new Order(orderId, customerId, pizzas, status, LocalDateTime.now(), postalCode,  (double) (Math.round(price*100)/100), false));
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredOrders;
    }
}