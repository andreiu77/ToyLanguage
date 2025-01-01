package model.statements;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.state.PrgState;
import model.types.IType;

public class CompStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    public IStatement getFirst() {
        return first;
    }

    public IStatement getSecond() {
        return second;
    }

    @Override
    public String toString(){
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStatement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public IStatement deepCopy() {
        return new CompStatement(first.deepCopy(), second.deepCopy());
    }
}
