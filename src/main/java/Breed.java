import com.fasterxml.jackson.annotation.JsonProperty;

public record Breed (

    String id,
    String name,
    String description,
    @JsonProperty("life_min") int lifeMin,
    @JsonProperty("life_max") int lifeMax,
    @JsonProperty("male_weight_min") int maleWeightMin,
    @JsonProperty("male_weight_max") int maleWeightMax,
    @JsonProperty("female_weight_min") int femaleWeightMin,
    @JsonProperty("female_weight_max") int femaleWeightMax,
    boolean hypoallergenic
) {}

