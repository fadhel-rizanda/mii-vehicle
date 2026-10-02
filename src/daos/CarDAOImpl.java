package daos;

import models.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO Class untuk entitas Car.
 * Class ini menyediakan operasi CRUD ke tabel Car yang ada di database.
 * @author Fadhel Rizanda
 */
public class CarDAOImpl implements CarDAO {
    private final Connection connection;

    public CarDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Mengambil semua data Car dari database.
     * @return List<Car> - daftar semua mobil yang ada di database.
     */
    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM CAR").executeQuery();
            while (rs.next()) {
                cars.add(mapCar(rs));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    /**
     * Mengambil data Car berdasarkan IDnya.
     * @param id - ID mobil yang ingin dicari.
     * @return Car - objek mobil yang ditemukan, atau null jika tidak ditemukan.
     */
    @Override
    public Car findById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM CAR WHERE ID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapCar(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil data Car berdasarkan ID pemiliknya.
     * @param ownerId - ID pemilik mobil yang ingin dicari.
     * @return List<Car> - daftar mobil yang dimiliki oleh pemilik tersebut.
     */
    @Override
    public List<Car> findByOwnerId(int ownerId) {
        List<Car> cars = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM CAR WHERE OWNER_ID = ?");
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cars.add(mapCar(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    /**
     * Menambahkan data Car baru ke database.
     * @param car - objek mobil yang ingin ditambahkan.
     * @return boolean - true jika berhasil ditambahkan, false jika gagal.
     */
    @Override
    public boolean insert(Car car) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO CAR (id, brand, model, color, year, owner_id, transmission, tank_capacity_in_l, fuel_consumption_in_kpl, total_door, total_seat, current_fuel_in_l, is_engine_on, total_km, current_speed_in_kph, is_doors_locked, last_speed_change_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, car.getId());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setString(4, car.getColor());
            ps.setInt(5, car.getYear());
            ps.setObject(6, car.getOwnerId(), java.sql.Types.INTEGER);

            ps.setString(7, car.getTransmission());
            ps.setDouble(8, car.getTankCapacityInL());
            ps.setDouble(9, car.getFuelConsumptionInKpl());
            ps.setInt(10, car.getTotalDoors());
            ps.setInt(11, car.getTotalSeat());
            ps.setDouble(12, car.getCurrentFuelInL());
            ps.setBoolean(13, car.isEngineOn());
            ps.setDouble(14, car.getTotalKm());
            ps.setDouble(15, car.getCurrentSpeedInKph());
            ps.setBoolean(16, car.isDoorsLocked());
            ps.setTimestamp(17, car.getLastSpeedChangeAt() == null
                    ? null
                    : Timestamp.valueOf(car.getLastSpeedChangeAt()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mengupdate data Car di database.
     * @param car - objek mobil yang ingin diupdate.
     * @return boolean - true jika berhasil diupdate, false jika gagal.
     */
    @Override
    public boolean update(Car car) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE CAR SET brand = ?, model = ?, color = ?, year = ?, owner_id = ?, transmission = ?, tank_capacity_in_l = ?, fuel_consumption_in_kpl = ?, total_door = ?, total_seat = ?, last_speed_change_at = ?, current_fuel_in_l = ?, is_engine_on = ?, total_km = ?, current_speed_in_kph = ?, is_doors_locked = ? WHERE id = ?");
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getColor());
            ps.setInt(4, car.getYear());
            ps.setObject(5, car.getOwnerId(), java.sql.Types.INTEGER);

            ps.setString(6, car.getTransmission());
            ps.setDouble(7, car.getTankCapacityInL());
            ps.setDouble(8, car.getFuelConsumptionInKpl());
            ps.setInt(9, car.getTotalDoors());
            ps.setInt(10, car.getTotalSeat());
            ps.setTimestamp(11, car.getLastSpeedChangeAt() == null
                    ? null
                    : Timestamp.valueOf(car.getLastSpeedChangeAt()));
            ps.setDouble(12, car.getCurrentFuelInL());
            ps.setBoolean(13, car.isEngineOn());
            ps.setDouble(14, car.getTotalKm());
            ps.setDouble(15, car.getCurrentSpeedInKph());
            ps.setBoolean(16, car.isDoorsLocked());
            ps.setInt(17, car.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Car mapCar(ResultSet rs) throws SQLException {
        Car car = new Car(
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
                rs.getInt("total_door"),
                rs.getInt("total_seat"),
                rs.getBoolean("is_doors_locked"),
                new ArrayList<>()
        );
        car.setTotalKm(rs.getDouble("total_km"));
        car.setCurrentSpeedInKph(rs.getDouble("current_speed_in_kph"));
        Timestamp timestamp = rs.getTimestamp("last_speed_change_at");
        if (timestamp!= null){
            car.setLastSpeedChangeAt(
                    timestamp.toLocalDateTime()
            );
        }
        return car;
    }

    /**
     * Menghapus data Car dari database berdasarkan IDnya.
     * @param id - ID mobil yang ingin dihapus.
     */
    @Override
    public void delete(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM CAR WHERE id = ?");
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
