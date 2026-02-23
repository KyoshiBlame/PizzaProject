import java.util.Comparator;

public class PizzaComparators {

    public static final Comparator<Pizza> BY_PRICE =
            Comparator.comparingInt(Pizza::calculatePrice);

    public static final Comparator<Pizza> BY_NAME =
            Comparator.comparing(Pizza::getName);
}