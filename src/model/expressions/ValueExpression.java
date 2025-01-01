package model.expressions;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.value.IValue;

public class ValueExpression implements IExpression {
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) {
        return this.value;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) {
        return this.value.getType();
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    public String toString() {
        return this.value.toString();
    }
}
