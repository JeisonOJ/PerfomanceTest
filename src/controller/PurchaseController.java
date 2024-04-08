package controller;

import entity.Client;
import entity.Product;
import entity.Purchase;
import model.PurchaseModel;
import utils.Utils;

import javax.swing.*;

public class PurchaseController {

    public static PurchaseModel instanceModel() {
        return new PurchaseModel();
    }

    public static String listAllPurchases() {
        StringBuilder message = new StringBuilder();
        message.append("......:::::::   All Purchases   :::::::......\n");
        if (!instanceModel().findAll().isEmpty()) {
            for (Object object : instanceModel().findAll()) {
                Purchase purchase = (Purchase) object;
                message.append(purchase).append("--------------------------\n");
            }
        }
        return message.toString();
    }

    public static void showAllPurchases() {
        JOptionPane.showMessageDialog(null, listAllPurchases());
    }

    /**
     * this method create a purchase
     */
    public static void createPurchase() {
        try {
            Object[] clients = Utils.listToArray(ClientController.instanceModel().findAll());
            Client client = (Client) JOptionPane.showInputDialog(null,
                    "Select client",
                    "Clients",
                    JOptionPane.QUESTION_MESSAGE, null,
                    clients,
                    clients[0]);
            Object[] products = Utils.listToArray(ProductController.instanceModel().findAll());
            Product product = (Product) JOptionPane.showInputDialog(null,
                    "Select product",
                    "Products",
                    JOptionPane.QUESTION_MESSAGE, null,
                    products,
                    products[0]);
            int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter de quantity to buy"));

            if (validate(quantity, product.getStock())) {
                Purchase purchase = new Purchase();
                purchase.setClient(client);
                purchase.setIdClient(client.getId());
                purchase.setProduct(product);
                purchase.setIdProduct(product.getId());
                purchase.setQuantity(quantity);

                purchase = (Purchase) instanceModel().insert(purchase);

                if (purchase.getId() != 0) {
                    int stock = product.getStock() - quantity;
                    product.setStock(stock);
                    updateQuantity(product);
                    JOptionPane.showMessageDialog(null, purchase);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data");
        }

    }

    /**
     * this method update the purchase
     * first return the stock
     * if failed, cancel the return the stock
     */
    public static void updatePurchase() {
        try {
            Object[] purchases = Utils.listToArray(instanceModel().findAll());
            Purchase purchase = (Purchase) JOptionPane.showInputDialog(null,
                    "Select purchase to update",
                    "Update",
                    JOptionPane.QUESTION_MESSAGE, null,
                    purchases,
                    purchases[0]);
            Object[] clients = Utils.listToArray(ClientController.instanceModel().findAll());
            Client client = (Client) JOptionPane.showInputDialog(null,
                    "Select client",
                    "Clients",
                    JOptionPane.QUESTION_MESSAGE, null,
                    clients,
                    clients[0]);
            int oldStock = returnStockAndSaveOldStock(purchase);
            System.out.println(oldStock);
            Object[] products = Utils.listToArray(ProductController.instanceModel().findAll());
            Product product = (Product) JOptionPane.showInputDialog(null,
                    "Select product",
                    "Products",
                    JOptionPane.QUESTION_MESSAGE, null,
                    products,
                    products[0]);

            int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter de quantity to buy"));

            if (validate(quantity, product.getStock())) {
                purchase.setClient(client);
                purchase.setIdClient(client.getId());
                purchase.setProduct(product);
                purchase.setIdProduct(product.getId());
                purchase.setQuantity(quantity);

                if (instanceModel().update(purchase)) {
                    JOptionPane.showMessageDialog(null, "Update successful");
                    int stock = product.getStock() - quantity;
                    product.setStock(stock);
                    updateQuantity(product);
                    JOptionPane.showMessageDialog(null, purchase);
                } else {
                    product.setStock(oldStock);
                    updateQuantity(product);
                    JOptionPane.showMessageDialog(null, "Update failed");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data"+e.getMessage());
        }
    }

    /**
     * This method delete a purchase
     * if success return the stock
     */
    public static void deletePurchase() {
        try {
            Object[] purchases = Utils.listToArray(instanceModel().findAll());
            Purchase purchase = (Purchase) JOptionPane.showInputDialog(null,
                    "Select purchase to delete",
                    "Delete",
                    JOptionPane.QUESTION_MESSAGE, null,
                    purchases,
                    purchases[0]);
            if (instanceModel().delete(purchase.getId())) {
                returnStockAndSaveOldStock(purchase);
                JOptionPane.showMessageDialog(null, "Delete successful");
            } else {
                JOptionPane.showMessageDialog(null, "Delete failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter a number");
        }
    }

    public static void findPurchaseById() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter id to find a purchase"));
            Purchase purchase = (Purchase) instanceModel().findById(number);
            if (purchase != null) {
                JOptionPane.showMessageDialog(null, purchase);
            } else {
                JOptionPane.showMessageDialog(null, "This purchase doesn't exist");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter a number");
        }
    }

    /**
     * list of purchases by product
     */
    public static void findAllPurchasesByProduct() {
        try {
            Object[] products = Utils.listToArray(ProductController.instanceModel().findAll());
            Product product = (Product) JOptionPane.showInputDialog(null,
                    "Select product",
                    "Products",
                    JOptionPane.QUESTION_MESSAGE, null,
                    products,
                    products[0]);
            StringBuilder message = new StringBuilder();
            message.append("......:::::::   All Purchases   :::::::......\n")
                    .append("Product name: ").append(product.getName())
                    .append("\n");
            if (!instanceModel().findAllByProduct(product.getId()).isEmpty()) {
                for (Object object : instanceModel().findAllByProduct(product.getId())) {
                    Purchase purchase = (Purchase) object;
                    message.append(purchase).append("--------------------------\n");
                }
                JOptionPane.showMessageDialog(null, message);
            } else {
                message.append("there are no purchases for this product");
                JOptionPane.showMessageDialog(null, message);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter a number");
        }
    }

    /**
     * This method validate the current stock and if stock is over 0
     * @param quantity
     * @param stock
     * @return
     */
    public static boolean validate(int quantity, int stock) {
        boolean valid = false;
        if (quantity > 0) {
            if (quantity > stock) {
                JOptionPane.showMessageDialog(null, "there are no stock to this quantity");
            } else {
                valid = true;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect quantity, enter positive numbers");
        }

        return valid;
    }

    /**
     * This method updated the quantity can be used to updated when success or fail
     * @param product
     */
    public static void updateQuantity(Product product) {
        ProductController.instanceModel().update(product);
    }

    /**
     * This method return to the old stock before update and save the old stock to handle errors
     * @param purchase
     * @return
     */
    public static int returnStockAndSaveOldStock(Purchase purchase){
        int oldStock = purchase.getProduct().getStock();
        int quantityToReturn = purchase.getQuantity();
        int stock = oldStock + quantityToReturn;
        purchase.getProduct().setStock(stock);
        ProductController.instanceModel().update(purchase.getProduct());
        return oldStock;
    }

    public static void menu() {
        String option;
        String message = """
                ....::::::   PRODUCTS MENU   ::::::....
                1. Show Purchases.
                2. Create purchase.
                3. Update purchase.
                4. Delete purchase.
                5. Find purchase by ID.
                6. Find all purchases for product.
                7. Exit.
                                
                ENTER THE OPTION TO CONTINUE...
                """;
        do {
            option = JOptionPane.showInputDialog(null, message);
            if (option == null) {
                break;
            }
            switch (option) {
                case "1":
                    showAllPurchases();
                    break;
                case "2":
                    createPurchase();
                    break;
                case "3":
                    updatePurchase();
                    break;
                case "4":
                    deletePurchase();
                    break;
                case "5":
                    findPurchaseById();
                    break;
                case "6":
                    findAllPurchasesByProduct();
                    break;
            }
        } while (!option.equals("7"));
    }

}
