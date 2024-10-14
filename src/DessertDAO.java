import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DessertDAO {
    public List<Product> getAllDesserts() {
        List<Product> desserts = new ArrayList<>();
        String query = "SELECT * FROM desserts";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                desserts.add(new Dessert(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getBoolean("vegetarian"),
                    rs.getBoolean("vegan"),
                    rs.getString("ingredients"),
                    rs.getBoolean("isPizza")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return desserts;
    }
}
