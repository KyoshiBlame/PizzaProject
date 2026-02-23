import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pizza implements HasId, Priced {

    private final UUID id = UUID.randomUUID();

    private String name;
    private Base base;
    private List<Ingredient> ingredients;
    private PizzaSize size = PizzaSize.SMALL;

        private Crust crust;

    private Integer customIngredientsPrice;

    public Pizza(String name, Base base, List<Ingredient> ingredients) {

        if (base == null)
            throw new IllegalArgumentException("Пицца должна иметь основу");

        this.name = name;
        this.base = base;
        this.ingredients = new ArrayList<>(ingredients);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        if (base == null) {
            throw new IllegalArgumentException("Пицца должна иметь основу");
        }
        this.base = base;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setSize(PizzaSize size) {
        this.size = size;
    }

    public PizzaSize getSize() {
        return size;
    }

    public Crust getCrust() {
        return crust;
    }

    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    public int getIngredientsPrice() {
        if (customIngredientsPrice != null) {
            return customIngredientsPrice;
        }

        int total = 0;
        for (Ingredient i : ingredients) {
            total += i.GetPrice();
        }
        return total;
    }

    public void setCustomIngredientsPrice(Integer price) {
        this.customIngredientsPrice = price;
    }

    public Pizza copy() {
        Pizza p = new Pizza(this.name, this.base, this.ingredients);
        p.setSize(this.size);
        p.setCrust(this.crust);
        p.setCustomIngredientsPrice(this.customIngredientsPrice);
        return p;
    }

    public static Pizza makeHalfHalf(Pizza a, Pizza b, Base base, PizzaSize size) {

        List<Ingredient> combined = new ArrayList<>();
        combined.addAll(a.getIngredients());
        combined.addAll(b.getIngredients());

        Pizza combo = new Pizza("1/2 " + a.getName() + " + 1/2 " + b.getName(), base, combined);
        combo.setSize(size);

        int halfIngredients = (a.getIngredientsPrice() + b.getIngredientsPrice()) / 2;
        combo.setCustomIngredientsPrice(halfIngredients);

        return combo;
    }

    @Override
    public int getPrice() {
        return calculatePrice();
    }

    public int calculatePrice() {

        int total = base.GetPrice() + getIngredientsPrice();

        if (crust != null) {
            total += crust.calculatePrice();
        }

        return (int)(total * size.getCoef());
    }
}
