package model.value;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean equals(IValue other) {
        return other instanceof IntValue && ((IntValue) other).value == this.value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public IType getType() {
        return new IntType();
    }
}
