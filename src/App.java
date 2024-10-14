import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;

public class App {
    private Customer loggedInCustomer;
    private List<Product> shoppingCart = new ArrayList<>();
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
            System.out.println("3. Staff Login");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleLogin();
                    break;
                case "2":
                    handleRegistration();
                    break;
                case "3":
                    //handleStaffLogin();
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
            showMainMenu();
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
        System.out.print("Enter postalCode: ");
        String postalCode = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Customer customer = new Customer(0, name, gender, birthdate, phoneNumber, address, postalCode, username, password);
        CustomerDAO customerDAO = new CustomerDAO();
        if (customerDAO.registerCustomer(customer)) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Registration failed.");
        }
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("\nPizza Ordering System - Main Menu");
            System.out.println("1. Pizzas");
            System.out.println("2. Drinks");
            System.out.println("3. Desserts");
            System.out.println("4. View Cart");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showPizzasMenu();
                    break;
                case "2":
                    showDrinksMenu();
                    break;
                case "3":
                    showDessertsMenu();
                    break;
                case "4":
                    viewCart();
                    break;
                case "5":
                    loggedInCustomer = null;
                    shoppingCart.clear();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showPizzasMenu() {
        PizzaDAO pizzaDAO = new PizzaDAO();
        List<Product> pizzaList = pizzaDAO.getAllPizzas();

        while (true) {
            System.out.println("\nPizza Ordering System - Pizzas Menu");
            for (int i = 0; i < pizzaList.size(); i++) {
                Product pizza = pizzaList.get(i);
                System.out.printf("%d. %s - Price: %.2f€\n   Ingredients: %s\n   Vegetarian: %s\n   Vegan: %s\n",
                        i + 1, pizza.getName(), pizza.getPrice(), pizza.getIngredients(),
                        pizza.isVegetarian() ? "Yes" : "No",
                        pizza.isVegan() ? "Yes" : "No");
            }
            System.out.println((pizzaList.size() + 1) + ". Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            try {
                int option = Integer.parseInt(choice);
                if (option >= 1 && option <= pizzaList.size()) {
                    addToCart(pizzaList.get(option - 1));
                } else if (option == pizzaList.size() + 1) {
                    return;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void showDrinksMenu() {
        DrinkDAO drinkDAO = new DrinkDAO();
        List<Product> drinkList = drinkDAO.getAllDrinks();

        while (true) {
            System.out.println("Pizza Ordering System - Drinks Menu");
            for (int i = 0; i < drinkList.size(); i++) {
                Product drink = drinkList.get(i);
                System.out.printf("%d. %s - Price: %.2f€",
                        i + 1, drink.getName(), drink.getPrice());
            }
            System.out.println((drinkList.size() + 1) + ". Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            try {
                int option = Integer.parseInt(choice);
                if (option >= 1 && option <= drinkList.size()) {
                    addToCart(drinkList.get(option - 1));
                } else if (option == drinkList.size() + 1) {
                    return;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        
            System.out.println((drinkList.size() ) + ". Back to Main Menu");
            System.out.print("Choose an option: ");
            String choices = scanner.nextLine();

            try {
                int option = Integer.parseInt(choices);
                if (option >= 1 && option <= drinkList.size()) {
                    addToCart(drinkList.get(option - 1));
                } else if (option == drinkList.size() + 1) {
                    return;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    

    private void showDessertsMenu() {
        DessertDAO dessertDAO = new DessertDAO();
        List<Product> dessertList = dessertDAO.getAllDesserts();

        while (true) {
            System.out.println("Pizza Ordering System - Desserts Menu");
            for (int i = 0; i < dessertList.size(); i++) {
                Product dessert = dessertList.get(i);
                System.out.printf("%d. %s - Price: %.2f€",
                        i + 1, dessert.getName(), dessert.getPrice());
            }
            System.out.println((dessertList.size() + 1) + ". Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            try {
                int option = Integer.parseInt(choice);
                if (option >= 1 && option <= dessertList.size()) {
                    addToCart(dessertList.get(option - 1));
                } else if (option == dessertList.size() + 1) {
                    return;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
            
            
            System.out.println((dessertList.size() + 1) + ". Back to Main Menu");
            System.out.print("Choose an option: ");
            String choices = scanner.nextLine();

            try {
                int option = Integer.parseInt(choices);
                if (option >= 1 && option <= dessertList.size()) {
                    addToCart(dessertList.get(option - 1));
                } else if (option == dessertList.size() + 1) {
                    return;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }


    private void addToCart(Product item) {
        shoppingCart.add(item);
        System.out.println("Added to cart: " + item.getName());
        double cartTotal = 0;
        for(int i = 0; i < shoppingCart.size(); i++){
                cartTotal += shoppingCart.get(i).getPrice();
        }
        System.out.println("Cart total: €" + cartTotal );
    }

    private void viewCart() {
        if (shoppingCart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
        } else {
            System.out.println("\nShopping Cart:");
            for (int i = 0; i < shoppingCart.size(); i++) {
                Product item = shoppingCart.get(i);
                System.out.printf("%d. %s - Price: %.2f€\n", i + 1, item.getName(), item.getPrice());
            }
            System.out.println("------------");
            double orderTotal = 0;
            for(int i = 0; i < shoppingCart.size(); i++){
                    orderTotal += shoppingCart.get(i).getPrice();
            }
            System.out.println("Order Total €" + orderTotal);
            System.out.println("------------");
            System.out.println("\n1. Place Order");
            System.out.println("2. Back to Main Menu");
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
            Order order = new Order(0, loggedInCustomer.getId(), shoppingCart, "Pending", LocalDateTime.now(), loggedInCustomer.getPostalCode());
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