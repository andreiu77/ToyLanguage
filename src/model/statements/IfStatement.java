package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;
import model.value.IValue;

public class IfStatement implements IStatement {
    private IExpression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        IValue val = expression.eval(state.getSymTable(), state.getHeap());
        if(!val.getType().equals(new BoolType())){
            throw new StatementException("The expression is not a boolean");
        }
        if(((BoolValue) val).getValue()){
            state.getExeStack().push(thenStatement);
        }
        else{
            state.getExeStack().push(elseStatement);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType type = expression.typeCheck(typeEnv);
        if(!type.equals(new BoolType())){
            throw new StatementException("The condition of IF is not a boolean");
        }
        thenStatement.typeCheck(typeEnv);
        elseStatement.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "(IF(" + expression.toString() + ") THEN(" + thenStatement.toString() + ") ELSE(" + elseStatement.toString() + "))\n";
    }
}
