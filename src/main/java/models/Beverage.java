package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * Beverage Model - To store beverage properties from input json
 */

@Getter
@AllArgsConstructor
@ToString
public class Beverage {
    private String name;
    private Map<String,Integer> ingredients;


}
