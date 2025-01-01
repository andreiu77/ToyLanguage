package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

public class ForkStatement implements IStatement{
    private IStatement statement;

    public ForkStatement(IStatement st){
        statement = st;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        return new PrgState(state.getSymTable().deepCopy(), state.getExeStack(), state.getOutput(), statement, state.getFileTable(), state.getHeap());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString(){
        return "fork(" + statement.toString() + ")";
    }
}
