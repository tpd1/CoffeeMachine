package machine;

import static machine.CoffeeMachine.MachineStatus.*;

public class CoffeeMachine {
    public enum MachineStatus {
        MAIN_MENU, SHUTTING_DOWN, COFFEE_CHOICE, ADD_WATER, ADD_MILK, ADD_COFFEE, ADD_CUPS
    }

    private CoffeeType[] coffeeTypes;
    private int currentWater;
    private int currentMilk;
    private int currentCoffee;
    private int currentCups;
    private int currentMoney;
    private MachineStatus currentStatus;

    public static final int DEFAULT_WATER = 400;
    public static final int DEFAULT_MILK = 540;
    public static final int DEFAULT_COFFEE = 120;
    public static final int DEFAULT_CUPS = 9;
    public static final int DEFAULT_MONEY = 550;
    private static final String NOT_ENOUGH = "Sorry, not enough ";
    private static final String WRITE_ACTION = "Write action (buy, fill, take, remaining, exit):";
    private static final String COFFEE_CHOICE_MSG = "\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:";
    private static final String CAN_MAKE = "I have enough resources, making you a coffee!\n";


    public CoffeeMachine() {
        currentWater = DEFAULT_WATER;
        currentMilk = DEFAULT_MILK;
        currentCoffee = DEFAULT_COFFEE;
        currentCups = DEFAULT_CUPS;
        currentMoney = DEFAULT_MONEY;
        currentStatus = MAIN_MENU;
        initialiseCoffeeTypes();
        showMainMenu();
    }

    public CoffeeMachine(int water, int milk, int coffee, int cups, int money) {
        currentWater = water;
        currentMilk = milk;
        currentCoffee = coffee;
        currentCups = cups;
        currentMoney = money;
        initialiseCoffeeTypes();
        showMainMenu();
    }

    private void initialiseCoffeeTypes() {
        coffeeTypes = new CoffeeType[3];
        coffeeTypes[0] = new CoffeeType("espresso", 250, 0, 16, 4);
        coffeeTypes[1] = new CoffeeType("latte", 350, 75, 20, 7);
        coffeeTypes[2] = new CoffeeType("cappuccino", 200, 100, 12, 6);
    }

    public void showMainMenu() {
        currentStatus = MAIN_MENU;
        System.out.println(WRITE_ACTION);
    }

    public void processInput(String input) {
        if (currentStatus == MAIN_MENU) {
            switch (input) {
                case "remaining" ->  {
                    printStatus();
                    showMainMenu();
                }
                case "take" -> {
                    takeMoney();
                    showMainMenu();
                }
                case "exit" -> currentStatus = SHUTTING_DOWN;
                case "buy" -> {
                    currentStatus = COFFEE_CHOICE;
                    System.out.println(COFFEE_CHOICE_MSG);
                }
                case "fill" -> {
                    currentStatus = ADD_WATER;
                    System.out.println("\nWrite how many ml of water you want to add:");
                }
            }
        } else if (currentStatus == ADD_WATER) {
            if (isPositiveNumber(input)) {
                addWater(Integer.parseInt(input));
                currentStatus = ADD_MILK;
                System.out.println("Write how many ml of milk you want to add:");
            }
        } else if (currentStatus == ADD_MILK) {
            if (isPositiveNumber(input)) {
                addMilk(Integer.parseInt(input));
                currentStatus = ADD_COFFEE;
                System.out.println("Write how many g of coffee you want to add:");
            }
        } else if (currentStatus == ADD_COFFEE) {
            if (isPositiveNumber(input)) {
                addCoffee(Integer.parseInt(input));
                currentStatus = ADD_CUPS;
                System.out.println("Write how many disposable cups you want to add:");
            }
        } else if (currentStatus == ADD_CUPS) {
            if (isPositiveNumber(input)) {
                addCups(Integer.parseInt(input));
                System.out.println();
                showMainMenu();
            }
        } else if (currentStatus == COFFEE_CHOICE) {
            if (isPositiveNumber(input)) {
                buyDrink(Integer.parseInt(input));
                showMainMenu();
            } else if (input.equals("back")) {
                showMainMenu();
            }
        } else {
            System.out.println("Unknown command");
            showMainMenu();
        }

    }

    public MachineStatus getMachineStatus() {
        return this.currentStatus;
    }

    public static boolean isPositiveNumber(String str) {
        try {
            int num = Integer.parseInt(str);
            return num >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void printStatus() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(currentWater + " ml of water");
        System.out.println(currentMilk + " ml of milk");
        System.out.println(currentCoffee + " g of coffee beans");
        System.out.println(currentCups + " disposable cups");
        System.out.println("$" + currentMoney + " of money");
        System.out.println();
    }

    public void buyDrink(int choice) {
        if (choice == 1) { // Espresso
            if (canMakeDrink(coffeeTypes[0])) {
                makeCoffee(coffeeTypes[0]);
            }
        } else if (choice == 2) { // Latte
            if (canMakeDrink(coffeeTypes[1])) {
                makeCoffee(coffeeTypes[1]);
            }
        } else if (choice == 3) { // Cappuccino
            if (canMakeDrink(coffeeTypes[2])) {
                makeCoffee(coffeeTypes[2]);
            }
        } else {
            System.out.println("Unknown Coffee Type");
        }
    }

    private void makeCoffee(CoffeeType coffeeType) {
        System.out.println(CAN_MAKE);
        currentWater -= coffeeType.getWaterAmount();
        currentMilk -= coffeeType.getMilkAmount();
        currentCoffee -= coffeeType.getCoffeeAmount();
        currentCups--;
        currentMoney += coffeeType.getPrice();
    }

    private boolean canMakeDrink(CoffeeType coffeeType) {
        if (currentWater - coffeeType.getWaterAmount() < 0) {
            System.out.println(NOT_ENOUGH + "water!\n");
            return false;
        } else if (currentMilk - coffeeType.getMilkAmount() < 0) {
            System.out.println(NOT_ENOUGH + "milk!\n");
            return false;
        } else if (currentCoffee - coffeeType.getCoffeeAmount() < 0) {
            System.out.println(NOT_ENOUGH + "coffee!\n");
            return false;
        } else if (currentCups - 1 < 0) {
            System.out.println(NOT_ENOUGH + "cups!\n");
            return false;
        }
        return true;
    }

    public void addWater(int amount) {
        if (amount > 0) {
            currentWater += amount;
        }
    }

    public void addMilk(int amount) {
        if (amount > 0) {
            currentMilk += amount;
        }
    }

    public void addCoffee(int amount) {
        if (amount > 0) {
            currentCoffee += amount;
        }
    }

    public void addCups(int amount) {
        if (amount > 0) {
            currentCups += amount;
        }
    }

    private void takeMoney() {
        int moneyToGive = currentMoney;
        System.out.printf("\nI gave you $%d\n", moneyToGive);
        currentMoney = 0;
    }
}
