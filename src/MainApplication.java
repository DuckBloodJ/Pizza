import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        PizzaDAO pizzaDAO = new PizzaDAO();
        List<Pizza> pizzaList = pizzaDAO.getAllPizzas();

        ObservableList<String> pizzaObservableList = FXCollections.observableArrayList();
        for (Pizza pizza : pizzaList) {
            pizzaObservableList.add(pizza.getName() + " - Price: " + pizza.getPrice() + "€");
        }

        ListView<String> pizzaListView = new ListView<>(pizzaObservableList);

        VBox vbox = new VBox(pizzaListView);
        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setTitle("Pizza Ordering System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
