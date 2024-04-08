import controller.ClientController;
import controller.ProductController;
import controller.PurchaseController;
import controller.ShopController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String option;
        String message = """
                ....::::::   MENU   ::::::....
                1. Buy Products.
                2. Manage Shops.
                3. Manage Products.
                4. Manage Clients.
                5. Exit.
                                
                ENTER THE OPTION TO CONTINUE...
                """;
        do {
            option = JOptionPane.showInputDialog(null, message);
            if (option == null) {
                break;
            }
            switch (option) {
                case "1":
                    PurchaseController.menu();
                    break;
                case "2":
                    ShopController.menu();
                    break;
                case "3":
                    ProductController.menu();
                    break;
                case "4":
                    ClientController.menu();
                    break;
            }
        } while (!option.equals("5"));
    }
}