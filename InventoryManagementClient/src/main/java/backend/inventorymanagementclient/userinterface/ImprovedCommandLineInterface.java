package backend.inventorymanagementclient.userinterface;

import backend.inventorymanagementclient.InventoryManagementClient;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ImprovedCommandLineInterface implements Runnable {
    private final InventoryManagementClient management;
    private final Scanner scanner = new Scanner(System.in);

    public ImprovedCommandLineInterface(InventoryManagementClient management) {
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

    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    private float getFloatInput(String prompt) {
    while (true) {
        System.out.print(prompt);
        try {
            return Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a valid decimal number.");
        }
    }
}
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private boolean execute(int choice) {
        switch (choice) {
            case 1 -> addProduct();
            case 2 -> sellProduct();
            case 3 -> restockProduct();
            case 4 -> displayInventory();
            case 5 -> exportInventory();
            case 6 -> {
                System.out.println("Exiting...");
                return false;
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    private void addProduct() {
        String productName = getStringInput("Enter new product's name: ");
        int productStock = getIntInput("Enter new product's stock: ");
        float productPrice = getFloatInput("Enter price: ");
        String description = getStringInput("Enter description: ");
        management.addProductToDB(productName, productStock, productPrice, description);
        System.out.println("Product added successfully!");
    }

    private void sellProduct() {
        int productCode = getIntInput("Enter product code: ");
        int quantity = getIntInput("Enter quantity to sell: ");
        //String result = management.sellProductInDB(productCode, quantity);
        //System.out.println(result);
    }

    private void restockProduct() {
        int productCode = getIntInput("Enter product code: ");
        int additionalStock = getIntInput("Enter additional stock amount: ");
        String result = management.modifyProductStockInDB(productCode, additionalStock);
        System.out.println(result);
    }

    private void displayInventory() {
        String inventoryData = management.requestDB();
        System.out.println("\nCurrent Inventory:\n" + inventoryData);
    }

    private void exportInventory() {
        if (management.exportCSV()) {
            System.out.println("Inventory exported successfully!");
        } else {
            System.out.println("Failed to export inventory.");
        }
    }

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");
            running = execute(choice);
        }
    }
}
