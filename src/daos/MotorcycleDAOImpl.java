package daos;

import models.Motorcycle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MotorcycleDAOImpl {
    private final Connection connection;

    public MotorcycleDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Motorcycle> findAll() {
        List<Motorcycle> list = new ArrayList<>();
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM MOTORCYCLE").executeQuery();
            while (rs.next()) {
                Motorcycle motorcycle = new Motorcycle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("year"),
                        rs.getObject("owner_id", Integer.class),

                        rs.getString("transmission"),
                        rs.getDouble("tank_capacity_in_l"),
                        rs.getBoolean("is_engine_on"),
                        rs.getDouble("current_fuel_in_l"),
                        rs.getDouble("fuel_consumption_in_kpl"),

                        rs.getBoolean("is_kickstand_down"),
                        null
                        );
                list.add(motorcycle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Motorcycle> findByOwnerId(int ownerId) {
        List<Motorcycle> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM MOTORCYCLE WHERE owner_id = ?");
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Motorcycle motorcycle = new Motorcycle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("year"),
                        rs.getObject("owner_id", Integer.class),

                        rs.getString("transmission"),
                        rs.getDouble("tank_capacity_in_l"),
                        rs.getBoolean("is_engine_on"),
                        rs.getDouble("current_fuel_in_l"),
                        rs.getDouble("fuel_consumption_in_kpl"),

                        rs.getBoolean("is_kickstand_down"),
                        null
                        );
                list.add(motorcycle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Motorcycle findById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM MOTORCYCLE WHERE ID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Motorcycle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("year"),
                        rs.getObject("owner_id", Integer.class),

                        rs.getString("transmission"),
                        rs.getDouble("tank_capacity_in_l"),
                        rs.getBoolean("is_engine_on"),
                        rs.getDouble("current_fuel_in_l"),
                        rs.getDouble("fuel_consumption_in_kpl"),

                        rs.getBoolean("is_kickstand_down"),
                        null
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Motorcycle motorcycle) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO MOTORCYCLE(id, brand, model, color, year, owner_id, transmission, tank_capacity_in_l, is_engine_on, current_fuel_in_l, fuel_consumption_in_kpl, is_kickstand_down) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, motorcycle.getId());
            ps.setString(2, motorcycle.getBrand());
            ps.setString(3, motorcycle.getModel());
            ps.setString(4, motorcycle.getColor());
            ps.setInt(5, motorcycle.getYear());
            ps.setObject(6, motorcycle.getOwnerId(), java.sql.Types.INTEGER);
            ps.setString(7, motorcycle.getTransmission());
            ps.setDouble(8, motorcycle.getTankCapacityInL());
            ps.setBoolean(9, motorcycle.isEngineOn());
            ps.setDouble(10, motorcycle.getCurrentFuelInL());
            ps.setDouble(11, motorcycle.getFuelConsumptionInKpl());
            ps.setBoolean(12, motorcycle.isKickstandDown());
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Motorcycle motorcycle) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE MOTORCYCLE SET brand = ?, model = ?, color = ?, year = ?, owner_id = ?, transmission = ?, tank_capacity_in_l = ?, is_engine_on = ?, current_fuel_in_l = ?, fuel_consumption_in_kpl = ?, is_kickstand_down = ? WHERE id = ?");
            ps.setString(1, motorcycle.getBrand());
            ps.setString(2, motorcycle.getModel());
            ps.setString(3, motorcycle.getColor());
            ps.setInt(4, motorcycle.getYear());
            ps.setObject(5, motorcycle.getOwnerId(), java.sql.Types.INTEGER);
            ps.setString(6, motorcycle.getTransmission());
            ps.setDouble(7, motorcycle.getTankCapacityInL());
            ps.setBoolean(8, motorcycle.isEngineOn());
            ps.setDouble(9, motorcycle.getCurrentFuelInL());
            ps.setDouble(10, motorcycle.getFuelConsumptionInKpl());
            ps.setBoolean(11, motorcycle.isKickstandDown());
            ps.setInt(12, motorcycle.getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete(Motorcycle motorcycle) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM MOTORCYCLE WHERE id = ?");
            ps.setInt(1, motorcycle.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
