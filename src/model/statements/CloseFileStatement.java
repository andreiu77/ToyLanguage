package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements IStatement {
    private IExpression expression;

    public CloseFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IValue value = expression.eval(state.getSymTable(), state.getHeap());
        if(!value.getType().equals(new StringType()))
            throw new StatementException("The expression is not a string");
        BufferedReader file = fileTable.getValue((StringValue) value);
        try {
            file.close();
        } catch (IOException e) {
            throw new StatementException("Could not close the file");
        }
        fileTable.remove((StringValue) value);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        if(!expression.typeCheck(typeEnv).equals(new StringType()))
            throw new StatementException("The expression is not a string");
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeFile(" + expression + ")";
    }
}
