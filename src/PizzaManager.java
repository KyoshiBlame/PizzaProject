import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PizzaManager implements Manageable<Pizza>{
    private List<Pizza> ListPizza = new ArrayList<Pizza>();
    private ManagerBase bsm;
    private ManagerIngredient inm;
    private Scanner sc = new Scanner(System.in);

    public PizzaManager(ManagerBase bsm, ManagerIngredient inm) {
        this.ListPizza = new ArrayList<Pizza>();
        this.bsm = bsm;
        this.inm = inm;
    }

    public List<Pizza> findByIngredient(String ingredientName) {
        return ListPizza.stream()
                .filter(p -> p.getIngredients().stream()
                        .anyMatch(i ->
                                i.GetName().equalsIgnoreCase(ingredientName)))
                .collect(Collectors.toList());
    }

    private Base ChooseBase() {
        bsm.printAll();

        int index;

        while (true) {

            System.out.print("Введите номер базы: ");
            String input = sc.nextLine();

            try {
                index = Integer.parseInt(input) - 1;

                if (index >= 0 && index < bsm.getAll().size()) {
                    break;
                } else {
                    System.out.println("Неверный индекс");
                }

            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число");
            }
        }

        Base base = bsm.getAll().get(index);

        System.out.println("База успешно добавлена");

        return base;
    }

    private List<Ingredient> ChooseIngr() {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();

        inm.printAll();
        System.out.println("Введите номера ингредиентов которые хотите добавить: ");
        String input = sc.nextLine();
        String[] parts = input.split(" ");

        for (int i = parts.length - 1; i >= 0; i--) {
            try {
                int index = Integer.parseInt(parts[i]) - 1;
                if (index >= 0 && index < inm.getAll().size()) {
                    ingredients.add(inm.getAll().get(index));
                } else {
                    System.out.println("Неверный индекс: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Введено неверное значение: " + parts[i]);
            }
        };

        System.out.println("Ингредиетны успешо добавлены");

        return ingredients;
    }

    @Override
    public void add() {

        System.out.print("Введите название пиццы: ");
        String name = sc.nextLine();

        Base base = ChooseBase();
        List<Ingredient> ingredients = ChooseIngr();

        Pizza pizza = new Pizza(name, base, ingredients);
        ListPizza.add(pizza);

        System.out.printf("Пицца %s создана Цена: %d%n",
                name, pizza.getPrice());
    }

        public Pizza createForOrder() {

        System.out.print("Введите название пиццы: ");
        String name = sc.nextLine();

        Base base = ChooseBase();
        List<Ingredient> ingredients = ChooseIngr();

        Pizza pizza = new Pizza(name, base, ingredients);

        System.out.printf("Пицца %s создана Цена: %d%n",
                name, pizza.getPrice());

        return pizza;
    }

    @Override
    public void remove() {
        printAll();
        System.out.print("Введите номера пицц, которые хотите удалить: ");
        String input = sc.nextLine();
        String[] parts = input.split(" ");

        for (int i = parts.length - 1; i >= 0; i--) {
            try {
                int index = Integer.parseInt(parts[i]) - 1;
                if (index >= 0 && index < ListPizza.size()) {
                    ListPizza.remove(index);
                } else {
                    System.out.println("Неверный индекс: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Пропущено неверное значение: " + parts[i]);
            }
        }
    }


    @Override
    public void edit() {

        printAll();
        System.out.print("Введите номер пиццы, которую хотите редактировать: ");

        try {
            int index = Integer.parseInt(sc.nextLine()) - 1;

            if (index >= 0 && index < ListPizza.size()) {

                Pizza pizza = ListPizza.get(index);

                System.out.println("\n[1] Изменить базу");
                System.out.println("[2] Изменить ингредиенты");
                System.out.println("[0] Назад");

                String choice = sc.nextLine();

                switch (choice) {

                    case "1":
                        editBase(pizza);
                        break;

                    case "2":
                        editIngredients(pizza);
                        break;

                    case "0":
                        return;

                    default:
                        System.out.println("Неверный выбор");
                }

            } else {
                System.out.println("Неверный индекс");
            }

        } catch (NumberFormatException e) {
            System.out.println("Введите корректное число");
        }
    }

    private void editBase(Pizza pizza) {

        System.out.println("\nТекущая база: "
                + pizza.getBase().GetName());

        Base newBase = ChooseBase();

        pizza.setBase(newBase);

        System.out.println("База успешно изменена");
        System.out.println("Новая цена: " + pizza.getPrice());
    }

    private void editIngredients(Pizza pizza) {

        while (true) {

            System.out.println("\nТекущие ингредиенты:");

            List<Ingredient> ingredients = pizza.getIngredients();

            for (int i = 0; i < ingredients.size(); i++) {
                System.out.printf("[%d] %s%n", i + 1,
                        ingredients.get(i).GetName());
            }

            System.out.println("\n[1] Удалить");
            System.out.println("[2] Добавить");
            System.out.println("[0] Назад");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    System.out.print("Введите номер: ");
                    try {
                        int index = Integer.parseInt(sc.nextLine()) - 1;

                        if (index >= 0 && index < ingredients.size()) {
                            ingredients.remove(index);
                            System.out.println("Удалено");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка ввода");
                    }
                    break;

                case "2":
                    List<Ingredient> newOnes = ChooseIngr();
                    ingredients.addAll(newOnes);
                    break;

                case "0":
                    return;
            }
        }
    }

    @Override
    public List<Pizza> getAll() {
        return ListPizza;
    }

    public void printAll() {

        if (ListPizza.isEmpty()) {
            System.out.println("Список пицц пуст");
            return;
        }

        for (int i = 0; i < ListPizza.size(); i++) {

            Pizza p = ListPizza.get(i);

            System.out.printf("\n[%d] %s%n", i + 1, p.getName());
            System.out.println("База: " + p.getBase().GetName());

            System.out.println("Ингредиенты:");

            List<Ingredient> ingredients = p.getIngredients();

            if (ingredients.isEmpty()) {
                System.out.println("  (нет ингредиентов)");
            } else {
                for (Ingredient ingr : ingredients) {
                    System.out.println("  - " + ingr.GetName());
                }
            }

            System.out.println("Цена: " + p.getPrice());
            System.out.println("----------------------------------");
        }
    }
}
