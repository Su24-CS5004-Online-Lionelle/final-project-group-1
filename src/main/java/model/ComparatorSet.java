package model;
import java.util.*;
import java.util.function.*;

public class ComparatorSet {

    public static Comparator<Dog> getComparator(String sortOn) {
        switch (sortOn.toLowerCase()) {
            case "name":
                return Comparator.comparing(Dog::getName);
            case "sex":
                return Comparator.comparing(Dog::getSex);
            case "breed":
                return Comparator.comparing(dog -> dog.getBreed().name());
            case "age":
                return Comparator.comparingInt(Dog::getAge);
            case "weight":
                return Comparator.comparingDouble(Dog::getWeight);
            case "price":
                return Comparator.comparingDouble(Dog::getPrice);
            default:
                return null;
        }
    }

    public static Predicate<Dog> getPredicate(String filter, Object value) {
        switch (filter.toLowerCase()) {
            case "name":
                return dog -> dog.getName().contains((String) value);
            case "sex":
                return dog -> dog.getSex().equalsIgnoreCase((String) value);
            case "breed":
                return dog -> dog.getBreed().name().contains((String) value);
            case "age":
                return dog -> dog.getAge() == (int) value;
            case "weight":
                return dog -> dog.getWeight() == (double) value;
            case "price":
                return dog -> dog.getPrice() == (double) value;
            default:
                return null;
        }
    }
}
