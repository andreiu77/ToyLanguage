package model.expressions;

import exceptions.ADTException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.value.IValue;

public class VariableExpression implements IExpression {
    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException {
        return symTbl.getValue(variable);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) {
        return typeEnv.getValue(variable);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(new String(variable));
    }

    public String toString(){
        return variable;
    }
}