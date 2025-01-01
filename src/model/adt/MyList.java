package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private List<T> list;

    public MyList(){
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(T element : list){
            str.append(element).append("\n");
        }
        return "Output list:\n" + str.toString();
    }
}
