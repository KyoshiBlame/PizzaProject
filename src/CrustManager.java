import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CrustManager implements Manageable<Crust> {

    private List<Crust> crusts = new ArrayList<>();
    private ManagerIngredient ingredientManager;
    private PizzaManager pizzaManager;
    private Scanner sc = new Scanner(System.in);

    public CrustManager(ManagerIngredient ingredientManager, PizzaManager pizzaManager) {
        this.ingredientManager = ingredientManager;
        this.pizzaManager = pizzaManager;
    }

    @Override
    public void add() {

        System.out.print("Введите название бортика: ");
        String name = sc.nextLine();

        List<Ingredient> ingredients = chooseIngredients();
        Crust crust = new Crust(name, ingredients);

        System.out.println("Ограничения по пиццам:");
        System.out.println("[1] Разрешить только для выбранных пицц");
        System.out.println("[2] Запретить для выбранных пицц");
        System.out.println("[0] Без ограничений");
        System.out.print("Ваш выбор: ");
        String mode = sc.nextLine();

        if (mode.equals("1") || mode.equals("2")) {
            if (pizzaManager.getAll().isEmpty()) {
                System.out.println("Список пицц пуст. Ограничения не заданы.");
            } else {
                pizzaManager.printAll();
                System.out.print("Введите номера пицц через пробел: ");
                String[] parts = sc.nextLine().split(" ");

                for (String part : parts) {
                    try {
                        int idx = Integer.parseInt(part) - 1;
                        if (idx >= 0 && idx < pizzaManager.getAll().size()) {
                            String pizzaName = pizzaManager.getAll().get(idx).getName();
                            if (mode.equals("1")) {
                                crust.getAllowedPizzaNames().add(pizzaName);
                            } else {
                                crust.getForbiddenPizzaNames().add(pizzaName);
                            }
                        }
                    } catch (NumberFormatException ignored) { }
                }
            }
        }

        crusts.add(crust);
        System.out.println("Бортик добавлен: " + name + " | Цена: " + crust.calculatePrice());
    }

    @Override
    public void remove() {
        printAll();
        if (crusts.isEmpty()) return;

        System.out.print("Введите номера бортиков для удаления (через пробел): ");
        String[] parts = sc.nextLine().split(" ");

        for (int i = parts.length - 1; i >= 0; i--) {
            try {
                int idx = Integer.parseInt(parts[i]) - 1;
                if (idx >= 0 && idx < crusts.size()) {
                    crusts.remove(idx);
                }
            } catch (NumberFormatException ignored) { }
        }

        System.out.println("Удаление завершено");
    }

    @Override
    public void edit() {
        printAll();
        if (crusts.isEmpty()) return;

        System.out.print("Введите номер бортика для редактирования: ");
        int idx;
        try {
            idx = Integer.parseInt(sc.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод");
            return;
        }

        if (idx < 0 || idx >= crusts.size()) {
            System.out.println("Неверный индекс");
            return;
        }

        Crust crust = crusts.get(idx);

        System.out.println("[1] Изменить ингредиенты бортика");
        System.out.println("[2] Изменить ограничения по пиццам");
        System.out.println("[0] Назад");
        System.out.print("Выбор: ");
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                List<Ingredient> ing = chooseIngredients();
                crust.getIngredients().clear();
                crust.getIngredients().addAll(ing);
                System.out.println("Ингредиенты обновлены. Цена: " + crust.calculatePrice());
                break;

            case "2":
                crust.getAllowedPizzaNames().clear();
                crust.getForbiddenPizzaNames().clear();

                System.out.println("Ограничения по пиццам:");
                System.out.println("[1] Разрешить только для выбранных пицц");
                System.out.println("[2] Запретить для выбранных пицц");
                System.out.println("[0] Без ограничений");
                System.out.print("Ваш выбор: ");
                String mode = sc.nextLine();

                if (mode.equals("1") || mode.equals("2")) {
                    if (pizzaManager.getAll().isEmpty()) {
                        System.out.println("Список пицц пуст. Ограничения не заданы.");
                    } else {
                        pizzaManager.printAll();
                        System.out.print("Введите номера пицц через пробел: ");
                        String[] parts = sc.nextLine().split(" ");
                        for (String part : parts) {
                            try {
                                int pidx = Integer.parseInt(part) - 1;
                                if (pidx >= 0 && pidx < pizzaManager.getAll().size()) {
                                    String pizzaName = pizzaManager.getAll().get(pidx).getName();
                                    if (mode.equals("1")) crust.getAllowedPizzaNames().add(pizzaName);
                                    else crust.getForbiddenPizzaNames().add(pizzaName);
                                }
                            } catch (NumberFormatException ignored) { }
                        }
                    }
                }
                System.out.println("Ограничения обновлены");
                break;

            case "0":
                return;

            default:
                System.out.println("Неверный выбор");
        }
    }

    @Override
    public List<Crust> getAll() {
        return crusts;
    }

    public void printAll() {
        if (crusts.isEmpty()) {
            System.out.println("Список бортиков пуст");
            return;
        }

        for (int i = 0; i < crusts.size(); i++) {
            Crust c = crusts.get(i);
            System.out.println("[" + (i + 1) + "] " + c.getName() + " | Цена: " + c.calculatePrice());
        }
    }

    private List<Ingredient> chooseIngredients() {

        List<Ingredient> ingredients = new ArrayList<>();

        if (ingredientManager.getAll().isEmpty()) {
            System.out.println("Список ингредиентов пуст");
            return ingredients;
        }

        ingredientManager.printAll();
        System.out.print("Введите номера ингредиентов для бортика (через пробел): ");
        String[] parts = sc.nextLine().split(" ");

        for (String part : parts) {
            try {
                int idx = Integer.parseInt(part) - 1;
                if (idx >= 0 && idx < ingredientManager.getAll().size()) {
                    ingredients.add(ingredientManager.getAll().get(idx));
                }
            } catch (NumberFormatException ignored) { }
        }

        return ingredients;
    }
}
