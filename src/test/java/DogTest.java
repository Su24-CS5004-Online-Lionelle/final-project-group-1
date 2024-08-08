import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Breed;
import model.Dog;

import static org.junit.jupiter.api.Assertions.*;

public class DogTest {

    private Breed breed;
    private Dog dog;

    @BeforeEach
    public void setUp() {
        Breed breed = new Breed("1", "New Breed", "Description", 10, 12, 50, 70, 45, 60, false);
        dog = new Dog("1", "Dog1", "f", breed, 2, 45.0, "1.png");
    }

    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Dog(null, "Dog2", "f", breed, 5, 20.0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", null, "f", breed, 5, 20.0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", null, breed, 5, 20.0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", null, 5, 20.0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 5, 20.0, null));
    }

    @Test
    public void testConstructorInvalidSex() {
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "x", breed, 5, 20.0, "2.png"));
    }

    @Test
    public void testConstructorInvalidAge() {
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 0, 20.0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, -1, 20.0, "2.png"));
    }

    @Test
    public void testConstructorInvalidWeight() {
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 5, 0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 5, -1, "2.png"));
    }

    @Test
    public void testConstructorInvalidPrice() {
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 5, 20.0, "2.png", -1, false));
    }

    @Test
    public void testConstructorInvalidImage() {
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 5, 20.0, "invalid.png"));
    }

    @Test
    public void testId() {
        assertEquals("1", dog.getID());
    }

    @Test
    public void testGetSex() {
        assertEquals("f", dog.getSex());
    }

    @Test
    public void testGetBreed() {
        Breed breed = new Breed("1", "New Breed", "Description", 10, 12, 50, 70, 45, 60, false);
        assertEquals(breed, dog.getBreed());
    }

    @Test
    public void testChangeName() {
        dog.changeName("Dog");
        assertEquals("Dog", dog.getName());
    }

    @Test
    public void testChangeAgeValid() {
        dog.changeAge(3);
        assertEquals(3, dog.getAge());
    }

    @Test
    public void testChangeAgeInvalid() {
        assertThrows(IllegalArgumentException.class, () -> dog.changeAge(1));
    }

    @Test
    public void testChangeWeightValid() {
        dog.changeWeight(15.0);
        assertEquals(15.0, dog.getWeight());
    }

    @Test
    public void testChangeWeightInvalid() {
        assertThrows(IllegalArgumentException.class, () -> dog.changeWeight(0));
        assertThrows(IllegalArgumentException.class, () -> dog.changeWeight(-1));
    }

    @Test
    public void testChangeImageValid() {
        dog.changeImage("2.png");
        assertEquals("src/main/resources/dogimages/2.png", dog.getImage());
    }

    @Test
    public void testChangeImageInvalid() {
        assertThrows(IllegalArgumentException.class, () -> dog.changeImage("invalid.png"));
    }

    @Test
    public void testChangePriceValid() {
        dog.changePrice(20.0);
        assertEquals(20.0, dog.getPrice());
    }

    @Test
    public void testChangePriceInvalid() {
        assertThrows(IllegalArgumentException.class, () -> dog.changePrice(-1));
    }

    @Test
    public void testChangeIsReady() {
        dog.changeIsReady(true);
        assertTrue(dog.getIsReady());
        dog.changeIsReady(false);
        assertFalse(dog.getIsReady());
    }
}
