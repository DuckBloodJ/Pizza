import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int customerId = rs.getInt("customer_id");
                String status = rs.getString("status");

                // Fetch products for this order
                PizzaDAO pizzaDAO = new PizzaDAO();
                List<Product> products = pizzaDAO.getAllPizzas(); // Assuming getPizzasByOrderId method

                orders.add(new Order(orderId, customerId, products, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    public Order getOrderById(int orderId) {
        Order order = null;
        String query = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int customerId = rs.getInt("customer_id");
                    String status = rs.getString("status");

                    // Fetch products for this order
                    PizzaDAO pizzaDAO = new PizzaDAO();
                    List<Product> products = pizzaDAO.getAllPizzas(); // You might want to adjust this method

                    order = new Order(orderId, customerId, products, status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order; // Will return null if no order is found
    }
    public List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("id");
                    String status = rs.getString("status");

                    // Fetch products for this order
                    PizzaDAO pizzaDAO = new PizzaDAO();
                    List<Product> products = pizzaDAO.getAllPizzas(); // Adjust this as needed

                    orders.add(new Order(orderId, customerId, products, status));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // New method to get all orders by status
    public List<Order> getOrdersByStatus(String status) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE status = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("id");
                    int customerId = rs.getInt("customer_id");

                    // Fetch products for this order
                    PizzaDAO pizzaDAO = new PizzaDAO();
                    List<Product> products = pizzaDAO.getAllPizzas(); // Adjust this as needed

                    orders.add(new Order(orderId, customerId, products, status));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}