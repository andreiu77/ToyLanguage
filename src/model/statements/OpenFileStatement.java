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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class OpenFileStatement implements IStatement {
    private IExpression expression;

    public OpenFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IValue value = expression.eval(state.getSymTable(), state.getHeap());
        if(!value.getType().equals(new StringType()))
            throw new StatementException("The expression does not match the type of a file");
        if(fileTable.contains((StringValue) value))
            throw new StatementException("The file already exists");
        BufferedReader file;
        try {
            file = new BufferedReader(new FileReader(((StringValue) value).getValue()));
        } catch (FileNotFoundException e) {
            throw new StatementException("The file does not exist");
        }
        fileTable.insert((StringValue) value, file);
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
        return new OpenFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "openFile(" + expression.toString() + ")";
    }
}
