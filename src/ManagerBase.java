import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerBase implements Manageable<Base> {

    private List<Base> listBase = new ArrayList<>();
    private final Base classicBase = new Base("Классическая", 300);
    private Scanner sc = new Scanner(System.in);

    public ManagerBase() {
        listBase.add(classicBase);
    }

    @Override
    public void add() {
        System.out.print("Введите название базы: ");
        String name = sc.nextLine();

        int price;
        while (true) {
            System.out.print("Введите цену базы: ");
            try {
                price = Integer.parseInt(sc.nextLine());

                if (!name.equalsIgnoreCase(classicBase.GetName())) {
                    int maxPrice = (int) (classicBase.GetPrice() * 1.2);
                    if (price > maxPrice) {
                        System.out.println("Цена не может быть больше чем 120% от классической (" + maxPrice + ")");
                        continue;
                    }
                }

                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }

        Base base = new Base(name, price);
        listBase.add(base);

        System.out.printf("База: %s успешно добавлена%n", name);
    }

    @Override
    public void remove() {
        printAll();
        System.out.print("Введите номера баз которые хотите удалить: ");
        String input = sc.nextLine();
        String[] parts = input.split(" ");

        for (int i = parts.length - 1; i >= 0; i--) {
            try {
                int index = Integer.parseInt(parts[i]) - 1;
                if (index == 0) {
                    System.out.println("Классичекую удалить базу нельзя");
                    continue;
                }
                if (index >= 0 && index < listBase.size()) {
                    listBase.remove(index);
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
        System.out.print("Введите номер базы для редактирования: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();
        if (index < 0 || index >= listBase.size()) {
            System.out.println("Неверный индекс");
            return;
        }

        if (index == 0) {
            System.out.println("Классическую базу нельзя изменить");
            return;
        }

        System.out.print("Новое название: ");
        String name = sc.nextLine();

        int price;
        while(true) {
            System.out.print("Новая стоимость: ");
            price = sc.nextInt();
            sc.nextLine();

            if (classicBase.GetPrice() * 1.2 >= price) {
                break;
            }

            System.out.println("Цена не может быть больше чем 120% от классической");
        }
        listBase.set(index, new Base(name, price));
        System.out.println("База обновлена ");
    }

    @Override
    public List<Base> getAll() {
        return new ArrayList<>(listBase);
    }

    public void printAll() {
        for (int i = 0; i < listBase.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, listBase.get(i).GetName());
        }
    }
}