package controller;

import entity.Client;
import model.ClientModel;
import utils.Utils;

import javax.swing.*;

public class ClientController {

    public static ClientModel instanceModel(){
        return new ClientModel();
    }

    public static String listAllClients() {
        StringBuilder message = new StringBuilder();
        message.append("......:::::::   All Clients   :::::::......\n");
        if (!instanceModel().findAll().isEmpty()) {
            for (Object object : instanceModel().findAll()) {
                Client client = (Client) object;
                message.append(client).append("\n--------------------------\n");
            }
        }
        return message.toString();
    }

    public static void showAllClients() {
        JOptionPane.showMessageDialog(null, listAllClients());
    }

    public static void createClient() {
        try {
            String name = JOptionPane.showInputDialog(null, "Enter the client name");
            String lastName = JOptionPane.showInputDialog(null, "Enter the client last name");
            String email = JOptionPane.showInputDialog(null, "Enter the client email");

            Client client = new Client();
            client.setName(name);
            client.setLastName(lastName);
            client.setEmail(email);

            client = (Client) instanceModel().insert(client);

            if (client.getId() != 0) {
                JOptionPane.showMessageDialog(null, client);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data");
        }

    }

    public static void updateClient() {
        try {
            Object[] clients = Utils.listToArray(instanceModel().findAll());
            Client client = (Client) JOptionPane.showInputDialog(null,
                    "Select client to update",
                    "Update",
                    JOptionPane.QUESTION_MESSAGE, null,
                    clients,
                    clients[0]);
            String name = JOptionPane.showInputDialog(null, "Enter the client name",client.getName());
            String lastName = JOptionPane.showInputDialog(null, "Enter the client last name",client.getLastName());
            String email = JOptionPane.showInputDialog(null, "Enter the client email",client.getEmail());

            client.setName(name);
            client.setLastName(lastName);
            client.setEmail(email);

            if (instanceModel().update(client)) {
                JOptionPane.showMessageDialog(null, "Update successful");
            } else {
                JOptionPane.showMessageDialog(null, "Update failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid data");
        }
    }

    public static void deleteClient() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, listAllClients() + "\nEnter id to delete"));
            if (instanceModel().delete(number)) {
                JOptionPane.showMessageDialog(null, "Delete successful");
            } else {
                JOptionPane.showMessageDialog(null, "Delete failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter a number");
        }
    }

    public static void findClientById() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter id to find a client"));
            Client client = (Client) instanceModel().findById(number);
            if (client != null) {
                JOptionPane.showMessageDialog(null, client);
            } else {
                JOptionPane.showMessageDialog(null, "This client doesn't exist");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter a number");
        }
    }

    public static void findClientByName() {
        String name = JOptionPane.showInputDialog(null, "Enter name to find clients");
        StringBuilder message = new StringBuilder();
        message.append("......:::::::   All Clients   :::::::......\n").append("With Name: ").append(name).append("\n");
        if (!instanceModel().findByName(name).isEmpty()) {
            for (Object object : instanceModel().findByName(name)) {
                Client client = (Client) object;
                message.append(client).append("--------------------------\n");
            }
            JOptionPane.showMessageDialog(null,message);
        }else{
            message.append("there are no clients in this shop");
            JOptionPane.showMessageDialog(null,message);
        }
    }

    public static void menu() {
        String option;
        String message = """
                ....::::::   PRODUCTS MENU   ::::::....
                1. Show clients.
                2. Create client.
                3. Update client.
                4. Delete client.
                5. Find client by ID.
                6. Find client by name.
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
                    showAllClients();
                    break;
                case "2":
                    createClient();
                    break;
                case "3":
                    updateClient();
                    break;
                case "4":
                    deleteClient();
                    break;
                case "5":
                    findClientById();
                    break;
                case "6":
                    findClientByName();
                    break;
            }
        } while (!option.equals("7"));
    }

}
