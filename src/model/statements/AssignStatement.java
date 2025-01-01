package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.value.IValue;

public class AssignStatement implements IStatement {
    private IExpression expression;
    private String varName;

    public AssignStatement(String varName, IExpression expression) {
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        if(!state.getSymTable().contains(varName)){
            throw new StatementException("Variable " + varName + " does not exist in SymTable");
        }
        IValue oldVal = state.getSymTable().getValue(varName);
        IValue newVal = this.expression.eval(state.getSymTable(), state.getHeap());
        if(!oldVal.getType().equals(newVal.getType())){
            throw new StatementException("Value of variable " + varName + " is not of type " + oldVal.getType());
        }
        state.getSymTable().insert(varName, newVal);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typeVar = typeEnv.getValue(varName);
        IType typeExp = expression.typeCheck(typeEnv);
        if(!typeVar.equals(typeExp)){
            throw new StatementException("Assignment: right hand side and left hand side have different types");
        }
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return varName + " = " + expression.toString();
    }
}
