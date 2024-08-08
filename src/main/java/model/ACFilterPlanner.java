package model;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class ACFilterPlanner {
    /** The stream of filtered Dog objects. */
    private Stream<Dog> filtered;
    /** The list of Dog objects to be filtered/sorted. */
    private List<Dog> dogs;

    /**
     * Constructs a new ACFilterPlanner with the given list of dogs.
     *
     * @param dogs the list of Dog objects to be filtered/sorted.
     */
    public ACFilterPlanner(List<Dog> dogs) {
        this.dogs = dogs;
        this.filtered = dogs.stream();
    }

    /**
     * Filters and sorts the list of dogs.
     *
     * @param nameOn
     * @param nameFilter
     * @param sexOn
     * @param sexFilter
     * @param breedOn
     * @param breedFilter
     * @param ageOn
     * @param ageFilter
     * @param weightOn
     * @param weightFilter
     * @param priceOn
     * @param priceFilter
     * @param sortOn
     * @param ascending
     * @return the stream of filtered/sorted dogs
     */
    public Stream<Dog> filter(boolean nameOn, String nameFilter,
                              boolean sexOn, String sexFilter,
                              boolean breedOn, String breedFilter,
                              boolean ageOn, String ageFilter,
                              boolean weightOn, String weightFilter,
                              boolean priceOn, String priceFilter,
                              String sortOn, boolean ascending) {

        filtered = dogs.stream();

        applyFilter(nameOn, "name", nameFilter);
        applyFilter(sexOn, "sex", sexFilter);
        applyFilter(breedOn, "breed", breedFilter);
        applyFilter(ageOn, "age", ageFilter);
        applyFilter(weightOn, "weight", weightFilter);
        applyFilter(priceOn, "price", priceFilter);

        if (sortOn != null && !sortOn.isEmpty()) {
            Comparator<Dog> comparator = ComparatorSet.getComparator(sortOn);
            if (comparator != null) {
                filtered = ascending ? filtered.sorted(comparator) : filtered.sorted(comparator.reversed());
            }
        }

        return filtered;
    }

    /**
     *
     *
     * @param filterOn
     * @param field
     * @param value
     */
    public void applyFilter(boolean filterOn, String field, Object value) {
        if (filterOn) {
            if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                throw new IllegalArgumentException(field + " filter cannot be null or empty");
            }
            Predicate<Dog> predicate = ComparatorSet.getPredicate(field, value);
            filtered = filtered.filter(predicate);
        }
    }

}
