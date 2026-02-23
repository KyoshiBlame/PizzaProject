import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderManager {

    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getAll() {
        return orders;
    }

    public List<Order> findByDate(LocalDate date) {
        return orders.stream()
                .filter(o -> o.getCreatedAt().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public void printAll() {
        if (orders.isEmpty()) {
            System.out.println("Список заказов пуст");
            return;
        }

        for (Order o : orders) {
            System.out.println("----------------------------------");
            System.out.println("Заказ #" + o.getNumber());
            System.out.println("Время: " + o.getCreatedAt());
            System.out.println("Пицц: " + o.getPizzas().size());
            System.out.println("Сумма: " + o.getTotalPrice());
            if (o.getComment() != null && !o.getComment().isBlank()) {
                System.out.println("Комментарий: " + o.getComment());
            }
            if (o.getScheduledFor() != null) {
                System.out.println("Отложен на: " + o.getScheduledFor());
            }
        }
        System.out.println("----------------------------------");
    }
}
