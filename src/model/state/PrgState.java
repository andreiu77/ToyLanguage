package model.state;

import exceptions.ADTException;
import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class PrgState {
    private MyIDictionary<String, IValue> symTable;
    private MyIStack<IStatement> execStack;
    private MyIList<String> output;
    private IStatement initialStatement;
    private MyIHeap heap;
    private int id;
    private static int lastIndex;

    private MyIDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(MyIDictionary<String, IValue> dict, MyIStack<IStatement> stack, MyIList<String> output, IStatement init, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap heap) {
        this.symTable = dict;
        this.execStack = stack;
        this.output = output;
        this.initialStatement = init.deepCopy();
        execStack.push(init);
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = getNewId();
    }

    private synchronized int getNewId(){
        lastIndex++;
        return lastIndex;
    }

    public String fileTableToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FileTable: \n");
        for(StringValue key : this.fileTable.getKeys()){
            sb.append(key.toString() + "\n");
        }
        return sb.toString();
    }

    public PrgState oneStep() throws ADTException, ExpressionException, FileNotFoundException{
        if(execStack.isEmpty())
            throw new EmptyStackException("Stack is empty");
        IStatement curr = execStack.pop();
        return curr.execute(this);
    }

    public boolean isNotComplete(){
        return !execStack.isEmpty();
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIStack<IStatement> getExeStack() {
        return execStack;
    }

    public MyIList<String> getOutput() {
        return output;
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "PrgState with id " + this.id + "\n" + execStack.toString() + "\n" + symTable.toString() + "\n" + output.toString() + "\n" + fileTableToString() + "\n" + heap.toString() + "\n-----";
    }

}
