import java.util.List;

class ACControllerTest {
    private ACController controller;
    private AdoptionCenterModel model;

    @BeforeEach
    void setUp() {
        model = new AdoptionCenterModel();
        controller = new ACController(model);

        // Adding some test dogs to the model
        Dog dog1 = new Dog("1", "Buddy", "Male", "Labrador", 3, 30.0, "labrador.jpg", 500, true);
        Dog dog2 = new Dog("2", "Lucy", "Female", "Beagle", 5, 20.0, "beagle.jpg", 400, true);
        Dog dog3 = new Dog("3", "Max", "Male", "Poodle", 2, 15.0, "poodle.jpg", 600, true);

        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);
    }

    @Test
    void testSetNameSearch() {
        controller.setNameSearch("Buddy");
        controller.setResultList();
        List<Dog> result = controller.getResultList();
        
        assertEquals(1, result.size());
        assertEquals("Buddy", result.get(0).getName());
    }

    @Test
    void testAddToWishList() {
        Dog dog = model.getDogById("1");
        String response = controller.addToWishList(dog);
        
        assertEquals("Buddy has been added to your wishlist.", response);
        assertEquals(1, controller.getWishList().size());
    }

    @Test
    void testAddToWishListDuplicate() {
        Dog dog = model.getDogById("1");
        controller.addToWishList(dog);  // Adding the first time
        String response = controller.addToWishList(dog);  // Attempting to add again
        
        assertEquals("Buddy is already in your wishlist.", response);
        assertEquals(1, controller.getWishList().size());
    }

    @Test
    void testRemoveFromWishList() {
        Dog dog = model.getDogById("1");
        controller.addToWishList(dog);
        String response = controller.removeFromWishList("1");
        
        assertEquals("Buddy was removed from wish list.", response);
        assertEquals(0, controller.getWishList().size());
    }

    @Test
    void testRemoveFromWishListNotFound() {
        String response = controller.removeFromWishList("10");  // ID that doesn't exist
        
        assertEquals("10 could not be found in the wish list.", response);
    }

    @Test
    void testGetDog() {
        Dog dog = controller.getDog("1");
        assertNotNull(dog);
        assertEquals("Buddy", dog.getName());
    }

    @Test
    void testSetHomeList() {
        controller.setHomeList();
        List<Dog> homeList = controller.getHomeList();
        
        assertNotNull(homeList);
        assertEquals(3, homeList.size());
        assertEquals("Max", homeList.get(0).getName());  // Assuming "Max" is the youngest dog
    }
}
