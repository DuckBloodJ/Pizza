import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDAO {
    
    // Method to get a staff member by username and password (for login purposes)
    public Staff getStaffByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM staff WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Staff(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),   
                        rs.getString("email"),      
                        rs.getString("phone"),
                        rs.getString("address"),
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

    // Method to register a new staff member, only Managers can register others
    public boolean registerStaff(Staff newStaff, Staff loggedInStaff) {
        // Check if the logged-in staff has the "Manager" position
        if (!"Manager".equalsIgnoreCase(loggedInStaff.getPosition())) {
            System.out.println("Only staff with Manager position can register new staff members.");
            return false;
        }
        
        String query = "INSERT INTO staff (name, position, email, phone, address, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStaff.getName());
            stmt.setString(2, newStaff.getPosition()); 
            stmt.setString(3, newStaff.getEmail());   
            stmt.setString(4, newStaff.getPhoneNumber());
            stmt.setString(5, newStaff.getAddress());
            stmt.setString(6, newStaff.getUsername());
            stmt.setString(7, newStaff.getPassword());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
