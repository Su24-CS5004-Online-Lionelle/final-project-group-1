public class Dog {

    /** the unique identifier of the dog */
    private String id;

    /** the name of the dog */
    private String name;

    /** the sex of the dog ('m' or 'f', case insensitive) */
    private String sex;

    /** the breed of the dog */
    private Breed breed;

    /** the age of the dog in years */
    private int age;

    /** the weight of the dog in pounds */
    private double weight;

    /** the price of the dog */
    private double price;

    /** the readiness for adoption status of the dog */
    private boolean isReady;

      /**
     * Constructs a new Dog with the specified attributes, defaulting price to 0.00 and readiness for adoption to false.
     *
     * @param id     the unique identifier of the dog
     * @param name   the name of the dog
     * @param sex    the sex of the dog ('m' or 'f', case insensitive)
     * @param breed  the breed of the dog
     * @param age    the age of the dog in years
     * @param weight the weight of the dog in pounds
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Dog(String id, String name, String sex, Breed breed, int age, double weight) {
        this(id, name, sex, breed, age, weight, 0.00, false);
    }

    /**
     * Constructs a new Dog with the specified attributes.
     *
     * @param id      the unique identifier of the dog
     * @param name    the name of the dog
     * @param sex     the sex of the dog ('m' or 'f', case insensitive)
     * @param breed   the breed of the dog
     * @param age     the age of the dog in years
     * @param weight  the weight of the dog in pounds
     * @param price   the price of the dog
     * @param isReady the readiness for adoption status of the dog
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Dog(String id, String name, String sex, Breed breed, int age, double weight, double price, boolean isReady) throws IllegalArgumentException {
        if (id == null || name == null || sex == null || breed == null) {
            throw new IllegalArgumentException("Fields cannot be null.");
        }
        if (!(sex.equalsIgnoreCase("m") || sex.equalsIgnoreCase("f"))) {
            throw new IllegalArgumentException("Sex must be either m or f, case insensitive.");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("Dog age must be greater than zero.");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Dog weight must be greater than zero.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price of dog cannot be negative.");
        }
        this.id = id;
        this.name = name;
        this.sex = sex.toLowerCase();
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.price = price;
        this.isReady = isReady;
    }

    /**
     * Returns the ID of the dog.
     *
     * @return the ID of the dog
     */
    public String getID() {
        return this.id;
    }

    /**
     * Returns the name of the dog.
     *
     * @return the name of the dog
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the sex of the dog.
     *
     * @return the sex of the dog
     */
    public String getSex() {
        return this.sex;
    }

    /**
     * Returns the breed of the dog.
     *
     * @return the breed of the dog
     */
    public Breed getBreed() {
        return this.breed;
    }

    /**
     * Returns the age of the dog.
     *
     * @return the age of the dog
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Returns the weight of the dog.
     *
     * @return the weight of the dog
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Returns the price of the dog.
     *
     * @return the price of the dog
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Returns the readiness status of the dog.
     *
     * @return the readiness status of the dog
     */
    public boolean getIsReady() {
        return this.isReady;
    }

    /**
     * Changes the name of the dog.
     *
     * @param name the new name of the dog
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * Changes the age of the dog.
     *
     * @param age the new age of the dog
     * @throws IllegalArgumentException if the new age is not greater than the current age
     */
    public void changeAge(int age) throws IllegalArgumentException {
        if (age <= this.age) {
            throw new IllegalArgumentException("The new dog age must be older than the currently assigned one.");
        }
        this.age = age;
    }

    /**
     * Changes the weight of the dog.
     *
     * @param weight the new weight of the dog
     * @throws IllegalArgumentException if the weight is not greater than zero
     */
    public void changeWeight(double weight) throws IllegalArgumentException {
        if (weight <= 0) {
            throw new IllegalArgumentException("Dog weight must be greater than zero.");
        }
        this.weight = weight;
    }

    /**
     * Changes the price of the dog.
     *
     * @param price the new price of the dog
     * @throws IllegalArgumentException if the price is negative
     */
    public void changePrice(double price) throws IllegalArgumentException {
        if (price < 0) {
            throw new IllegalArgumentException("Price of dog cannot be negative.");
        }
        this.price = price;
    }

    /**
     * Changes the readiness status of the dog.
     *
     * @param isReady the new readiness status of the dog
     */
    public void changeIsReady(boolean isReady) {
        this.isReady = isReady;
    }
}
