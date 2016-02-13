package pits;

import javafx.beans.property.*;

/**
 * Created by Eric on 2/7/2016.
 */
public class Item {

    private final SimpleStringProperty name;
    private final SimpleStringProperty unit;
    private final SimpleDoubleProperty walmartHyvee;
    private final SimpleDoubleProperty usfoods;
    private final SimpleDoubleProperty roma;
    private final SimpleIntegerProperty count;

    public Item(String name, String unit, Double walmartHyvee, Double usfoods, Double roma, int count) {
        this.name = new SimpleStringProperty(name);
        this.unit = new SimpleStringProperty(unit);
        this.walmartHyvee = new SimpleDoubleProperty(walmartHyvee);
        this.usfoods = new SimpleDoubleProperty(usfoods);
        this.roma = new SimpleDoubleProperty(roma);
        this.count = new SimpleIntegerProperty(count);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public double getWalmartHyvee() {
        return walmartHyvee.get();
    }

    public void setWalmartHyvee(double walmartHyvee) {
        this.walmartHyvee.set(walmartHyvee);
    }

    public double getUsfoods() {
        return usfoods.get();
    }

    public void setUsfoods(double usfoods) {
        this.usfoods.set(usfoods);
    }

    public double getRoma() {
        return roma.get();
    }

    public void setRoma(double roma) {
        this.roma.set(roma);
    }
}
