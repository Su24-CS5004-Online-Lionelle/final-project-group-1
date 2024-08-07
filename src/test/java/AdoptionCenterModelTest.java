import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class AdoptionCenterModelTest {

    private AdoptionCenterModel model;
    private Dog dog1;
    private Dog dog2;
    private Dog dog3;

    @BeforeEach
    public void setUp() {
        model = new AdoptionCenterModel();
        Breed breed = new Breed("1", "New Breed", "Description", 10, 12, 50, 70, 45, 60, false);
        dog1 = new Dog("1", "Dog1", "f", breed, 2, 45, "1.png");
        dog2 = new Dog("2", "Dog2", "f", breed, 3, 50, "2.png");
        dog3 = new Dog("3", "Dog3", "m", breed, 4, 55, "3.png");
    }

    @Test
    public void testAddDog() {
        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);
        List<Dog> dogs = model.getAllDogs();
        assertEquals(3, dogs.size());
        assertTrue(dogs.contains(dog3));
    }

    @Test
    public void testAddDogNull() {
        assertThrows(IllegalArgumentException.class, () -> model.addDog(null));
    }

    @Test
    public void testRemoveDog() {
        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);
        model.removeDog(dog1.getID());
        List<Dog> dogs = model.getAllDogs();
        assertEquals(2, dogs.size());
        assertFalse(dogs.contains(dog1));
    }

    @Test
    public void testRemoveDogNonExist() {
        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);
        model.removeDog("4");
        List<Dog> dogs = model.getAllDogs();
        assertEquals(3, dogs.size());
    }

    @Test
    public void testGetAllDogs() {
        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);
        List<Dog> dogs = model.getAllDogs();
        assertEquals(3, dogs.size());
        assertTrue(dogs.contains(dog1));
        assertTrue(dogs.contains(dog2));
        assertTrue(dogs.contains(dog3));
    }

    @Test
    public void testGetDogById() {
        model.addDog(dog1);
        Dog foundDog = model.getDogById(dog1.getID());
        assertNotNull(foundDog);
        assertEquals(dog1.getID(), foundDog.getID());
    }

    @Test
    public void testGetDogByIdNonExist() {
        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);
        Dog foundDog = model.getDogById("4");
        assertNull(foundDog);
    }

    @Test
    public void testChangeDogAge() {
        model.addDog(dog1);
        int newAge = 5;
        model.changeDogAge(dog1.getID(), newAge);
        Dog updatedDog = model.getDogById(dog1.getID());
        assertEquals(newAge, updatedDog.getAge());
    }

    @Test
    public void testChangeDogAgeInvalid() {
        model.addDog(dog1);
        int invalidAge = -1;
        assertThrows(IllegalArgumentException.class, () -> model.changeDogAge(dog1.getID(), invalidAge));
    }

    @Test
    public void testChangeDogAgeNonExist() {
        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);
        assertThrows(IllegalArgumentException.class, () -> model.changeDogAge("4", 3));
    }

    @Test
    public void testChangeDogPrice() {
        model.addDog(dog1);
        double newPrice = 5.0;
        model.changeDogPrice(dog1.getID(), newPrice);
        Dog updatedDog = model.getDogById(dog1.getID());
        assertEquals(newPrice, updatedDog.getPrice());
    }

    @Test
    public void testChangeDogPriceInvalid() {
        model.addDog(dog1);
        double invalidPrice = -5.0;
        assertThrows(IllegalArgumentException.class, () -> model.changeDogPrice(dog1.getID(), invalidPrice));
    }

    @Test
    public void testChangeDogPriceNonExist() {
        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);
        assertThrows(IllegalArgumentException.class, () -> model.changeDogPrice("4", 5.0));
    }

    @Test
    public void testMarkDogReadyForAdoption() {
        model.addDog(dog1);
        model.changeDogPrice(dog1.getID(), 5.0);
        boolean isReady = true;
        model.markDogReadyForAdoption(dog1.getID(), isReady);
        Dog updatedDog = model.getDogById(dog1.getID());
        assertTrue(updatedDog.getIsReady());
    }

    @Test
    public void testMarkDogReadyForAdoptionNoPrice() {
        model.addDog(dog1);
        assertThrows(IllegalArgumentException.class, () -> model.markDogReadyForAdoption(dog1.getID(), true));
    }

    @Test
    public void testMarkDogReadyForAdoptionNonExist() {
        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);
        assertThrows(IllegalArgumentException.class, () -> model.markDogReadyForAdoption("4", true));
    }

    @Test
    public void testGetAdoptableDogs() {
        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);

        model.changeDogPrice(dog1.getID(), 5.0);
        model.changeDogPrice(dog2.getID(), 5.0);
        model.changeDogPrice(dog3.getID(), 5.0);

        model.markDogReadyForAdoption(dog1.getID(), true);
        model.markDogReadyForAdoption(dog2.getID(), true);
        model.markDogReadyForAdoption(dog3.getID(), false);

        List<Dog> adoptableDogs = model.getAdoptableDogs();

        assertEquals(2, adoptableDogs.size());
        assertEquals(dog1, adoptableDogs.get(0));
        assertEquals(dog2, adoptableDogs.get(1));
    }

}
