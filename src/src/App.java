import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {
    private Customer loggedInCustomer;

    @Override
    public void start(Stage primaryStage) {
        showLoginPage(primaryStage);
    }

    private void showLoginPage(Stage primaryStage) {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.getCustomerByUsernameAndPassword(username, password);
            if (customer != null) {
                loggedInCustomer = customer;
                showMenuPage(primaryStage);
            } else {
                System.out.println("Invalid username or password");
            }
        });

        registerButton.setOnAction(event -> showRegistrationPage(primaryStage));

        VBox vbox = new VBox(usernameField, passwordField, loginButton, registerButton);
        Scene scene = new Scene(vbox, 900, 600);

        primaryStage.setTitle("Pizza Ordering System - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showRegistrationPage(Stage primaryStage) {
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        DatePicker birthdatePicker = new DatePicker();
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");
        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button registerButton = new Button("Register");

        registerButton.setOnAction(event -> {
            String name = nameField.getText();
            String gender = genderComboBox.getValue();
            String birthdate = (birthdatePicker.getValue() != null) ? birthdatePicker.getValue().toString() : "";
            String phoneNumber = phoneNumberField.getText();
            String address = addressField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            Customer customer = new Customer(0, name, gender, birthdate, phoneNumber, address, username, password);
            CustomerDAO customerDAO = new CustomerDAO();
            if (customerDAO.registerCustomer(customer)) {
                System.out.println("Registration successful");
                showLoginPage(primaryStage);
            } else {
                System.out.println("Registration failed");
            }
        });

        VBox vbox = new VBox(nameField, genderComboBox, birthdatePicker, phoneNumberField, addressField, usernameField, passwordField, registerButton);
        Scene scene = new Scene(vbox, 900, 600);

        primaryStage.setTitle("Pizza Ordering System - Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMenuPage(Stage primaryStage) {
        PizzaDAO pizzaDAO = new PizzaDAO();
        List<Pizza> pizzaList = pizzaDAO.getAllPizzas();

        ObservableList<String> pizzaObservableList = FXCollections.observableArrayList();
        for (Pizza pizza : pizzaList) {
            pizzaObservableList.add(pizza.getName() + " - Price: " + pizza.getPrice() + "€");
        }

        ListView<String> pizzaListView = new ListView<>(pizzaObservableList);

        VBox vbox = new VBox(pizzaListView);
        Scene scene = new Scene(vbox, 900, 600);

        primaryStage.setTitle("Pizza Ordering System - Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
