package model;

import database.CRUD;
import database.ConfigDB;
import entity.Product;
import entity.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel implements CRUD {

    @Override
    public Object insert(Object object) {
        Product product = (Product) object;
        Connection connection = ConfigDB.openConnection();
        String sql = "INSERT INTO products (name_product, price, stock ,id_shop) VALUES (?,?,?,?);";

        try {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setInt(4, product.getIdShop());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next()) {
                    product.setId(rs.getInt(1));
                    System.out.println("Insert: product inserted successfully");
                }
            }
        } catch (SQLException e) {
            System.out.println("Insert: error in database" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return product;
    }

    @Override
    public boolean update(Object object) {
        Product product = (Product) object;
        boolean isUpdated = false;
        Connection connection = ConfigDB.openConnection();
        String sql = "UPDATE products SET name_product = ?,price = ?,stock = ?,id_shop = ? WHERE id = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setInt(4, product.getIdShop());
            ps.setInt(5, product.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Update: product update successfully");
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
        String sql = "DELETE FROM products WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Delete: product deleted successfully");
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
        List<Object> products = new ArrayList<>();
        Connection connection = ConfigDB.openConnection();
        String sql = "SELECT * FROM products";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                products.add(new Product(rs.getInt("id"), rs.getString("name_product"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("id_shop")));
            }

        } catch (SQLException e) {
            System.out.println("FindAll: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return products;
    }

    @Override
    public Object findById(int id) {
        Connection connection = ConfigDB.openConnection();
        Product product = null;
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("name_product"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("id_shop"));
            }

        } catch (SQLException e) {
            System.out.println("FindById: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return product;
    }

    public List<Object> findByName(String name){
        Connection connection = ConfigDB.openConnection();
        List<Object> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name_product like ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%"+name+"%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name_product"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("id_shop"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("FindById: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return products;
    }

    public List<Object> findAllByIdShop(int id) {
        Connection connection = ConfigDB.openConnection();
        List<Object> products = new ArrayList<>();
        String sql = "SELECT * FROM products INNER JOIN shop ON products.id_shop = shop.id WHERE shop.id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name_product"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("id_shop"));
                Shop shop = new Shop(rs.getInt("id"),rs.getString("name"), rs.getString("location"));
                product.setShop(shop);
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("FindById: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return products;
    }
}
