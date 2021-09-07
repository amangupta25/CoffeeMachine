package event;

import inventory.InventoryHandler;
import models.Beverage;

/**
 * An event to start the beverage making process asynchronously
 */

public class MakeBeverage implements Runnable{
    private final Beverage beverage;

    public MakeBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public void run() {
        if(InventoryHandler.getInstance().checkAndFetchIngredients(beverage)) {
            System.out.println(beverage.getName() + " is prepared");
        }
    }
}
