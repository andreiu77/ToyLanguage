package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private IExpression expression;
    private String varName;

    public ReadFileStatement(IExpression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        MyIDictionary<String, IValue> table = state.getSymTable();

        if(!table.contains(varName)){
            throw new StatementException("Variable '" + varName + "' does not exist");
        }

        if(!table.getValue(varName).getType().equals(new IntType())){
            throw new StatementException("Variable '" + varName + "' is not a number");
        }

        IValue value = expression.eval(table, state.getHeap());

        if(!value.getType().equals(new StringType())){
            throw new StatementException("Result is not a string");
        }

        BufferedReader reader = state.getFileTable().getValue((StringValue) value);
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new StatementException("Error reading file");
        }
        if (line.equals("")) {
            line = "0";
        }

        int parser = Integer.parseInt(line);

        table.insert(varName, new IntValue(parser));

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        if(!expression.typeCheck(typeEnv).equals(new StringType())){
            throw new StatementException("Expression is not a string");
        }
        if(!typeEnv.getValue(varName).equals(new IntType())){
            throw new StatementException("Variable '" + varName + "' is not a number");
        }
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), new String(varName));
    }

    @Override
    public String toString() {
        return "readFile(" + expression + ", " + varName + ")";
    }
}
