package model.adt;

import exceptions.EmptyStackException;

import java.util.Stack;

public interface MyIStack<T> {
    public Stack<T> getStack();
    public void push(T element);
    public T pop() throws EmptyStackException;
    public int size();
    public boolean isEmpty();
}
