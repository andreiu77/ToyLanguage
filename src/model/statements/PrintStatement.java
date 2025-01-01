package model.statements;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.expressions.IExpression;
import model.types.IType;
import model.value.IValue;

public class PrintStatement implements IStatement{
    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws ExpressionException {
        IValue result = this.expression.eval(prgState.getSymTable(), prgState.getHeap());
        prgState.getOutput().add(result.toString());
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        this.expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    public String toString(){
        return "print(" + expression.toString() + ")";
    }
}