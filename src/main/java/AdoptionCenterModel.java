import java.util.ArrayList;
import java.util.List;

public class AdoptionCenterModel {
    // List to store Dog objects
    private List<Dog> dogs;

    // Constructor to initialize the list
    public AdoptionCenterModel() {
        this.dogs = new ArrayList<>();
    }

    // Method to add a Dog to the list
    public void addDog(Dog dog) {
        if (dog != null) {
            dogs.add(dog);
        } else {
            throw new IllegalArgumentException("Dog object cannot be null.");
        }
    }

    // Method to remove a Dog from the list
    public void removeDog(String dogId) {
        dogs.removeIf(dog -> dog.getID().equals(dogId));
    }

    // Method to get a list of all dogs
    public List<Dog> getAllDogs() {
        return new ArrayList<>(dogs);
    }

    // Method to get a dog by its ID
    public Dog getDogById(String dogId) {
        return dogs.stream()
                   .filter(dog -> dog.getID().equals(dogId))
                   .findFirst()
                   .orElse(null);
    }

    // Method to change the age of a dog by its ID
    public void changeDogAge(String dogId, int newAge) {
        Dog dog = getDogById(dogId);
        if (dog != null) {
            dog.changeAge(newAge);
        } else {
            throw new IllegalArgumentException("Dog with given ID not found.");
        }
    }

    // Method to change the price of a dog by its ID
    public void changeDogPrice(String dogId, double newPrice) {
        Dog dog = getDogById(dogId);
        if (dog != null) {
            dog.changePrice(newPrice);
        } else {
            throw new IllegalArgumentException("Dog with given ID not found.");
        }
    }

    // Method to mark a dog as ready for adoption by its ID
    public void markDogReadyForAdoption(String dogId, boolean isReady) {
        Dog dog = getDogById(dogId);
        if (dog != null) {
            if (dog.getPrice() > 0) {
                dog.changeIsReady(isReady);
            } else {
                throw new IllegalArgumentException("Dog must have a price.");
            }
        } else {
            throw new IllegalArgumentException("Dog with given ID not found.");
        }
    }
}
