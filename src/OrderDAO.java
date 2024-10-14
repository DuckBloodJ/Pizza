import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

            // Fetch pizzas for this order
            PizzaDAO pizzaDAO = new PizzaDAO();
            List<Product> pizzas = pizzaDAO.getAllPizzas(); // Assuming getPizzasByOrderId method

            orders.add(new Order(orderId, customerId, pizzas, status, LocalDateTime.now(), postalCode, false));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return orders;
}


    public boolean placeOrder(Order order) {
        String query = "INSERT INTO orders (customer_id, status) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setString(2, order.getStatus());
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
}