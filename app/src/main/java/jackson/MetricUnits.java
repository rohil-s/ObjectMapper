package jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MetricUnits {
    KILOMETERS("kilometer", "std"),
    METER("meter", "std"),
    MILE("mile", "nonstd");

    private String unit;
    private String system;

    private MetricUnits(String unit, String system) {
        this.unit = unit;
        this.system = system;
    }

    @JsonValue
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

}
