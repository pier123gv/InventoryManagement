package backend.inventorymanagementserver.userinterface;

import backend.inventorymanagementserver.InventoryManagementServer;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandLineInterface implements Runnable {
    private final InventoryManagementServer management;

    public CommandLineInterface(InventoryManagementServer management) {
        this.management = management;
    }

    private void showMenu() {
        System.out.println("\n=== Inventory Management ===");
        System.out.println("1. Add Product");
        System.out.println("2. Sell Product");
        System.out.println("3. Restock Product");
        System.out.println("4. Display Inventory");
        System.out.println("5. Export Inventory CSV");
        System.out.println("6. Exit");
    }

    private boolean execute(int choice) {
        Scanner sc2 = new Scanner(System.in);
        int productStock, productCode;
        String productName;
        Object productKey;
        int choice2;

        switch (choice) {
            case 1 -> {
                System.out.println("Enter new product's name: ");
                productName = sc2.nextLine();
                System.out.println("Enter new product's stock: ");
                productStock = Integer.parseInt(sc2.nextLine());
                management.addProductToDB(productName, productStock);
                System.out.println("Product added successfully!");
                return true;
            }
            case 2 -> {
                System.out.println("1: By productCode, 2: By productName");
                choice2 = Integer.parseInt(sc2.nextLine());
                System.out.println("Enter product key (Name or Code): ");
                productKey = sc2.nextLine();
                if (choice2 == 1) productKey = ((int)productKey);  // Correct conversion
                System.out.println("Enter sell amount: ");
                productStock = Integer.parseInt(sc2.nextLine());
                boolean success = management.sellProductInDB(productKey, productStock);
                System.out.println(success ? "Product sold successfully!" : "Failed to sell product.");
                return true;
            }
            case 3 -> {
                System.out.println("1: By productCode, 2: By productName");
                choice2 = Integer.parseInt(sc2.nextLine());
                System.out.println("Enter product key (Name or Code): ");
                productKey = sc2.nextLine();
                if (choice2 == 1) productKey = ((int) productKey);  // Correct conversion
                System.out.println("Enter additional product stock: ");
                productStock = Integer.parseInt(sc2.nextLine());
                boolean success = management.restockProductInDB(productKey, productStock);
                System.out.println(success ? "Product restocked successfully!" : "Failed to restock product.");
                return true;
            }
            case 4 -> {
                System.out.println("Current Inventory:");
                System.out.println(management.showDatabaseContent().replace("##",","));
                return true;
            }
            case 5 -> {
                if(management.exportCSV()){
                    System.out.println("Inventory exported successfully!");
                    return true;
                }
                return false;
            }
            case 6 -> {
                System.out.println("Exiting...");
                return false;
            }
            default -> {
                System.out.println("Incorrect option, try again...");
                return false;
            }
        }
        
    }

    @Override
    public void run() {
        boolean running = true;
        Scanner sc = new Scanner(System.in);

        while (running) {
            showMenu();
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
                running = execute(choice);  // Proper handling of the return value
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
