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


public class AllocHeapStatement implements IStatement {
    private String var;
    private IExpression expression;

    public AllocHeapStatement(String var, IExpression value) {
        this.var = var;
        this.expression = value;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        if(!state.getSymTable().contains(var)){
            throw new StatementException("Variable '" + var + "' does not exist");
        }
        if(!(state.getSymTable().getValue(var).getType() instanceof RefType)){
            throw new StatementException("Variable '" + var + "' is not a reference type");
        }
        IValue exprVal = expression.eval(state.getSymTable(), state.getHeap());
        if(!(((RefValue)(state.getSymTable().getValue(var))).getLocationType().equals(exprVal.getType()))){
            System.out.println(((RefValue)(state.getSymTable().getValue(var))).getLocationType());
            System.out.println(exprVal.getType());
            throw new StatementException("Wrong type");
        }
        int address = state.getHeap().allocate(exprVal);
        state.getSymTable().insert(var, new RefValue(address, ((RefValue)(state.getSymTable().getValue(var))).getLocationType()));
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typeVar = typeEnv.getValue(var);
        IType typeExp = expression.typeCheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp))){
            return typeEnv;
        }
        throw new StatementException("Variable '" + var + "' is not a reference type");
    }

    @Override
    public IStatement deepCopy() {
        return new AllocHeapStatement(var, expression);
    }

    @Override
    public String toString() {
        return "AllocHeap(" + var + ", " + expression + ")";
    }
}
