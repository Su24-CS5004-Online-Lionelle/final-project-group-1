import java.util.List;

public class ACControllerTest {

    private ACController controller;
    private AdoptionCenterModel model;
    private Dog dog1;
    private Dog dog2;
    private Dog dog3;

    @BeforeEach
    public void setUp() {
        // Initialize the Breed object
        Breed breed = new Breed("1", "New Breed", "Description", 10, 12, 50, 70, 45, 60, false);

        // Initialize Dog objects
        dog1 = new Dog("1", "Dog1", "f", breed, 2, 45, "1.png", 500, true);
        dog2 = new Dog("2", "Dog2", "f", breed, 5, 50, "2.png", 700, true);
        dog3 = new Dog("3", "Dog3", "m", breed, 4, 55, "3.png", 600, true);

        // Initialize the AdoptionCenterModel and ACController
        model = new AdoptionCenterModel();
        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);
        controller = new ACController(model);
    }

    /**
     * Tests the constructor of ACController.
     * Verifies that the controller is not null and initializes the correct number of dogs.
     */
    @Test
    public void testConstructor() {
        assertNotNull(controller);
        assertEquals(3, controller.getAdoptableDogs().size());
    }

    /**
     * Tests setting and applying the name filter.
     * Verifies that only the dog with the specified name is returned.
     */
    @Test
    public void testSetNameSearch() {
        controller.setNameSearch("Dog1");
        controller.setResultList();
        List<Dog> resultList = controller.getResultList();

        assertEquals(1, resultList.size());
        assertEquals(dog1, resultList.get(0));
    }

    /**
     * Tests setting and applying the breed filter.
     * Verifies that all dogs of the specified breed are returned.
     */
    @Test
    public void testSetBreedSearch() {
        controller.setBreedSearch("New Breed");
        controller.setResultList();
        List<Dog> resultList = controller.getResultList();

        assertEquals(3, resultList.size());
        assertTrue(resultList.contains(dog1));
        assertTrue(resultList.contains(dog2));
        assertTrue(resultList.contains(dog3));
    }

    /**
     * Tests setting and applying the sex filter.
     * Verifies that only dogs of the specified sex are returned.
     */
    @Test
    public void testSetSexSearch() {
        controller.setSexSearch("f");
        controller.setResultList();
        List<Dog> resultList = controller.getResultList();

        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(dog1));
        assertTrue(resultList.contains(dog2));
    }

    /**
     * Tests adding a dog to the wishlist.
     * Verifies that the dog is successfully added.
     */
    @Test
    public void testAddToWishList() {
        String response = controller.addToWishList(dog1);

        assertEquals("Dog1 has been added to your wishlist.", response);
        assertEquals(1, controller.getWishList().size());
    }

    /**
     * Tests adding a dog to the wishlist when it already exists.
     * Verifies that the dog is not added again.
     */
    @Test
    public void testAddToWishListDuplicate() {
        controller.addToWishList(dog1);
        String response = controller.addToWishList(dog1);

        assertEquals("Dog1 is already in your wishlist.", response);
        assertEquals(1, controller.getWishList().size());
    }

    /**
     * Tests removing a dog from the wishlist.
     * Verifies that the dog is successfully removed.
     */
    @Test
    public void testRemoveFromWishList() {
        controller.addToWishList(dog1);
        String response = controller.removeFromWishList("1");

        assertEquals("Dog1 was removed from wish list.", response);
        assertEquals(0, controller.getWishList().size());
    }

    /**
     * Tests removing a dog from the wishlist when it is not in the wishlist.
     * Verifies that the appropriate message is returned.
     */
    @Test
    public void testRemoveFromWishListNotFound() {
        String response = controller.removeFromWishList("10");

        assertEquals("10 could not be found in the wish list.", response);
    }

    /**
     * Tests sorting dogs by age in ascending order.
     * Verifies that dogs are correctly sorted from youngest to oldest.
     */
    @Test
    public void testSortByAgeAscending() {
        controller.setHomeList(); // Assuming setHomeList sorts by age ascending
        List<Dog> homeList = controller.getHomeList();

        assertEquals(3, homeList.size());
        assertEquals(dog1, homeList.get(0)); // Youngest dog
        assertEquals(dog3, homeList.get(1)); // Second youngest
        assertEquals(dog2, homeList.get(2)); // Oldest dog
    }

    /**
     * Tests setting an invalid filter.
     * Verifies that applying a filter with a null or empty value throws an exception.
     */
    @Test
    public void testApplyFilterWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> controller.setNameSearch(null));
        assertThrows(IllegalArgumentException.class, () -> controller.setNameSearch(""));
    }
}
