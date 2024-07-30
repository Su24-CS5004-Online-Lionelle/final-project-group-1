import java.util.List;

public class ACController {
    private AdoptionCenterModel model;
    private List<Dog> resultList;
    private ACFilterPlanner planner;
    private String nameSearch;
    private boolean nameOn = false;
    private String sexSearch;
    private boolean sexOn = false;
    private String breedSearch;
    private boolean breedOn = false;
    private String ageSearch;
    private boolean ageOn = false;
    private String weightSearch;
    private boolean weightOn = false;
    private String priceSearch;
    private boolean priceOn = false;
    
    public ACController(AdoptionCenterModel model) {
        this.model = model;
        this.ACFilterPlanner = new ACFilterPlanner(model.getAdoptableDogs());
    }

    public void setNameSearch(String name) {
        this.nameOn = true;
        this.nameSearch = name;
    }

    public void setSexSearch(String sex) {
        this.sexOn = true;
        this.sexSearch = sex;
    }

    public void setBreedSearch(String breed) {
        this.breedOn = true;
        this.breedSearch = breed;
    }

    public void setAgeSearch(String age) {
        this.ageOn = true;
        this.ageSearch = age;
    }

    public void setWeightSearch(String weight) {
        this.weightOn = true;
        this.weightSearch = weight;
    }

    public void setPriceSearch(String price) {
        this.priceOn = true;
        this.priceSearch = price;
    }

    public void clearSearchFilters() {
        nameOn = false;
        sexOn = false;
        breedOn = false;
        ageOn = false;
        weightOn = false;
        priceOn = false;
    }

    public List<Dog> getResultList() {

    }

}
