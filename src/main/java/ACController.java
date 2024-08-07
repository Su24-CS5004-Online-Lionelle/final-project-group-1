import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ACController {
    /** The model containing adoption center data. */
    private AdoptionCenterModel model;
    /** The list of dogs resulting from applied filters. */
    private List<Dog> resultList;
    /** The planner for filtering dogs. */
    private ACFilterPlanner planner;
    /** Flag indicating if name filter is active. */
    private boolean nameOn = false;
    /** The search string for filtering by name. */
    private String nameSearch;
    /** Flag indicating if sex filter is active. */
    private boolean sexOn = false;
    /** The search string for filtering by sex. */
    private String sexSearch;
    /** Flag indicating if breed filter is active. */
    private boolean breedOn = false;
    /** The search string for filtering by breed. */
    private String breedSearch;
    /** Flag indicating if age filter is active. */
    private boolean ageOn = false;
    /** The search string for filtering by age. */
    private String ageSearch;
    /** Flag indicating if weight filter is active. */
    private boolean weightOn = false;
    /** The search string for filtering by weight. */
    private String weightSearch;
    /** Flag indicating if price filter is active. */
    private boolean priceOn = false;
    /** The search string for filtering by price. */
    private String priceSearch;
    /** The attribute to sort results by. */
    private String sortOn = "price";
    /** Flag indicating if sorting should be in ascending order. */
    private boolean ascending = true;
    /** Wish list for the wish list page. */
    private List<Dog> wishList = new ArrayList<>();

    /**
     * Constructs a new ACController with the given model.
     *
     * @param model The AdoptionCenterModel to use.
     */
    public ACController(AdoptionCenterModel model) {
        this.model = model;
        this.planner = new ACFilterPlanner(this.model.getAdoptableDogs());
    }

    /**
     * Sets the name search filter.
     *
     * @param name The name to search for.
     */
    public void setNameSearch(String name) {
        this.nameOn = true;
        this.nameSearch = name;
    }

    /**
     * Sets the sex search filter.
     *
     * @param sex The sex to search for.
     */
    public void setSexSearch(String sex) {
        this.sexOn = true;
        this.sexSearch = sex;
    }

    /**
     * Sets the breed search filter.
     *
     * @param breed The breed to search for.
     */
    public void setBreedSearch(String breed) {
        this.breedOn = true;
        this.breedSearch = breed;
    }

    /**
     * Sets the age search filter.
     *
     * @param age The age to search for.
     */
    public void setAgeSearch(String age) {
        this.ageOn = true;
        this.ageSearch = age;
    }

    /**
     * Sets the weight search filter.
     *
     * @param weight The weight to search for.
     */
    public void setWeightSearch(String weight) {
        this.weightOn = true;
        this.weightSearch = weight;
    }

    /**
     * Sets the price search filter.
     *
     * @param price The price to search for.
     */
    public void setPriceSearch(String price) {
        this.priceOn = true;
        this.priceSearch = price;
    }

    /**
     * Clears all search filters.
     */
    public void clearSearchFilters() {
        nameOn = false;
        sexOn = false;
        breedOn = false;
        ageOn = false;
        weightOn = false;
        priceOn = false;
    }

    /**
     * Applies all active filters and sets the resultList.
     */
    public void setResultList() {
        Stream<Dog> filteredDogs;
        filteredDogs = planner.filter(
        this.nameOn, this.nameSearch,
        this.sexOn, this.sexSearch,
        this.breedOn, this.breedSearch,
        this.ageOn, this.ageSearch,
        this.weightOn, this.weightSearch,
        this.priceOn, this.priceSearch, this.sortOn, this.ascending);
        this.resultList = filteredDogs.toList();
    }

    /**
     * Converts the resultList to JSON format.
     *
     * @return A JSON string representation of the resultList.
     */
    public String getResultListToJsonFormat() {

        setResultList();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this.resultList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    /**
     * Saves the list of dogs to a json file in json format.
     * 
     * The contents of the file will be each dog detail in json format. It will
     * overwrite the file if
     * it already exists.
     * 
     * Saves them in the same order as getResultListToJsonFormat.
     * 
     * @param filename The name of the file to save the list to.
     */
    public void saveList(String filename) {
        String result = "";
        String file = filename;
        if (!filename.endsWith(".json")) {
            file = filename + ".json";
        }
        result = this.getResultListToJsonFormat();
        File dogFile = new File(file);
        try (FileWriter dogWriter = new FileWriter(dogFile, false)) {
            dogWriter.write(result);
            dogWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the list of dogs in the wish list.
     *
     * @return a List of Dog objects representing the wish list.
     */
    public List<Dog> getWishList() {
        return this.wishList;
    }

    /**
     * Adds a Dog to the wish list if it does not already exist.
     *
     * @param dog the Dog object to be added to the wish list.
     * @return a String message indicating whether the dog was added or already exists in the wish list.
     */
    public String addToWishList(Dog dog) {
        boolean alrdyExist = false;
        for (Dog wishDog : wishList) {
            if (dog.equals(wishDog)) {
                alrdyExist = true;
            }
        }
        if (alrdyExist == false) {
            this.wishList.add(dog);
            return dog.getName() + " has been added to your wish list.";
        }
        return dog.getName() + " already exists in your wish list.";
    }

    /**
     * Removes a Dog from the wish list based on its ID.
     *
     * @param id the ID of the Dog to be removed from the wish list.
     * @return a String message indicating whether the dog was removed or could not be found in the wish list.
     */
    public String removeFromWishList(String id) {
        for (int index = 0; index < wishList.size(); index++) {
            if (id.equals(wishList.get(index).getID())) {
                String name = wishList.get(index).getName();
                this.wishList.remove(index);
                return name + "was removed from wish list.";
            }
        }
        return id + " could not be found in the wish list.";
    }
}
