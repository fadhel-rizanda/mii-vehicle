package daos;

import enums.Gender;
import models.Passenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * DAO Class untuk entitas Passenger.
 * Class ini menyediakan operasi CRUD ke tabel Passenger yang ada di database.
 * @author Fadhel Rizanda
 */
public class PassengersDAOImpl implements PassengersDAO {
    private final Connection connection;

    public PassengersDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Passenger> findAll() {
        List<Passenger> passengers = new ArrayList<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("select * from passenger");
            while (rs.next()) {
                Passenger passenger = new Passenger(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        parseGender(rs.getString("gender")),
                        rs.getInt("vehicle_id"),
                        rs.getString("vehicle_model")
                );

                passenger.setId(rs.getInt("id"));
                passenger.setName(rs.getString("name"));
                passenger.setAge(rs.getInt("age"));
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    @Override
    public Passenger findById(int id) {
        Passenger passenger = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from passenger where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                passenger = new Passenger(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        parseGender(rs.getString("gender")),
                        rs.getInt("vehicle_id"),
                        rs.getString("vehicle_model")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passenger;
    }

    @Override
    public List<Passenger> findAllByGenericId(int id, String model) {
        List<Passenger> passengers = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from passenger where vehicle_id = ? and vehicle_model = ?");
            ps.setInt(1, id);
            ps.setString(2, model);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Passenger passenger = new Passenger(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        parseGender(rs.getString("gender")),
                        rs.getInt("vehicle_id"),
                        rs.getString("vehicle_model")
                );

                passenger.setId(rs.getInt("id"));
                passenger.setName(rs.getString("name"));
                passenger.setAge(rs.getInt("age"));
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    @Override
    public void insert(Passenger passenger) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO PASSENGER(id, name, age, gender, vehicle_id, vehicle_model) VALUES(?,?,?,?,?,?)");
            ps.setInt(1, passenger.getId());
            ps.setString(2, passenger.getName());
            ps.setInt(3, passenger.getAge());
            ps.setString(4, passenger.getGender().name());
            if (passenger.getVehicleId() != null) {
                ps.setInt(5, passenger.getVehicleId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            if (passenger.getVehicleModel() != null) {
                ps.setString(6, passenger.getVehicleModel());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Passenger passenger) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PASSENGER SET name = ?, age = ?, gender = ?, vehicle_id = ?, vehicle_model = ? WHERE id = ?");
            ps.setString(1, passenger.getName());
            ps.setInt(2, passenger.getAge());
            ps.setString(3, passenger.getGender().name());
            if (passenger.getVehicleId() != null) {
                ps.setInt(4, passenger.getVehicleId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            if (passenger.getVehicleModel() != null) {
                ps.setString(5, passenger.getVehicleModel());
            } else {
                ps.setNull(5, java.sql.Types.VARCHAR);
            }
            ps.setInt(6, passenger.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM PASSENGER WHERE id = ?");
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Gender parseGender(String gender) {
        return Gender.valueOf(gender.trim().toUpperCase(Locale.ROOT));
    }
}
