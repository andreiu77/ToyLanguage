package model.value;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean equals(IValue other) {
        return other instanceof StringValue && ((StringValue) other).value.equals(this.value);
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return value;
    }
}
