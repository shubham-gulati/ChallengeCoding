package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @JsonProperty("id")
    private String Id;

    @JsonProperty("resource")
    private String resource;

    @JsonProperty("type")
    private String type;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("deliverability")
    private String deliverability;

    public String getId() {
        return Id;
    }

    public String getResource() {
        return resource;
    }

    public Address getAddress() {
        return address;
    }

    public String getDeliverability() {
        return deliverability;
    }

    public String getType() {
        return type;
    }
}
