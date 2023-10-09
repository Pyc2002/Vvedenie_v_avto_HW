package HW_2;

import java.util.ArrayList;
import java.util.List;

public class ProductGroup implements CarParts {

    private List<CarParts> partsGroup = new ArrayList<>();

    public void addItem(CarParts item) {
        partsGroup.add(item);
    }

    public void removeItem(CarParts item){
        partsGroup.remove(item);
    }

    public int getSize(){
        return partsGroup.size();
    }


    @Override
    public double getPrice() {
        double totalPrice = 0;
        for (CarParts item : partsGroup) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
}
