package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableItem {
    private final StringProperty column1;
    private final StringProperty column2;

    public TableItem(String column1, String column2) {
        this.column1 = new SimpleStringProperty(column1);
        this.column2 = new SimpleStringProperty(column2);
    }

    public String getColumn1() {
        return column1.get();
    }

    public void setColumn1(String value) {
        column1.set(value);
    }

    public StringProperty column1Property() {
        return column1;
    }

    public String getColumn2() {
        return column2.get();
    }

    public void setColumn2(String value) {
        column2.set(value);
    }

    public StringProperty column2Property() {
        return column2;
    }
}
