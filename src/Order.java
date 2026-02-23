import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Order implements HasId {

    private final UUID id = UUID.randomUUID();
    private static final AtomicInteger counter = new AtomicInteger(1);

    private int number;
    private List<Pizza> pizzas = new ArrayList<>();
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime scheduledFor;

    public Order(List<Pizza> pizzas,
                 String comment,
                 LocalDateTime scheduledFor) {

        this.number = counter.getAndIncrement();
        this.pizzas = pizzas;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
        this.scheduledFor = scheduledFor;
    }

    public UUID getId() {
        return id;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getScheduledFor() {
        return scheduledFor;
    }

    public int getTotalPrice() {
        return pizzas.stream()
                .mapToInt(Pizza::calculatePrice)
                .sum();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getNumber() {
        return number;
    }
}