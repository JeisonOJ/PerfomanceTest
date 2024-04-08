package model;

import database.CRUD;
import database.ConfigDB;
import entity.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopModel implements CRUD {


    @Override
    public Object insert(Object object) {
        Shop shop = (Shop) object;
        Connection connection = ConfigDB.openConnection();
        String sql = "INSERT INTO shop (name, location) VALUES (?,?);";

        try {
            PreparedStatement ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,shop.getName());
            ps.setString(2,shop.getLocation());
            int rows = ps.executeUpdate();
            if(rows>0){
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next()){
                    shop.setId(rs.getInt(1));
                    System.out.println("Insert: shop inserted successfully");
                }
            }
        }catch (SQLException e){
            System.out.println("Insert: error in database"+e.getMessage());
        }finally {
            ConfigDB.closeConnection();
        }
        return shop;
    }

    @Override
    public boolean update(Object object) {
        Shop shop = (Shop) object;
        boolean isUpdated = false;
        Connection connection = ConfigDB.openConnection();
        String sql = "UPDATE shop SET name = ?,location = ? WHERE id = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, shop.getName());
            ps.setString(2, shop.getLocation());
            ps.setInt(3, shop.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Update: shop update successfully");
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
        String sql = "DELETE FROM shop WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Delete: shop deleted successfully");
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
        List<Object> shops = new ArrayList<>();
        Connection connection = ConfigDB.openConnection();
        String sql = "SELECT * FROM shop";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                shops.add(new Shop(rs.getInt("id"),rs.getString("name"), rs.getString("location")));
            }

        } catch (SQLException e) {
            System.out.println("FindAll: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return shops;
    }

    @Override
    public Object findById(int id) {
        Connection connection = ConfigDB.openConnection();
        Shop shop = null;
        String sql = "SELECT * FROM shop WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                shop = new Shop(rs.getInt("id"),rs.getString("name"), rs.getString("location"));
            }

        } catch (SQLException e) {
            System.out.println("FindById: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return shop;
    }
}
