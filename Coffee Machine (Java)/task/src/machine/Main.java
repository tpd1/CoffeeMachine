package machine;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();

        while (coffeeMachine.getMachineStatus() != CoffeeMachine.MachineStatus.SHUTTING_DOWN) {
            String userAction = in.next().toLowerCase();
            coffeeMachine.processInput(userAction);
        }
        System.out.println("Shutting down coffee machine.");

    }
}
