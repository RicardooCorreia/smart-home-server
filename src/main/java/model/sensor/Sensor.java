package model.sensor;

import com.google.common.base.MoreObjects;
import model.EntityModel;

import java.util.UUID;

public class Sensor implements EntityModel {
    public static final String KEY_PATH = "key";
    public static final String HOUSE_KEY_PATH = "houseId";
    public static final String SOURCE_KEY_PATH = "sourceId";
    public static final String NAME_PATH = "name";
    public static final String ON_PATH = "on";

    private String id;
    private String name;
    private String houseId;
    private String sourceId;
    private boolean on;

    public Sensor(String id, String name, String houseId, String sourceId, boolean on) {
        this.id = id;
        this.name = name;
        this.houseId = houseId;
        this.sourceId = sourceId;
        this.on = on;
    }

    public Sensor() {
    }

    public String getKey() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public SensorTypeEnum getType() {
        return SensorTypeEnum.SIMPLE;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("houseId", houseId)
                .add("sourceId", sourceId)
                .add("on", on)
                .toString();
    }

    public static class Builder {
        private Sensor sensorUnderConstruction;

        public Builder() {
            sensorUnderConstruction = new Sensor();
        }

        public static Builder aSensor() {
            return new Builder();
        }

        public Builder withValue(boolean value) {
            sensorUnderConstruction.setOn(value);
            return this;
        }

        public Builder withId(String id) {
            sensorUnderConstruction.setId(id);
            return this;
        }

        public Builder withNewId() {
            sensorUnderConstruction.setId(UUID.randomUUID().toString());
            return this;
        }

        public Builder withName(String name) {
            sensorUnderConstruction.setName(name);
            return this;
        }

        public Builder withHouseId(String houseId) {
            sensorUnderConstruction.setHouseId(houseId);
            return this;
        }

        public Builder withSourceId(String sourceId) {
            sensorUnderConstruction.setSourceId(sourceId);
            return this;
        }

        public Sensor build() {
            return sensorUnderConstruction;
        }
    }
}

