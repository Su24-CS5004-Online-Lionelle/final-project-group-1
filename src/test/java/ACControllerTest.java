import java.util.List;

/**
 * Unit tests for the ACController class.
 */
class ACControllerTest {
    private ACController controller; // The controller being tested
    private AdoptionCenterModel model; // The model used by the controller

    /**
     * Sets up the test environment before each test.
     * Initializes the AdoptionCenterModel and ACController objects,
     * and adds some test dogs to the model.
     */
    @BeforeEach
    void setUp() {
        model = new AdoptionCenterModel(); // Initialize the model
        controller = new ACController(model); // Initialize the controller with the model

        // Adding some test dogs to the model
        Dog dog1 = new Dog("1", "Buddy", "Male", "Labrador", 3, 30.0, "labrador.jpg", 500, true);
        Dog dog2 = new Dog("2", "Lucy", "Female", "Beagle", 5, 20.0, "beagle.jpg", 400, true);
        Dog dog3 = new Dog("3", "Max", "Male", "Poodle", 2, 15.0, "poodle.jpg", 600, true);

        model.addDog(dog1); // Add dog1 to the model
        model.addDog(dog2); // Add dog2 to the model
        model.addDog(dog3); // Add dog3 to the model
    }

    /**
     * Tests the setNameSearch method and the resulting filtered list.
     * Ensures that filtering by name correctly returns the expected dog.
     */
    @Test
    void testSetNameSearch() {
        controller.setNameSearch("Buddy"); // Set the name filter to "Buddy"
        controller.setResultList(); // Apply the filter
        List<Dog> result = controller.getResultList(); // Get the filtered list
        
        // Check that only one dog is returned and that its name is "Buddy"
        assertEquals(1, result.size());
        assertEquals("Buddy", result.get(0).getName());
    }

    /**
     * Tests the addToWishList method.
     * Ensures that a dog can be added to the wishlist and the operation is successful.
     */
    @Test
    void testAddToWishList() {
        Dog dog = model.getDogById("1"); // Get the dog with ID "1" (Buddy)
        String response = controller.addToWishList(dog); // Add the dog to the wishlist
        
        // Check that the response message is correct and the wishlist contains one dog
        assertEquals("Buddy has been added to your wishlist.", response);
        assertEquals(1, controller.getWishList().size());
    }

    /**
     * Tests the addToWishList method for duplicate additions.
     * Ensures that the same dog cannot be added to the wishlist more than once.
     */
    @Test
    void testAddToWishListDuplicate() {
        Dog dog = model.getDogById("1"); // Get the dog with ID "1" (Buddy)
        controller.addToWishList(dog); // Add the dog to the wishlist the first time
        String response = controller.addToWishList(dog); // Attempt to add the same dog again
        
        // Check that the response message indicates the dog is already in the wishlist
        assertEquals("Buddy is already in your wishlist.", response);
        // Check that the wishlist still contains only one instance of the dog
        assertEquals(1, controller.getWishList().size());
    }

    /**
     * Tests the removeFromWishList method.
     * Ensures that a dog can be removed from the wishlist and the operation is successful.
     */
    @Test
    void testRemoveFromWishList() {
        Dog dog = model.getDogById("1"); // Get the dog with ID "1" (Buddy)
        controller.addToWishList(dog); // Add the dog to the wishlist
        String response = controller.removeFromWishList("1"); // Remove the dog from the wishlist
        
        // Check that the response message is correct and the wishlist is now empty
        assertEquals("Buddy was removed from wish list.", response);
        assertEquals(0, controller.getWishList().size());
    }

    /**
     * Tests the removeFromWishList method for a dog not found in the wishlist.
     * Ensures that attempting to remove a dog that isn't in the wishlist returns the correct message.
     */
    @Test
    void testRemoveFromWishListNotFound() {
        String response = controller.removeFromWishList("10"); // Attempt to remove a dog with a non-existent ID
        
        // Check that the response message indicates the dog could not be found
        assertEquals("10 could not be found in the wish list.", response);
    }

    /**
     * Tests the getDog method.
     * Ensures that the controller correctly retrieves a dog by its ID.
     */
    @Test
    void testGetDog() {
        Dog dog = controller.getDog("1"); // Retrieve the dog with ID "1" (Buddy)
        
        // Check that the retrieved dog is not null and has the expected name
        assertNotNull(dog);
        assertEquals("Buddy", dog.getName());
    }

    /**
     * Tests the setHomeList method.
     * Ensures that the home list is set and sorted by age (youngest to oldest).
     */
    @Test
    void testSetHomeList() {
        controller.setHomeList(); // Set the home list
        List<Dog> homeList = controller.getHomeList(); // Get the home list
        
        // Check that the home list is not null and contains all the dogs
        assertNotNull(homeList);
        assertEquals(3, homeList.size());
        // Check that the first dog in the list is the youngest
        assertEquals("Max", homeList.get(0).getName()); // Assuming "Max" is the youngest dog
    }
}
