package inventory;

import lombok.extern.slf4j.Slf4j;
import models.Beverage;

import java.util.HashMap;
import java.util.Map;

/**
 * To handle all the inventory and related checks and updates
 * Properties - Singleton, Threadsafe
 */

@Slf4j
public class InventoryHandler {
    //Bill Pugh Singleton Implementation - thread safe
    private InventoryHandler(){}
    private static class InventorySingleton {
        private static final InventoryHandler instance = new InventoryHandler();
    }

    public static InventoryHandler getInstance(){
        return InventorySingleton.instance;
    }

    public Map<String,Integer> inventory = new HashMap<>();

    public void fillInventory(Integer qty, String ingredient){
        int presentQty = inventory.getOrDefault(ingredient,0);
        inventory.put(ingredient,presentQty + qty);
    }

    public synchronized boolean checkAndFetchIngredients(Beverage beverage){
        Map<String,Integer> ingredientsRequired = beverage.getIngredients();
        boolean canBeverageBePrepared = true;


        for(String ingredientName: ingredientsRequired.keySet()) {
            int ingredientQtyInInventory = inventory.getOrDefault(ingredientName,-1);
            if(ingredientQtyInInventory == 0 || ingredientQtyInInventory == -1) {
                System.out.println(beverage.getName()
                        + " cannot be prepared because "
                        + ingredientName
                        + " is not available");
                canBeverageBePrepared = false;
                break;
            }
            else if(ingredientsRequired.get(ingredientName) > ingredientQtyInInventory){
                System.out.println(beverage.getName()
                        + " cannot be prepared because item "
                        + ingredientName +
                        " is not sufficient");
                canBeverageBePrepared = false;
                break;
            }
        }

        if(canBeverageBePrepared) {
            for(String ingredientName: ingredientsRequired.keySet()) {
                int presentQty = inventory.getOrDefault(ingredientName,0);
                inventory.put(ingredientName,presentQty - ingredientsRequired.get(ingredientName));
            }
        }
        return canBeverageBePrepared;
    }

    // For test cases purpose
    public void reset(){
        inventory.clear();
    }
}
