package enums;

public enum VehicleModel {
    CAR("Car"),
    MOTORCYCLE("Motorcycle"),
    BICYCLE("Bicycle");

    private final String modelName;

    VehicleModel(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }
}
