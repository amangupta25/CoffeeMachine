package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * Outlet Model - To store the number of outlets from input json
 */

@Getter
@ToString
public class Outlet {
    @JsonProperty("count_n")
    private int numberOfOutlets;
}
