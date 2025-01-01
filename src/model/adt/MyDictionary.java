package model.adt;

import exceptions.KeyNotFoundException;
import model.value.IValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V>{
    private Map<K, V> map;

    public MyDictionary() {
        map = new HashMap<>();
    }

    public MyDictionary(Map<K, V> m){
        map = new HashMap<>(m);
    }

    public Map<K, V> getMap() {
        return map;
    }

    @Override
    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public V getValue(K key) throws KeyNotFoundException {
        if(!this.map.containsKey(key))
            throw new KeyNotFoundException("Key not found");
        return this.map.get(key);
    }

    @Override
    public void remove(K key) throws KeyNotFoundException {
        if(!this.map.containsKey(key))
            throw new KeyNotFoundException("Key not found");
        this.map.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public Set<K> getKeys() {
        return this.map.keySet();
    }

    public Collection<IValue> getValues(){
        return (Collection<IValue>) this.map.values();
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        return new MyDictionary<>(this.map);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(K key : map.keySet()){
            sb.append(key.toString() + " -> " + map.get(key).toString() + "\n");
        }
        return "MyDictionary{\n" + sb.toString() + "}\n";
    }
}
