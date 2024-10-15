import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;

public class App {
    private Customer loggedInCustomer;
    private Staff loggedInStaff;
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
                    handleStaffLogin();
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
    private void handleStaffLogin(){
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.getStaffByUsernameAndPassword(username, password);
        if (staff != null) {
            loggedInStaff = staff;
            showStaffMenu();
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    private void showStaffMenu(){
        while (true) {
            System.out.println("\nOrder Status" );
            System.out.println("1. Show order Status");
            System.out.println("2. ");
            System.out.println("3. See All Being Delivered");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showorderStatusMenu();
                    break;
                case "2":
                    deliveryStatusMenu();
                    break;
                case "3":
                    analyticsMenu();
                    break;
                case "4":
                    loggedInStaff = null;
                    System.out.println("Logging out");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    // for the order status menu
    private void showorderStatusMenu(){
        while (true) {
            System.out.println("\nOrder Status" );
            System.out.println("1. See All Orders");
            System.out.println("2. See All Pending");
            System.out.println("3. See all Ready to Deliver");
            System.out.println("3. See All Being Delivered");
            System.out.println("4. See All Completed");
            System.out.println("5. Edit order status");
            System.out.println("6. Go back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showAllOrders();
                    break;
                case "2":
                    showAllPending();
                    break;
                case "3":
                    showAllReadyToDeliver();
                    break;
                case "4":
                    showAllBeingDelivered();
                    break;
                case "5":             
                    showAllCompleted();
                    break;
                case "6":
                    editOrderStatus();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private void showAllOrders(){
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getAllOrders();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
    private void showAllPending(){
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getAllPending();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
    private void showAllReadyToDeliver(){
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getAllReadyToDeliver();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
    private void showAllBeingDelivered(){
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getAllBeingDelivered();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
    private void showAllCompleted(){
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getAllCompleted();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
    private void editOrderStatus(){
        while (true) {
        
            OrderDAO orderDAO = new OrderDAO();
            System.out.print("Enter order ID: ");
            String orderID = scanner.nextLine();
            System.out.print("Enter new status: ");
            String newStatus = scanner.nextLine();
            if(checkOrderID(orderID) && checkOrderStatus(newStatus)){
                orderDAO.editOrderStatus(orderID, newStatus);
                System.out.println("Order status updated successfully.");
            }
            else{
                System.out.println("Invalid order ID or status.");
            }
        }
}
    private boolean checkOrderID(String orderID){
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getAllOrders();
        int number = 0;
        try {
            // Attempt to convert the string to an integer
            number = Integer.parseInt(orderID);
            for (Order order : orders) {
                if (order.getId() == number) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            // Handle the case where the string is not a valid integer
            System.err.println("Error: Invalid number  for input '" + orderID + "'");
        }
        System.out.println("Order ID not found.");
        return false;
    }
    private boolean checkOrderStatus(String orderStatus){
        if(orderStatus.equals("Pending") || orderStatus.equals("Ready to Deliver") || orderStatus.equals("Being Delivered") || orderStatus.equals("Completed")){
            return true;
        }
        else{
            System.out.println("Invalid status.");
            return false;
        }
    }
    // end of order status menu
    // for the delivery status menu
    private void deliveryStatusMenu(){
        while (true) {
            System.out.println("\nOrder Status" );
            System.out.println("1. See all Available Drivers");
            System.out.println("2. See All Being Delivered");
            System.out.println("3. Edit order status");
            System.out.println("4. Go back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // prints the ones being delivered 
                    showAllAvailableDrivers();
                    break;
                case "2":
                    //prints the ones being 
                    showAllBeingDelivered();
                    break;
                case "3":
                    editOrderStatus();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private void showAllAvailableDrivers() {
        StaffDAO staffDAO = new StaffDAO();
        ArrayList<DeliveryPerson> deliveryStaff = staffDAO.getAvailableDeliveryStaff();
        if (deliveryStaff.isEmpty()) {
            System.out.println("No available delivery staff at the moment.");
        } else {
            System.out.println("Available Delivery Staff:");
            for (Staff deliveryPerson : deliveryStaff) {
                System.out.println(deliveryPerson);
            }
        }
    }
    // end of the delivery status menu
    
    
    private void analyticsMenu(){
        while (true) {
            System.out.println("\nStore Analytics" );
            System.out.println("1. See Monthly Report");
            System.out.println("2. Go back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    showMonthlyRevenue();
                    break;
                case "2":
                    return;
            }
        }
    }
    private void showMonthlyRevenue(){
        OrderDAO orderDAO = new OrderDAO();

        System.out.println("\nMonthly Earnings Report");
        System.out.print("Filter by postal code (leave blank for all): ");
        String postalCode = scanner.nextLine();
        System.out.print("Filter by gender (M/F/O, leave blank for all): ");
        String gender = scanner.nextLine();
        System.out.print("Filter by minimum age (leave blank for all): ");
        String minAgeStr = scanner.nextLine();
        Integer minAge = minAgeStr.isEmpty() ? null : Integer.parseInt(minAgeStr);

        List<Order> orders = orderDAO.getFilteredOrders(postalCode, gender, minAge);
        double totalRevenue = 0;
        for (Order order : orders) {
            totalRevenue += order.getPrice();
        }

        System.out.printf("Total Revenue for this month: €%.2f\n", totalRevenue);
        System.out.println("Filters applied:");
        System.out.println("Postal Code: " + (postalCode.isEmpty() ? "All" : postalCode));
        System.out.println("Gender: " + (gender.isEmpty() ? "All" : gender));
        System.out.println("Minimum Age: " + (minAge == null ? "All" : minAge));
        System.out.println("------------");
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
            double orderTotal = 0.0;
            int pizzaCount = 0;
            for(int i = 0; i < shoppingCart.size(); i++){
                    orderTotal += shoppingCart.get(i).getPrice();
                    if(shoppingCart.get(i) instanceof Pizza){
                        pizzaCount++;
                    }

            }
            boolean dis10 = loggedInCustomer.tenPizzaCheck();
            orderTotal = (dis10) ? orderTotal * 0.9 : orderTotal;
            loggedInCustomer.addPizzaCount(pizzaCount);
            
            System.out.println((dis10) ? "Order Total €" + orderTotal+ "\n (With 10% discount)" : "Order Total €" + orderTotal);
            System.out.println("------------");
            System.out.println("\n1. Place Order");
            System.out.println("2. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    placeOrder(loggedInCustomer.tenPizzaCheck());
                    break;
                case "2":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void placeOrder(boolean discount10) {
        if (!shoppingCart.isEmpty() && loggedInCustomer != null) {
            OrderDAO orderDAO = new OrderDAO();
            Order order = new Order(0, loggedInCustomer.getId(), shoppingCart, "Pending", LocalDateTime.now(), loggedInCustomer.getPostalCode(), discount10);
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