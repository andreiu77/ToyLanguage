package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class RelationalExpression implements IExpression{
    private IExpression left;
    private IExpression right;
    private RelationalOperator operator;

    public RelationalExpression(IExpression left, RelationalOperator operator, IExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue leftVal = left.eval(symTbl, heap);
        IValue rightVal = right.eval(symTbl, heap);

        if(!leftVal.getType().equals(new IntType()))
            throw new ExpressionException(leftVal.getType() + " is not an integer");
        if(!rightVal.getType().equals(new IntType()))
            throw new ExpressionException(rightVal.getType() + " is not an integer");

        IntValue int1 = (IntValue) leftVal;
        IntValue int2 = (IntValue) rightVal;

        switch (operator) {
            case LESS:
                return new BoolValue(int1.getValue() < int2.getValue());
            case LESS_OR_EQUAL:
                return new BoolValue(int1.getValue() <= int2.getValue());
            case EQUAL:
                return new BoolValue(int1.getValue() == int2.getValue());
            case NOT_EQUAL:
                return new BoolValue(int1.getValue() != int2.getValue());
            case GREATER:
                return new BoolValue(int1.getValue() > int2.getValue());
            case GREATER_OR_EQUAL:
                return new BoolValue(int1.getValue() >= int2.getValue());
            default:
                throw new ExpressionException(operator + " is not a valid operator");
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
        if(!operator.equals(RelationalOperator.LESS) && !operator.equals(RelationalOperator.LESS_OR_EQUAL) &&
                !operator.equals(RelationalOperator.EQUAL) && !operator.equals(RelationalOperator.NOT_EQUAL) &&
                !operator.equals(RelationalOperator.GREATER) && !operator.equals(RelationalOperator.GREATER_OR_EQUAL))
            throw new ExpressionException("Unknown operator");
        return new BoolType();
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(left.deepCopy(), operator, right.deepCopy());
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}


