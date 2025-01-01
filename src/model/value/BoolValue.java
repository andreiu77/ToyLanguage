package model.value;
import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue {
    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean equals(IValue other) {
        return other instanceof BoolValue && ((BoolValue) other).value == this.value;
    }

    public boolean getValue() {
        return value;
    }

    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public IType getType() {
        return new BoolType();
    }
}
