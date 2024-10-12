import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private Customer loggedInCustomer;
    private List<Pizza> shoppingCart = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    public void start() {
        showLoginPage();
    }

    private void showLoginPage() {
        while (true) {
            System.out.println("\nPizza Ordering System - Login");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleLogin();
                    break;
                case "2":
                    handleRegistration();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerByUsernameAndPassword(username, password);
        if (customer != null) {
            loggedInCustomer = customer;
            showMenuPage();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void handleRegistration() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter gender (Male/Female/Other): ");
        String gender = scanner.nextLine();
        System.out.print("Enter birthdate (YYYY-MM-DD): ");
        String birthdate = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Customer customer = new Customer(0, name, gender, birthdate, phoneNumber, address, username, password);
        CustomerDAO customerDAO = new CustomerDAO();
        if (customerDAO.registerCustomer(customer)) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Registration failed.");
        }
    }

    private void showMenuPage() {
        PizzaDAO pizzaDAO = new PizzaDAO();
        List<Pizza> pizzaList = pizzaDAO.getAllPizzas();

        while (true) {
            System.out.println("\nPizza Ordering System - Menu");
            for (int i = 0; i < pizzaList.size(); i++) {
                Pizza pizza = pizzaList.get(i);
                System.out.printf("%d. %s - Price: %.2f€\n   Ingredients: %s\n   Vegetarian: %s\n   Vegan: %s\n",
                        i + 1, pizza.getName(), pizza.getPrice(), pizza.getIngredients(),
                        pizza.isVegetarian() ? "Yes" : "No",
                        pizza.isVegan() ? "Yes" : "No");
            }
            System.out.println((pizzaList.size() + 1) + ". View Cart");
            System.out.println((pizzaList.size() + 2) + ". Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            try {
                int option = Integer.parseInt(choice);
                if (option >= 1 && option <= pizzaList.size()) {
                    addToCart(pizzaList.get(option - 1));
                } else if (option == pizzaList.size() + 1) {
                    viewCart();
                } else if (option == pizzaList.size() + 2) {
                    loggedInCustomer = null;
                    shoppingCart.clear();
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void addToCart(Pizza pizza) {
        shoppingCart.add(pizza);
        System.out.println("Added to cart: " + pizza.getName());
    }

    private void viewCart() {
        if (shoppingCart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("\nShopping Cart:");
            for (int i = 0; i < shoppingCart.size(); i++) {
                Pizza pizza = shoppingCart.get(i);
                System.out.printf("%d. %s - Price: %.2f€\n", i + 1, pizza.getName(), pizza.getPrice());
            }
            System.out.println("\n1. Place Order");
            System.out.println("2. Back to Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    placeOrder();
                    break;
                case "2":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void placeOrder() {
        if (!shoppingCart.isEmpty() && loggedInCustomer != null) {
            OrderDAO orderDAO = new OrderDAO();
            Order order = new Order(0, loggedInCustomer.getId(), shoppingCart, "Pending");
            if (orderDAO.placeOrder(order)) {
                System.out.println("Order placed successfully!");
                shoppingCart.clear();
            } else {
                System.out.println("Failed to place order.");
            }
        } else {
            System.out.println("Your cart is empty or you are not logged in.");
        }
    }
}
