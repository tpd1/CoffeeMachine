package machine;

public class CoffeeType {
    private final String name;
    private final int waterAmount;
    private final int milkAmount;
    private final int coffeeAmount;
    private final int price;

    public CoffeeType(String name, int waterAmount, int milkAmount, int coffeeAmount, int price) {
        this.name = name;
        this.waterAmount = waterAmount;
        this.milkAmount = milkAmount;
        this.coffeeAmount = coffeeAmount;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public int getMilkAmount() {
        return milkAmount;
    }

    public int getCoffeeAmount() {
        return coffeeAmount;
    }

    public int getPrice() {
        return price;
    }
}
