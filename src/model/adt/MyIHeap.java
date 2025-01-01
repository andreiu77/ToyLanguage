package model.adt;

import model.value.IValue;

import java.util.Map;

public interface MyIHeap {
    public int allocate(IValue val);
    public IValue getValue(int key);
    public void set(int key, IValue val);
    public Map<Integer, IValue> getHeap();
    public boolean containsKey(int key);
    public void setContent(Map<Integer, IValue> newHeap);
}
