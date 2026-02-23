import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Crust implements HasId {

    private final UUID id = UUID.randomUUID();

    private String name;
    private List<Ingredient> ingredients = new ArrayList<>();
    private Set<String> allowedPizzaNames = new HashSet<>();
    private Set<String> forbiddenPizzaNames = new HashSet<>();

    public Crust(String name, List<Ingredient> ingredients) {
        this.name = name;
        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Set<String> getAllowedPizzaNames() {
        return allowedPizzaNames;
    }

    public Set<String> getForbiddenPizzaNames() {
        return forbiddenPizzaNames;
    }

    public boolean isAllowedFor(Pizza pizza) {

        if (!allowedPizzaNames.isEmpty()) {
            return allowedPizzaNames.contains(pizza.getName());
        }

        if (!forbiddenPizzaNames.isEmpty()) {
            return !forbiddenPizzaNames.contains(pizza.getName());
        }

        return true;
    }

    public int calculatePrice() {
        return ingredients.stream()
                .mapToInt(Ingredient::GetPrice)
                .sum();
    }
}
