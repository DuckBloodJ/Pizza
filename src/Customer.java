public class Customer {
    private int id;
    private String name;
    private String gender;
    private String birthdate;
    private String phoneNumber;
    private String address;
    private String username;
    private String password;
    private String postalCode;
    private int tenPizzaCounter;
    private boolean pizzaDiscount;

    public Customer(int id, String name, String gender, String birthdate, String phoneNumber, String address, String postalCode, String username, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = username;
        this.password = password;
        this.postalCode = postalCode;
        tenPizzaCounter = 0;
        pizzaDiscount = false;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void addPizzaCount(int i){
        tenPizzaCounter += i;
        if (tenPizzaCounter >= 10){
            tenPizzaCounter = tenPizzaCounter % 10;
        }
        else{pizzaDiscount = false;}

    }
    public boolean tenPizzaCheck(){
        return pizzaDiscount;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}