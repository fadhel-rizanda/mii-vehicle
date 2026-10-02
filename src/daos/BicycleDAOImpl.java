package daos;

import models.Bicycle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BicycleDAOImpl {
    private final Connection connection;

    public BicycleDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Bicycle> findAll() {
        List<Bicycle> list = new ArrayList<>();
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM Bicycle").executeQuery();
            while (rs.next()) {
                Bicycle Bicycle = new Bicycle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("year"),
                        rs.getObject("owner_id", Integer.class),

                        rs.getInt("total_gear")
                );
                list.add(Bicycle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Bicycle> findByOwnerId(int ownerId) {
        List<Bicycle> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bicycle WHERE owner_id = ?");
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bicycle Bicycle = new Bicycle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("year"),
                        rs.getObject("owner_id", Integer.class),

                        rs.getInt("total_gear")
                );
                list.add(Bicycle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Bicycle findById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bicycle WHERE ID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Bicycle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("year"),
                        rs.getObject("owner_id", Integer.class),

                        rs.getInt("total_gear")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Bicycle Bicycle) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Bicycle(id, brand, model, color, year, owner_id, total_gear) VALUES(?,?,?,?,?,?,?)");
            ps.setInt(1, Bicycle.getId());
            ps.setString(2, Bicycle.getBrand());
            ps.setString(3, Bicycle.getModel());
            ps.setString(4, Bicycle.getColor());
            ps.setInt(5, Bicycle.getYear());
            ps.setObject(6, Bicycle.getOwnerId(), java.sql.Types.INTEGER);
            ps.setInt(7, Bicycle.getTotalGears());
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Bicycle Bicycle) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Bicycle SET brand = ?, model = ?, color = ?, year = ?, owner_id = ?, total_gear = ? WHERE id = ?");
            ps.setString(1, Bicycle.getBrand());
            ps.setString(2, Bicycle.getModel());
            ps.setString(3, Bicycle.getColor());
            ps.setInt(4, Bicycle.getYear());
            ps.setObject(5, Bicycle.getOwnerId(), java.sql.Types.INTEGER);
            ps.setInt(6, Bicycle.getTotalGears());
            ps.setInt(7, Bicycle.getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete(Bicycle Bicycle) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Bicycle WHERE id = ?");
            ps.setInt(1, Bicycle.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
