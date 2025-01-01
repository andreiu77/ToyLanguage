package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.IntType;
import model.value.IValue;
import model.value.IntValue;

public class ArithmeticalExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    private ArithmeticalOperator operator;

    public ArithmeticalExpression(IExpression left, ArithmeticalOperator operator, IExpression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue value1 = left.eval(symTbl, heap);
        IValue value2 = right.eval(symTbl, heap);
        if (!value1.getType().equals(new IntType())) {
            throw new ExpressionException("First value is not int");
        }
        if (!value2.getType().equals(new IntType())) {
            throw new ExpressionException("Second value is not int");
        }

        IntValue int1 = (IntValue) value1;
        IntValue int2 = (IntValue) value2;

        switch (operator) {
            case ADD:
                return new IntValue(int1.getValue() + int2.getValue());
            case SUBTRACT:
                return new IntValue(int1.getValue() - int2.getValue());
            case MULTIPLY:
                return new IntValue(int1.getValue() * int2.getValue());
            case DIVIDE:
                if (int2.getValue() == 0)
                    throw new ExpressionException("Divide by zero");
                return new IntValue(int1.getValue() / int2.getValue());
            default:
                throw new ExpressionException("Unknown operator");
        }
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type1 = left.typeCheck(typeEnv);
        IType type2 = right.typeCheck(typeEnv);
        if (!type1.equals(new IntType())) {
            throw new ExpressionException("First operand is not an integer");
        }
        if (!type2.equals(new IntType())) {
            throw new ExpressionException("Second operand is not an integer");
        }
        return new IntType();
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticalExpression(left.deepCopy(), operator, right.deepCopy());
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}