package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

import java.util.Objects;

public class StringValue implements Value{
    private final String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public StringValue(String value) {
        this.value = value;
    }
    public StringValue(StringValue value) {
        this.value = value.value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

}
