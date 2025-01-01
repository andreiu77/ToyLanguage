package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;

public class WhileStatement implements IStatement{
    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        if(!expression.eval(state.getSymTable(), state.getHeap()).getType().equals(new BoolType()))
            throw new StatementException("Expression is not a boolean");
        boolean condition = ((BoolValue) expression.eval(state.getSymTable(), state.getHeap())).getValue();
        if(condition){
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType type = expression.typeCheck(typeEnv);
        if(!type.equals(new BoolType()))
            throw new StatementException("The condition of WHILE is not a boolean");
        statement.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ") " + statement.toString();
    }
}
