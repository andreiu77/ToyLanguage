package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.RefType;
import model.value.IValue;
import model.value.RefValue;

public class ReadHeapExpression implements IExpression{
    private IExpression expression;

    public ReadHeapExpression(IExpression expression){
        this.expression = expression;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        if(!(expression.eval(symTbl, heap).getType() instanceof RefType))
            throw new ExpressionException("Expression is not a reference type");
        IValue value = expression.eval(symTbl, heap);
        return heap.getValue(((RefValue) value).getAddress());
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type = expression.typeCheck(typeEnv);
        if(!(type instanceof RefType))
            throw new ExpressionException("Expression is not a reference type");
        return ((RefType) type).getInner();
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "ReadHeap(" + expression + ")";
    }
}
