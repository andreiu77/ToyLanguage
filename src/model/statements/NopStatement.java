package model.statements;

import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

public class NopStatement implements IStatement {

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        return null;
    }

    public String toString() {
        return "NopStatement";
    }
}
