package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.RefType;
import model.value.IValue;
import model.value.RefValue;

public class WriteHeapStatement implements IStatement{
    private String varName;
    private IExpression expression;

    public WriteHeapStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        if(!state.getSymTable().contains(varName)){
            throw new StatementException("Variable '" + varName + "' does not exist");
        }
        if(!(state.getSymTable().getValue(varName).getType() instanceof RefType)){
            throw new StatementException("Variable '" + varName + "' is not a reference type");
        }
        if(!state.getHeap().containsKey(((RefValue) state.getSymTable().getValue(varName)).getAddress())){
            throw new StatementException("Variable '" + varName + "' is not in the heap");
        }
        IValue exprVal = expression.eval(state.getSymTable(), state.getHeap());
        if(!exprVal.getType().equals(((RefValue) state.getSymTable().getValue(varName)).getLocationType())){
            throw new StatementException("Wrong type");
        }
        int address = ((RefValue) state.getSymTable().getValue(varName)).getAddress();
        state.getHeap().set(address, exprVal);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typeVar = typeEnv.getValue(varName);
        IType typeExp = expression.typeCheck(typeEnv);
        if(!(typeVar instanceof RefType)){
            throw new StatementException("Variable '" + varName + "' is not a reference type");
        }
        if(!typeExp.equals(((RefType) typeVar).getInner())){
            throw new StatementException("Expression type does not match the reference type");
        }
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(varName, expression);
    }

    @Override
    public String toString() {
        return "WriteHeap(" + varName + ", " + expression + ")";
    }
}
