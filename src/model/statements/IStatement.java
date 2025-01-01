package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

public interface IStatement {
    PrgState execute(PrgState state) throws StatementException, ADTException;
    IStatement deepCopy();
    MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException;
}
