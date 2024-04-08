package model;

import database.CRUD;
import database.ConfigDB;
import entity.Client;
import entity.Product;
import entity.Purchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseModel implements CRUD {
    @Override
    public Object insert(Object object) {
        Purchase purchase = (Purchase) object;
        Connection connection = ConfigDB.openConnection();
        String sql = "INSERT INTO purchase (id_clients,id_products,quantity) VALUES (?,?,?);";

        try {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, purchase.getIdClient());
            ps.setInt(2, purchase.getIdProduct());
            ps.setInt(3, purchase.getQuantity());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next()) {
                    purchase.setId(rs.getInt(1));
                    Purchase purchaseFound = (Purchase)findById(purchase.getId());
                    purchase.setCreateAt(purchaseFound.getCreateAt());
                    System.out.println("Insert: purchase inserted successfully");
                }
            }
        } catch (SQLException e) {
            System.out.println("Insert: error in database" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return purchase;
    }

    @Override
    public boolean update(Object object) {
        Purchase purchase = (Purchase) object;
        boolean isUpdated = false;
        Connection connection = ConfigDB.openConnection();
        String sql = "UPDATE purchase SET id_clients = ?,id_products = ?,quantity = ? WHERE id = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, purchase.getIdClient());
            ps.setInt(2, purchase.getIdProduct());
            ps.setInt(3, purchase.getQuantity());
            ps.setInt(4, purchase.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Update: purchase update successfully");
                isUpdated = true;
            }

        } catch (SQLException e) {
            System.out.println("Update: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return isUpdated;
    }

    @Override
    public boolean delete(int id) {
        boolean isDeleted = false;
        Connection connection = ConfigDB.openConnection();
        String sql = "DELETE FROM purchase WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Delete: purchase deleted successfully");
                isDeleted = true;
            }

        } catch (SQLException e) {
            System.out.println("Delete: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return isDeleted;
    }

    @Override
    public List<Object> findAll() {
        List<Object> purchases = new ArrayList<>();
        Connection connection = ConfigDB.openConnection();
        String sql = "SELECT * FROM purchase";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                purchases.add(new Purchase(rs.getInt("id"), rs.getString("created_at"),
                        rs.getInt("quantity"), rs.getInt("id_clients"), rs.getInt("id_products")));
            }

        } catch (SQLException e) {
            System.out.println("FindAll: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return purchases;
    }

    @Override
    public Object findById(int id) {
        Connection connection = ConfigDB.openConnection();
        Purchase purchase = null;
        String sql = "SELECT * FROM purchase WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                purchase = new Purchase(rs.getInt("id"), rs.getString("created_at"),
                        rs.getInt("quantity"), rs.getInt("id_clients"), rs.getInt("id_products"));
            }

        } catch (SQLException e) {
            System.out.println("FindById: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return purchase;
    }

    public List<Object> findAllByProduct(int id_product) {
        Connection connection = ConfigDB.openConnection();
        List<Object> purchases = new ArrayList<>();
        String sql = "select * from purchase" +
                " inner join products on purchase.id_products = products.id" +
                " inner join clients on purchase.id_clients = clients.id" +
                " where purchase.id_products = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_product);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Client client = new Client(rs.getInt("id_clients"), rs.getString("name_client"),
                        rs.getString("last_name"), rs.getString("email"));
                Product product = new Product(rs.getInt("id_products"), rs.getString("name_product"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("id_shop"));
                Purchase purchase = new Purchase(rs.getInt("id"), rs.getString("created_at"),
                        rs.getInt("quantity"), rs.getInt("id_clients"), rs.getInt("id_products"));
                purchase.setProduct(product);
                purchase.setClient(client);
                purchases.add(purchase);
            }

        } catch (SQLException e) {
            System.out.println("FindById: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return purchases;
    }
}
