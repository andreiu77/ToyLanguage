package model.expressions;


import exceptions.ADTException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;
import model.value.IValue;

public class LogicalExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    private LogicalOperator operator;

    public LogicalExpression(IExpression left, LogicalOperator operator, IExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue evaluatedExpressionLeft = left.eval(symTbl, heap);
        IValue evaluatedExpressionRight = right.eval(symTbl, heap);
        if (!evaluatedExpressionLeft.getType().equals(new BoolType())) {
            throw new ExpressionException("Left expression is not of type BoolType");
        }
        if (!evaluatedExpressionRight.getType().equals(new BoolType())) {
            throw new ExpressionException("Right expression is not of type BoolType");
        }
        switch (operator) {
            case AND:
                return new BoolValue(((BoolValue) evaluatedExpressionLeft).getValue() &&
                        ((BoolValue) evaluatedExpressionRight).getValue());
            case OR:
                return new BoolValue(((BoolValue) evaluatedExpressionLeft).getValue() ||
                        ((BoolValue) evaluatedExpressionRight).getValue());
            default:
                throw new ExpressionException("Unknown operator");
        }
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type1 = left.typeCheck(typeEnv);
        IType type2 = right.typeCheck(typeEnv);
        if (!type1.equals(new BoolType())) {
            throw new ExpressionException("First operand is not a boolean");
        }
        if (!type2.equals(new BoolType())) {
            throw new ExpressionException("Second operand is not a boolean");
        }
        if (!operator.equals(LogicalOperator.AND) && !operator.equals(LogicalOperator.OR)) {
            throw new ExpressionException("Unknown operator");
        }
        return new BoolType();
    }

    @Override
    public IExpression deepCopy() {
        return new LogicalExpression(left.deepCopy(), operator, right.deepCopy());
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}