import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrinkDAO {
    public List<Product> getAllDrinks() {
        List<Product> drinks = new ArrayList<>();
        String query = "SELECT * FROM drinks";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                drinks.add(new Drinks(
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

        return drinks;
    }
}
