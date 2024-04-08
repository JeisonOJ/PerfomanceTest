package controller;

import entity.Shop;
import model.ShopModel;
import utils.Utils;

import javax.swing.*;

public class ShopController {

    public static ShopModel instanceModel() {
        return new ShopModel();
    }

    public static String listAllShops() {
        StringBuilder message = new StringBuilder();
        message.append("......:::::::   All Shops   :::::::......\n");
        if (!instanceModel().findAll().isEmpty()) {
            for (Object object : instanceModel().findAll()) {
                Shop shop = (Shop) object;
                message.append(shop).append("\n--------------------------\n");
            }
        }
        return message.toString();
    }

    public static void showAllShops() {
        JOptionPane.showMessageDialog(null, listAllShops());
    }

    /**
     * Create shop in database
     */
    public static void createShop() {
        try {
            String name = JOptionPane.showInputDialog(null, "Enter the shop name");
            String location = JOptionPane.showInputDialog(null, "Enter the shop location");

            Shop shop = new Shop();
            shop.setName(name);
            shop.setLocation(location);

            shop = (Shop) instanceModel().insert(shop);
            if (shop.getId() != 0) {
                JOptionPane.showMessageDialog(null, shop);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data");
        }

    }

    public static void updateShop() {
        try {
//
            Object[] shops = Utils.listToArray(instanceModel().findAll());
            Shop shop = (Shop) JOptionPane.showInputDialog(null,
                    "Select shop to update",
                    "Update",
                    JOptionPane.QUESTION_MESSAGE, null,
                    shops,
                    shops[0]);
            String name = JOptionPane.showInputDialog(null, "Enter the shop name", shop.getName());
            String location = JOptionPane.showInputDialog(null, "Enter the shop location", shop.getLocation());

            shop.setName(name);
            shop.setLocation(location);

            if (instanceModel().update(shop)) {
                JOptionPane.showMessageDialog(null, "Update successful");
            } else {
                JOptionPane.showMessageDialog(null, "Update failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data");
        }
    }

    public static void deleteShop() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, listAllShops() + "\nEnter id to delete"));
            if (instanceModel().delete(number)) {
                JOptionPane.showMessageDialog(null, "Delete successful");
            } else {
                JOptionPane.showMessageDialog(null, "Delete failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter a number");
        }
    }

    public static void findShopById() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter id to find a shop"));
            Shop shop = (Shop) instanceModel().findById(number);
            if (shop != null) {
                JOptionPane.showMessageDialog(null, shop);
            } else {
                JOptionPane.showMessageDialog(null, "This shop doesn't exist");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter a number");
        }
    }

    public static void menu() {
        String option;
        String message = """
                ....::::::   SHOPS MENU   ::::::....
                1. Show shops.
                2. Create shop.
                3. Update shop.
                4. Delete shop.
                5. Find shop.
                6. Exit.
                                
                ENTER THE OPTION TO CONTINUE...
                """;
        do {
            option = JOptionPane.showInputDialog(null, message);
            if (option == null) {
                break;
            }
            switch (option) {
                case "1":
                    showAllShops();
                    break;
                case "2":
                    createShop();
                    break;
                case "3":
                    updateShop();
                    break;
                case "4":
                    deleteShop();
                    break;
                case "5":
                    findShopById();
                    break;
            }
        } while (!option.equals("6"));
    }

}
