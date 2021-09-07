package processor;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoffeeMachineTest {

    CoffeeMachine coffeeMachine;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        coffeeMachine.reset();
        resetCoffeeMachineSingleton("coffeeMachine");
    }

    // Resetting a singleton instance using reflect api
    public static void resetCoffeeMachineSingleton(String fieldName) {
        Field instance;
        try {
            instance = CoffeeMachine.class.getDeclaredField(fieldName);
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /*** Naming convention of Test Method - MethodName_StateUnderTest_ExpectedBehavior ***/

    @DisplayName("4 outlets")
    @Test
    public void process_FourOutlets_ValidOutput() throws IOException {
        final String path = "sample.json";
        File inputFile = new File(Objects.requireNonNull(CoffeeMachine.class.getClassLoader().getResource(path)).getFile());
        String json = FileUtils.readFileToString(inputFile, "UTF-8");
        coffeeMachine = CoffeeMachine.getInstance(json);
        coffeeMachine.process();
        assertEquals(4,coffeeMachine.machineInfo.getMachine().getOutlets().getNumberOfOutlets());
    }

    @DisplayName("3 outlets")
    @Test
    public void process_ThreeOutlets_ValidOutput() throws IOException {
        final String path = "sample1.json";
        File inputFile = new File(Objects.requireNonNull(CoffeeMachine.class.getClassLoader().getResource(path)).getFile());
        String json = FileUtils.readFileToString(inputFile, "UTF-8");
        coffeeMachine = CoffeeMachine.getInstance(json);
        coffeeMachine.process();
    }

    @DisplayName("Zero Ingredients Qty in inventory")
    @Test
    public void process_ZeroIngredientsQtyInInventory_ValidOutput() throws IOException {
        final String path = "sample2.json";
        File inputFile = new File(Objects.requireNonNull(CoffeeMachine.class.getClassLoader().getResource(path)).getFile());
        String json = FileUtils.readFileToString(inputFile, "UTF-8");
        coffeeMachine = CoffeeMachine.getInstance(json);
        coffeeMachine.process();
    }

    @DisplayName("No Ingredients in inventory")
    @Test
    public void process_NoIngredientsInInventory_ValidOutput() throws IOException {
        final String path = "sample3.json";
        File inputFile = new File(Objects.requireNonNull(CoffeeMachine.class.getClassLoader().getResource(path)).getFile());
        String json = FileUtils.readFileToString(inputFile, "UTF-8");
        coffeeMachine = CoffeeMachine.getInstance(json);
        coffeeMachine.process();
    }

    @DisplayName("Insufficient Ingredients for all beverages in inventory")
    @Test
    public void process_InsufficientIngredientsInInventory_ValidOutput() throws IOException {
        final String path = "sample4.json";
        File inputFile = new File(Objects.requireNonNull(CoffeeMachine.class.getClassLoader().getResource(path)).getFile());
        String json = FileUtils.readFileToString(inputFile, "UTF-8");
        coffeeMachine = CoffeeMachine.getInstance(json);
        coffeeMachine.process();
    }

    @DisplayName("Beverage With No Ingredients")
    @Test
    public void process_BeverageWithNoIngredients_ValidOutput() throws IOException {
        final String path = "sample5.json";
        File inputFile = new File(Objects.requireNonNull(CoffeeMachine.class.getClassLoader().getResource(path)).getFile());
        String json = FileUtils.readFileToString(inputFile, "UTF-8");
        coffeeMachine = CoffeeMachine.getInstance(json);
        coffeeMachine.process();
    }

    @DisplayName("Sufficient Ingredients for all beverages in inventory")
    @Test
    public void process_SufficientIngredientsInInventory_ValidOutput() throws IOException {
        final String path = "sample6.json";
        File inputFile = new File(Objects.requireNonNull(CoffeeMachine.class.getClassLoader().getResource(path)).getFile());
        String json = FileUtils.readFileToString(inputFile, "UTF-8");
        coffeeMachine = CoffeeMachine.getInstance(json);
        coffeeMachine.process();
    }

    @DisplayName("6 outlets")
    @Test
    public void process_SixOutlets_ValidOutput() throws IOException {
        final String path = "sample7.json";
        File inputFile = new File(Objects.requireNonNull(CoffeeMachine.class.getClassLoader().getResource(path)).getFile());
        String json = FileUtils.readFileToString(inputFile, "UTF-8");
        coffeeMachine = CoffeeMachine.getInstance(json);
        coffeeMachine.process();
    }
}