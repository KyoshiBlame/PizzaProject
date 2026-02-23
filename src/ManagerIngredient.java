import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerIngredient implements Manageable{
    private List<Ingredient> ListIngr;
    private Scanner sc = new Scanner(System.in);

    public ManagerIngredient() {
        this.ListIngr = new ArrayList<Ingredient>();
    }

    @Override
    public void add() {
        System.out.print("Введите название ингредиента: ");
        String name = sc.nextLine();

        int price;
        while (true) {
            System.out.print("Введите цену: ");
            try {
                price = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }

        Ingredient ingr = new Ingredient(name, price);
        ListIngr.add(ingr);
        System.out.printf("Ингредиет: %s успешно добавлен%n", name);
    }

    @Override
    public void remove() {
        printAll();
        System.out.print("Введите номера ингредиентов которые хотите удалить: ");
        String input = sc.nextLine();
        String[] parts = input.split(" ");

        for (int i = parts.length - 1; i >= 0; i--) {
            try {
                int index = Integer.parseInt(parts[i]) - 1;
                if (index >= 0 && index < ListIngr.size()) {
                    ListIngr.remove(index);
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
        System.out.print("Введите номер ингредиента для редактирования: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();
        if (index < 0 || index >= ListIngr.size()) {
            System.out.println("Неверный индекс");
            return;
        }
        System.out.print("Новое название: ");
        String name = sc.nextLine();

        int price = sc.nextInt();
        sc.nextLine();
        ListIngr.set(index, new Ingredient(name, price));
        System.out.println("Ингредиет обновлена");
    }

    @Override
    public List<Ingredient> getAll() {
        return ListIngr;
    }

    public void printAll() {
        for (int i = 0; i < ListIngr.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, ListIngr.get(i).GetName());
        }
    }
}
