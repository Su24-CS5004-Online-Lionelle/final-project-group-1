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
    private boolean adoptionReady = true;
    
    public ACController(AdoptionCenterModel model) {
        this.model = model;
        this.ACFilterPlanner = new ACFilterPlanner(model.getAdoptableDogs());
    }
}
