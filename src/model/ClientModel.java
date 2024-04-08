package model;

import database.CRUD;
import database.ConfigDB;
import entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientModel implements CRUD {
    @Override
    public Object insert(Object object) {
        Client client = (Client) object;
        Connection connection = ConfigDB.openConnection();
        String sql = "INSERT INTO clients (name_client, last_name, email) VALUES (?,?,?);";

        try {
            PreparedStatement ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,client.getName());
            ps.setString(2,client.getLastName());
            ps.setString(3,client.getEmail());
            int rows = ps.executeUpdate();
            if(rows>0){
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next()){
                    client.setId(rs.getInt(1));
                    System.out.println("Insert: client inserted successfully");
                }
            }
        }catch (SQLException e){
            System.out.println("Insert: error in database"+e.getMessage());
        }finally {
            ConfigDB.closeConnection();
        }
        return client;
    }

    @Override
    public boolean update(Object object) {
        Client client = (Client) object;
        boolean isUpdated = false;
        Connection connection = ConfigDB.openConnection();
        String sql = "UPDATE clients SET name_client = ?,last_name = ?,email = ? WHERE id = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, client.getName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.setInt(4, client.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Update: client update successfully");
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
        String sql = "DELETE FROM clients WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Delete: client deleted successfully");
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
        List<Object> clients = new ArrayList<>();
        Connection connection = ConfigDB.openConnection();
        String sql = "SELECT * FROM clients";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                clients.add(new Client(rs.getInt("id"),rs.getString("name_client"), rs.getString("last_name"),rs.getString("email")));
            }

        } catch (SQLException e) {
            System.out.println("FindAll: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return clients;
    }

    @Override
    public Object findById(int id) {
        Connection connection = ConfigDB.openConnection();
        Client client = null;
        String sql = "SELECT * FROM clients WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                client = new Client(rs.getInt("id"),rs.getString("name_client"), rs.getString("last_name"),rs.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println("FindById: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return client;
    }

    public List<Object> findByName(String name){
        Connection connection = ConfigDB.openConnection();
        List<Object> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE name_client like ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%"+name+"%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Client client = new Client(rs.getInt("id"),rs.getString("name_client"), rs.getString("last_name"),rs.getString("email"));
                clients.add(client);
            }

        } catch (SQLException e) {
            System.out.println("FindById: error in database\n" + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return clients;
    }
}
