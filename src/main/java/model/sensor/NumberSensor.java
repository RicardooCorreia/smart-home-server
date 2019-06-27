package model.sensor;

import com.google.common.base.MoreObjects;

import java.util.UUID;

public class NumberSensor extends Sensor {
    public static final String VALUE_PATH = "value";

    private float value;

    private NumberSensor() {
        super();
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public SensorTypeEnum getType() {
        return SensorTypeEnum.NUMBER;
    }

    @Override
    public String toString() {
        return super.toString() +
                MoreObjects.toStringHelper(this)
                        .add("value", value)
                        .toString(); // todo: fix this
    }

    public static class Builder {
        private NumberSensor sensorUnderConstruction;

        public Builder() {
            this.sensorUnderConstruction = new NumberSensor();
        }

        public static Builder aSensor() {
            return new Builder();
        }

        public Builder withNumberValue(float value) {
            sensorUnderConstruction.setValue(value);
            return this;
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

        public NumberSensor build() {
            return sensorUnderConstruction;
        }
    }
}
