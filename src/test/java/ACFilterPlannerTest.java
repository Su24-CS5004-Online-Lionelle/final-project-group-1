import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ACFilterPlanner;
import model.Breed;
import model.Dog;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ACFilterPlannerTest {

    private ACFilterPlanner planner;
    private Dog dog1;
    private Dog dog2;
    private Dog dog3;

    @BeforeEach
    public void setUp() {
        Breed breed = new Breed("1", "New Breed", "Description", 10, 12, 50, 70, 45, 60, false);
        dog1 = new Dog("1", "Dog1", "f", breed, 2, 45, "1.png");
        dog2 = new Dog("2", "Dog2", "f", breed, 5, 50, "2.png");
        dog3 = new Dog("3", "Dog3", "m", breed, 4, 55, "3.png");
        planner = new ACFilterPlanner(List.of(dog1, dog2, dog3));
    }

    @Test
    public void testConstructor() {
        assertNotNull(planner);
        assertEquals(3, planner.filter(false, "", false, "", false, "", false, "", false, "", false, "", "", true).count());
    }

    @Test
    public void testFilterByName() {
        Stream<Dog> result = planner.filter(true, "Dog1", false, "", false, "", false, "", false, "", false, "", "", true);
        List<Dog> filteredDogs = result.collect(Collectors.toList());

        assertEquals(1, filteredDogs.size());
        assertEquals(dog1, filteredDogs.get(0));
    }

    @Test
    public void testFilterBySex() {
        Stream<Dog> result = planner.filter(false, "", true, "f", false, "", false, "", false, "", false, "", "", true);
        List<Dog> filteredDogs = result.collect(Collectors.toList());

        assertEquals(2, filteredDogs.size());
        assertEquals(dog1, filteredDogs.get(0));
        assertEquals(dog2, filteredDogs.get(1));
    }

    @Test
    public void testFilterByBreed() {
        Stream<Dog> result = planner.filter(false, "", false, "", true, "New Breed", false, "", false, "", false, "", "", true);
        List<Dog> filteredDogs = result.collect(Collectors.toList());

        assertEquals(3, filteredDogs.size());
        assertTrue(filteredDogs.contains(dog1));
        assertTrue(filteredDogs.contains(dog2));
        assertTrue(filteredDogs.contains(dog3));
    }

    @Test
    public void testSortByNameAscending() {
        Stream<Dog> result = planner.filter(false, "", false, "", false, "", false, "", false, "", false, "", "name", true);
        List<Dog> sortedDogs = result.collect(Collectors.toList());

        assertEquals(3, sortedDogs.size());
        assertEquals(dog1, sortedDogs.get(0));
        assertEquals(dog2, sortedDogs.get(1));
        assertEquals(dog3, sortedDogs.get(2));
    }

    @Test
    public void testSortByAgeDescending() {
        Stream<Dog> result = planner.filter(false, "", false, "", false, "", false, "", false, "", false, "", "age", false);
        List<Dog> sortedDogs = result.collect(Collectors.toList());

        assertEquals(3, sortedDogs.size());
        assertEquals(dog2, sortedDogs.get(0));
        assertEquals(dog3, sortedDogs.get(1));
        assertEquals(dog1, sortedDogs.get(2));
    }

    @Test
    public void testApplyFilterWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> planner.applyFilter(true, "name", null));
    }

    @Test
    public void testApplyFilterWithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> planner.applyFilter(true, "name", ""));
    }
}
