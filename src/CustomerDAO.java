import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public Customer getCustomerByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM customers WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("birthday"),
                        rs.getString("phone"),
                        rs.getString("address"),rs.getString("postalcode"),
                        rs.getString("username"),
                        rs.getString("password")
                        
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerCustomer(Customer customer) {
        String query = "INSERT INTO customers (name, gender, birthday, phone, address,postal code, username, password) VALUES (?, ?, ?,?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getGender());
            stmt.setString(3, customer.getBirthdate());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6,customer.getPostalCode());
            stmt.setString(6, customer.getUsername());
            stmt.setString(7, customer.getPassword());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}