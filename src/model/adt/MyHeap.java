package model.adt;

import exceptions.ADTException;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{
    private Map<Integer, IValue> heap;
    private int freeLocation;

    public MyHeap(){
        this.heap = new HashMap<>();
        this.freeLocation = 1;
    }

    @Override
    public int allocate(IValue val) {
        this.heap.put(freeLocation, val);
        return freeLocation++;
    }

    @Override
    public IValue getValue(int key) {
        if(!this.heap.containsKey(key))
            throw new ADTException("Key not found");
        return this.heap.get(key);
    }

    @Override
    public void set(int key, IValue val) {
        if(!this.heap.containsKey(key))
            throw new ADTException("Key not found");
        this.heap.put(key, val);
    }

    @Override
    public Map<Integer, IValue> getHeap() {
        return this.heap;
    }

    @Override
    public boolean containsKey(int key) {
        return this.heap.containsKey(key);
    }

    @Override
    public void setContent(Map<Integer, IValue> newHeap) {
        this.heap = newHeap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Integer key : heap.keySet()){
            sb.append(key.toString() + " -> " + heap.get(key).toString() + "\n");
        }
        return "MyHeap{\n" + sb.toString() + "}\n";
    }
}
