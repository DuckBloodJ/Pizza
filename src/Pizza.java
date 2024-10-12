public class Pizza {
    private int id;
    private String name;
    private double price;
    private boolean vegetarian;
    private boolean vegan;
    private String ingredients;

    public Pizza(int id, String name, double price, boolean vegetarian, boolean vegan, String ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.ingredients = ingredients;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public String getIngredients() {
        return ingredients;
    }

}
