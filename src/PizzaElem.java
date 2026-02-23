import java.util.UUID;

abstract class PizzaElem implements HasId, Priced {

    private final UUID id = UUID.randomUUID();
    private String name;
    private int price;

    public PizzaElem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String GetName() {
        return this.name;
    }

    public int GetPrice() {
        return this.price;
    }

    @Override
    public int getPrice() {
        return price;
    }
}