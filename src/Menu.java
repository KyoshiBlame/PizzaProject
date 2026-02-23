import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private ManagerBase baseManager;
    private ManagerIngredient ingredientManager;
    private PizzaManager pizzaManager;
    private CrustManager crustManager;
    private OrderManager orderManager;

    private Scanner sc = new Scanner(System.in);

    public Menu(ManagerBase baseManager,
                ManagerIngredient ingredientManager,
                PizzaManager pizzaManager,
                CrustManager crustManager,
                OrderManager orderManager) {

        this.baseManager = baseManager;
        this.ingredientManager = ingredientManager;
        this.pizzaManager = pizzaManager;
        this.crustManager = crustManager;
        this.orderManager = orderManager;
    }

    public void start() {

        while (true) {

            printMainMenu();

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    baseMenu();
                    break;

                case "2":
                    ingredientMenu();
                    break;

                case "3":
                    pizzaMenu();
                    break;

                case "4":
                    crustMenu();
                    break;

                case "5":
                    orderMenu();
                    break;

                case "6":
                    filterMenu();
                    break;

                case "0":
                    System.out.println("Выход из программы");
                    return;

                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n====== 🍕 PIZZA CONSTRUCTOR ======");
        System.out.println("[1] Управление базами");
        System.out.println("[2] Управление ингредиентами");
        System.out.println("[3] Управление пиццами");
        System.out.println("[4] Управление бортиками");
        System.out.println("[5] Заказы");
        System.out.println("[6] Фильтры");
        System.out.println("[0] Выход");
        System.out.print("Выберите действие: ");
    }

    private void baseMenu() {
        manage(baseManager, "Базы");
    }

    private void ingredientMenu() {
        manage(ingredientManager, "Ингредиенты");
    }

    private void pizzaMenu() {
        manage(pizzaManager, "Пиццы");
    }

    private void crustMenu() {
        manage(crustManager, "Бортики");
    }

    private void orderMenu() {

        while (true) {
            System.out.println("\n--- Заказы ---");
            System.out.println("[1] Создать заказ из готовых пицц");
            System.out.println("[2] Создать заказ: половина A + половина B");
            System.out.println("[3] Создать заказ с новой пиццей");
            System.out.println("[4] Показать все заказы");
            System.out.println("[0] Назад");
            System.out.print("Выберите действие: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    createOrderFromExistingPizzas();
                    break;
                case "2":
                    createOrderHalfHalf();
                    break;
                case "3":
                    createOrderWithNewPizza();
                    break;
                case "4":
                    orderManager.printAll();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void createOrderWithNewPizza() {

        if (baseManager.getAll().isEmpty()) {
            System.out.println("Сначала добавьте хотя бы одну базу");
            return;
        }

        if (ingredientManager.getAll().isEmpty()) {
            System.out.println("Сначала добавьте хотя бы один ингредиент");
            return;
        }

        List<Pizza> pizzas = new ArrayList<>();

        while (true) {
            Pizza p = pizzaManager.createForOrder();
            p.setSize(chooseSize());
            pizzas.add(p);

            System.out.print("Добавить ещё пиццу в заказ? (y/n): ");
            String yn = sc.nextLine();
            if (!yn.equalsIgnoreCase("y")) {
                break;
            }
        }

        applyCrustsToPizzas(pizzas);

        Order order = buildOrder(pizzas);
        orderManager.addOrder(order);

        System.out.println("Заказ создан. Номер: " + order.getNumber());
        System.out.println("Сумма: " + order.getTotalPrice());
    }

    private void createOrderFromExistingPizzas() {

        if (pizzaManager.getAll().isEmpty()) {
            System.out.println("Сначала создайте хотя бы одну пиццу");
            return;
        }

        pizzaManager.printAll();
        System.out.print("Введите номера пицц для заказа (через пробел): ");
        String input = sc.nextLine();
        String[] parts = input.split(" ");

        List<Pizza> pizzas = new ArrayList<>();

        for (String part : parts) {
            try {
                int idx = Integer.parseInt(part) - 1;
                if (idx >= 0 && idx < pizzaManager.getAll().size()) {
                                Pizza p = pizzaManager.getAll().get(idx).copy();
                    p.setSize(chooseSize());
                    pizzas.add(p);
                }
            } catch (NumberFormatException ignored) { }
        }

        if (pizzas.isEmpty()) {
            System.out.println("Не выбрано ни одной пиццы");
            return;
        }

        applyCrustsToPizzas(pizzas);

        Order order = buildOrder(pizzas);
        orderManager.addOrder(order);

        System.out.println("Заказ создан. Номер: " + order.getNumber());
        System.out.println("Сумма: " + order.getTotalPrice());
    }

     private void createOrderHalfHalf() {

        if (pizzaManager.getAll().size() < 2) {
            System.out.println("Нужно минимум 2 пиццы в системе");
            return;
        }

        pizzaManager.printAll();
        System.out.print("Введите номер пиццы A: ");
        int aIdx = readIndex(pizzaManager.getAll().size());
        if (aIdx == -1) return;

        System.out.print("Введите номер пиццы B: ");
        int bIdx = readIndex(pizzaManager.getAll().size());
        if (bIdx == -1) return;

        Pizza a = pizzaManager.getAll().get(aIdx);
        Pizza b = pizzaManager.getAll().get(bIdx);

        System.out.println("Выберите основу для комбинированной пиццы (одна на всю пиццу):");
        Base base = chooseBaseForOrder();
        if (base == null) return;

        PizzaSize size = chooseSize();

        Pizza combo = Pizza.makeHalfHalf(a, b, base, size);

        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(combo);

        applyCrustsToPizzas(pizzas);

        Order order = buildOrder(pizzas);
        orderManager.addOrder(order);

        System.out.println("Заказ создан. Номер: " + order.getNumber());
        System.out.println("Пицца: " + combo.getName());
        System.out.println("Сумма: " + order.getTotalPrice());
    }

    private Base chooseBaseForOrder() {
        if (baseManager.getAll().isEmpty()) {
            System.out.println("Нет баз. Сначала добавьте базу.");
            return null;
        }
        baseManager.printAll();
        System.out.print("Введите номер базы: ");
        int idx = readIndex(baseManager.getAll().size());
        if (idx == -1) return null;
        return baseManager.getAll().get(idx);
    }

    private void applyCrustsToPizzas(List<Pizza> pizzas) {

        if (crustManager.getAll().isEmpty()) {
            return;
        }

        System.out.print("Добавить бортики? (y/n): ");
        String yn = sc.nextLine();
        if (!yn.equalsIgnoreCase("y")) {
            return;
        }

        System.out.print("Один общий бортик для всех пицц? (y/n): ");
        String common = sc.nextLine();

        if (common.equalsIgnoreCase("y")) {
            Crust crust = chooseCrustForPizza(pizzas.get(0));
            if (crust == null) return;

            for (Pizza p : pizzas) {
                p.setCrust(crust);
            }

        } else {
            for (Pizza p : pizzas) {
                System.out.println("\nПицца: " + p.getName());
                Crust crust = chooseCrustForPizza(p);
                p.setCrust(crust);
            }
        }
    }

    private Crust chooseCrustForPizza(Pizza pizza) {

        crustManager.printAll();
        System.out.print("Введите номер бортика (0 - без бортика): ");
        String s = sc.nextLine();

        int idx;
        try {
            idx = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод");
            return null;
        }

        if (idx == 0) return null;

        idx = idx - 1;
        if (idx < 0 || idx >= crustManager.getAll().size()) {
            System.out.println("Неверный индекс");
            return null;
        }

        Crust crust = crustManager.getAll().get(idx);

        if (!crust.isAllowedFor(pizza)) {
            System.out.println("Этот бортик нельзя использовать с пиццей: " + pizza.getName());
            return null;
        }

        return crust;
    }

    private Order buildOrder(List<Pizza> pizzas) {

        System.out.print("Комментарий (можно пусто): ");
        String comment = sc.nextLine();

        System.out.print("Сделать отложенным? (y/n): ");
        String yn = sc.nextLine();
        java.time.LocalDateTime scheduled = null;

        if (yn.equalsIgnoreCase("y")) {
            try {
                System.out.print("Дата (yyyy-MM-dd): ");
                String d = sc.nextLine();
                System.out.print("Время (HH:mm): ");
                String t = sc.nextLine();
                scheduled = java.time.LocalDateTime.parse(d + "T" + t + ":00");
            } catch (Exception e) {
                System.out.println("Неверная дата/время, заказ будет обычным");
                scheduled = null;
            }
        }

        return new Order(pizzas, comment, scheduled);
    }

    private int readIndex(int size) {
        try {
            int idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx < 0 || idx >= size) {
                System.out.println("Неверный индекс");
                return -1;
            }
            return idx;
        } catch (NumberFormatException e) {
            System.out.println("Введите корректное число");
            return -1;
        }
    }

    private PizzaSize chooseSize() {
        while (true) {
            System.out.println("Размер: [1] маленькая [2] средняя [3] большая");
            System.out.print("Выберите: ");
            String s = sc.nextLine();
            switch (s) {
                case "1": return PizzaSize.SMALL;
                case "2": return PizzaSize.MEDIUM;
                case "3": return PizzaSize.LARGE;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void filterMenu() {

        while (true) {
            System.out.println("\n--- Фильтры ---");
            System.out.println("[1] Пиццы, содержащие ингредиент");
            System.out.println("[2] Заказы за дату");
            System.out.println("[0] Назад");
            System.out.print("Выберите действие: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    filterPizzasByIngredient();
                    break;
                case "2":
                    filterOrdersByDate();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void filterPizzasByIngredient() {
        System.out.print("Введите название ингредиента: ");
        String name = sc.nextLine();

        List<Pizza> res = pizzaManager.findByIngredient(name);

        if (res.isEmpty()) {
            System.out.println("Ничего не найдено");
            return;
        }

        System.out.println("Найденные пиццы:");
        for (Pizza p : res) {
            System.out.println("- " + p.getName() + " (" + p.getPrice() + ")");
        }
    }

    private void filterOrdersByDate() {
        System.out.print("Введите дату (yyyy-MM-dd): ");
        String d = sc.nextLine();
        try {
            java.time.LocalDate date = java.time.LocalDate.parse(d);
            List<Order> res = orderManager.findByDate(date);
            if (res.isEmpty()) {
                System.out.println("Заказов не найдено");
                return;
            }
            for (Order o : res) {
                System.out.println("Заказ #" + o.getNumber() + " | " + o.getCreatedAt() + " | " + o.getTotalPrice());
            }
        } catch (Exception e) {
            System.out.println("Неверная дата");
        }
    }

    private <T> void manage(Manageable<T> manager, String title) {

        while (true) {

            System.out.printf("\n--- %s ---%n", title);
            System.out.println("[1] Добавить");
            System.out.println("[2] Удалить");
            System.out.println("[3] Редактировать");
            System.out.println("[4] Показать все");
            System.out.println("[0] Назад");
            System.out.print("Выберите действие: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    manager.add();
                    break;

                case "2":
                    manager.remove();
                    break;

                case "3":
                    manager.edit();
                    break;

                case "4":
                    if (manager instanceof PizzaManager) {
                        ((PizzaManager) manager).printAll();
                    } else if (manager instanceof ManagerBase) {
                        ((ManagerBase) manager).printAll();
                    } else if (manager instanceof ManagerIngredient) {
                        ((ManagerIngredient) manager).printAll();
                    } else if (manager instanceof CrustManager) {
                        ((CrustManager) manager).printAll();
                    }
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Неверный выбор");
            }
        }
    }
}
