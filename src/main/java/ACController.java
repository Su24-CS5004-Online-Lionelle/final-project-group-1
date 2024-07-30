import java.util.List;

public class ACController {
    private AdoptionCenterModel model;
    private List<Dog> resultList;
    private ACFilterPlanner planner;
    
    public ACController(AdoptionCenterModel model) {
        this.model = model;
        this.ACFilterPlanner = new ACFilterPlanner(model.getAllDogs());
    }

    public void setResultList(String userInput) {
        this.resultList = ACFilterPlanner(userInput);
    }
}
