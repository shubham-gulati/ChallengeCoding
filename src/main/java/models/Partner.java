package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner {

    @JsonProperty("id")
    private String Id;

    @JsonProperty("resource")
    private String resource;

    @JsonProperty("type")
    private ArrayList<String> type;

    @JsonProperty("capacity")
    private Integer capacity;

    @JsonProperty("address")
    private  Address address;

    public Address getAddress() {
        return address;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getId() {
        return Id;
    }

    public String getResource() {
        return resource;
    }
}
