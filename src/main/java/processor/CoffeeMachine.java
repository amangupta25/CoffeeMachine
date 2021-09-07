package processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import event.MakeBeverage;
import inventory.InventoryHandler;
import lombok.extern.slf4j.Slf4j;
import models.Beverage;
import models.MachineInfo;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Implements a working Coffee Machine, which can serve from multiple outlets in parallel, using multi-threading.
 *
 * Properties : Singleton Class
 *
 * Features :
 *              1. Multiple beverages requests can be handled
 *              2. Indicator to show which all ingredients are running low.
 *              3. Inventory can be refilled with ingredients
 *              4. Can serve from multiple outlets in parallel
 *
 *  Design Implementation : The coffee machine has been implemented using Singleton Class which uses multi-threading
 *                          to support N outlet parallel serving capabilities.
 *                          The inventory part of the design is implemented using Bill Pugh Singleton Algo which makes
 *                          it Singleton and thread safe. All the updates on inventory are also thread safe.
 */

@Slf4j
public class CoffeeMachine {
    private static CoffeeMachine coffeeMachine;
    public final MachineInfo machineInfo;
    private final ThreadPoolExecutor threadPoolExecutor;
    private InventoryHandler inventoryHandler;
    private static final int EXECUTOR_SHUTDOWN_TIME = 1;

    // Singleton
    private CoffeeMachine(String json) throws IOException {
        machineInfo = new ObjectMapper().readValue(json,MachineInfo.class);
        int numberOfOutlets = machineInfo.getMachine().getOutlets().getNumberOfOutlets();
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfOutlets);
    }

    public static CoffeeMachine getInstance(String json) throws IOException {
        if(coffeeMachine == null)
            coffeeMachine = new CoffeeMachine(json);
        return coffeeMachine;
    }

    public void makeBeverageRequest(Beverage beverage) {
        MakeBeverage task = new MakeBeverage(beverage);
        threadPoolExecutor.execute(task);
    }

    public void process() {
        // Initializes and Fills up the inventory with ingredients
        inventoryHandler = InventoryHandler.getInstance();
        Map<String, Integer> ingredients = machineInfo.getMachine().getIngredientQty();

        for(String ingredientName: ingredients.keySet()) {
            inventoryHandler.fillInventory(ingredients.get(ingredientName), ingredientName);
        }

        // Accepts the beverage making request and start the process
        Map<String, Map<String,Integer> > beverages = machineInfo.getMachine().getBeverages();
        for(String beverageName: beverages.keySet()) {
            Beverage beverage = new Beverage(beverageName,beverages.get(beverageName));
            coffeeMachine.makeBeverageRequest(beverage);
        }
    }

    // For Test cases purpose - Resets the machine and inventory
    public void reset() {
        shutdownExecutor();
        inventoryHandler.reset();
    }

    // Shuts Down the ThreadPool Executor
    private void shutdownExecutor(){
        threadPoolExecutor.shutdown();
        try {
            if (!threadPoolExecutor.awaitTermination(EXECUTOR_SHUTDOWN_TIME, TimeUnit.SECONDS)) {
                log.error("Executor did not terminate in the specified time.");
                List<Runnable> droppedTasks = threadPoolExecutor.shutdownNow();
                log.error("Executor was abruptly shut down. " + droppedTasks.size() + " tasks will not be executed.");
            }
        } catch (InterruptedException e) {
            log.error("Exception caught in shutting down hook : {}", e.getLocalizedMessage());
        }
    }
}
