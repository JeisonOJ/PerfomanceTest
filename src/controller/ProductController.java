package controller;

import entity.Product;
import entity.Shop;
import model.ProductModel;
import utils.Utils;

import javax.swing.*;

public class ProductController {

    public static ProductModel instanceModel(){
        return new ProductModel();
    }

    public static String listAllProducts() {
        StringBuilder message = new StringBuilder();
        message.append("......:::::::   All Products   :::::::......\n");
        if (!instanceModel().findAll().isEmpty()) {
            for (Object object : instanceModel().findAll()) {
                Product product = (Product) object;
                message.append(product).append("--------------------------\n");
            }
        }
        return message.toString();
    }

    public static void showAllProducts() {
        JOptionPane.showMessageDialog(null, listAllProducts());
    }

    public static void createProduct() {
        try {
            String name = JOptionPane.showInputDialog(null, "Enter the product name");
            double price = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the product price"));
            int stock = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the product stock"));

            Object[] shops = Utils.listToArray(ShopController.instanceModel().findAll());
            Shop shop = (Shop) JOptionPane.showInputDialog(null,
                    "Add product to this shop",
                    "Shops",
                    JOptionPane.QUESTION_MESSAGE,null,
                    shops,
                    shops[0]);

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setIdShop(shop.getId());
            product.setShop(shop);

            product = (Product) instanceModel().insert(product);

            if (product.getId() != 0) {
                JOptionPane.showMessageDialog(null, product);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data");
        }

    }

    public static void updateProduct() {
        try {
            Object[] products = Utils.listToArray(instanceModel().findAll());
            Product product = (Product) JOptionPane.showInputDialog(null,
                    "Select product to update",
                    "Update",
                    JOptionPane.QUESTION_MESSAGE, null,
                    products,
                    products[0]);
            String name = JOptionPane.showInputDialog(null, "Enter the product name",product.getName());
            double price = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the product price",product.getPrice()));
            int stock = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the product stock",product.getStock()));

            Object[] shops = Utils.listToArray(ShopController.instanceModel().findAll());
            Shop shop = (Shop) JOptionPane.showInputDialog(null,
                    "Add product to this shop",
                    "Shops",
                    JOptionPane.QUESTION_MESSAGE,null,
                    shops,
                    shops[0]);

            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setIdShop(shop.getId());
            product.setShop(shop);

            if (instanceModel().update(product)) {
                JOptionPane.showMessageDialog(null, "Update successful");
            } else {
                JOptionPane.showMessageDialog(null, "Update failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data");
        }
    }

    public static void deleteProduct() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, listAllProducts() + "\nEnter id to delete"));
            if (instanceModel().delete(number)) {
                JOptionPane.showMessageDialog(null, "Delete successful");
            } else {
                JOptionPane.showMessageDialog(null, "Delete failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter a number");
        }
    }

    public static void findProductById() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter id to find a product"));
            Product product = (Product) instanceModel().findById(number);
            if (product != null) {
                JOptionPane.showMessageDialog(null, product);
            } else {
                JOptionPane.showMessageDialog(null, "This product doesn't exist");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter a number");
        }
    }

    public static void findProductByProductName() {
        String name = JOptionPane.showInputDialog(null, "Enter name to find products");
        StringBuilder message = new StringBuilder();
        message.append("......:::::::   All Products   :::::::......\n").append("With Name: ").append(name).append("\n");
        if (!instanceModel().findByName(name).isEmpty()) {
            for (Object object : instanceModel().findByName(name)) {
                Product product = (Product) object;
                message.append(product).append("--------------------------\n");
            }
            JOptionPane.showMessageDialog(null,message);
        }else{
            message.append("there are no Products in this shop");
            JOptionPane.showMessageDialog(null,message);
        }
    }

    public static void findAllProductsForShop() {
        try {
            Object[] shops = Utils.listToArray(ShopController.instanceModel().findAll());
            Shop shop = (Shop) JOptionPane.showInputDialog(null,
                    "Select shop",
                    "Shops",
                    JOptionPane.QUESTION_MESSAGE,null,
                    shops,
                    shops[0]);
            StringBuilder message = new StringBuilder();
            message.append("......:::::::   All Products   :::::::......\n");
            if (!instanceModel().findAllByIdShop(shop.getId()).isEmpty()) {
                for (Object object : instanceModel().findAllByIdShop(shop.getId())) {
                    Product product = (Product) object;
                    message.append(product.getShop())
                            .append(product).append("--------------------------\n");
                }
                JOptionPane.showMessageDialog(null,message);
            }else{
                message.append("there are no Products in this shop");
                JOptionPane.showMessageDialog(null,message);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter a number");
        }
    }

    public static void menu() {
        String option;
        String message = """
                ....::::::   PRODUCTS MENU   ::::::....
                1. Show Products.
                2. Create product.
                3. Update product.
                4. Delete product.
                5. Find product by ID.
                6. Find product by name.
                7. Find all products in one shop.
                8. Exit.
                                
                ENTER THE OPTION TO CONTINUE...
                """;
        do {
            option = JOptionPane.showInputDialog(null, message);
            if (option == null) {
                break;
            }
            switch (option) {
                case "1":
                    showAllProducts();
                    break;
                case "2":
                    createProduct();
                    break;
                case "3":
                    updateProduct();
                    break;
                case "4":
                    deleteProduct();
                    break;
                case "5":
                    findProductById();
                    break;
                case "6":
                    findProductByProductName();
                    break;
                case "7":
                    findAllProductsForShop();
                    break;
            }
        } while (!option.equals("8"));
    }

}
