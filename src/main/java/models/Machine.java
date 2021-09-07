package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * Machine Model - To store machine properties from input json
 */

@Getter
@ToString
public class Machine {
    private Outlet outlets;
    @JsonProperty("beverages")
    private Map<String, Map<String,Integer>> beverages;
    @JsonProperty("total_items_quantity")
    private Map<String,Integer> ingredientQty;
}
