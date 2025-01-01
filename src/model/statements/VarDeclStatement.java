package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;


public class VarDeclStatement implements IStatement {
    private String varName;
    private IType type;

    public VarDeclStatement(String varName, IType type) {
        this.varName = varName;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        if(state.getSymTable().contains(varName)) {
            throw new StatementException("Variable " + varName + " already exists.");
        }
        state.getSymTable().insert(varName, type.defaultValue());
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        typeEnv.insert(varName, type);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStatement(varName, type);
    }

    @Override
    public String toString() {
        return varName + " = " + type.toString();
    }
}
