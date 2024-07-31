import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class ACFilterPlanner {
    private Stream<Dog> filtered;
    private List<Dog> dogs;

    public ACFilterPlanner(List<Dog> dogs) {
        this.dogs = dogs;
        this.filtered = dogs.stream();
    }

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
