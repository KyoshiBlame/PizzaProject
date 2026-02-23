public class Main {

    public static void main(String[] args) {

        ManagerBase baseManager = new ManagerBase();
        ManagerIngredient ingredientManager = new ManagerIngredient();
        PizzaManager pizzaManager =
                new PizzaManager(baseManager, ingredientManager);

        CrustManager crustManager =
                new CrustManager(ingredientManager, pizzaManager);

        OrderManager orderManager = new OrderManager();

        Menu menu = new Menu(baseManager,
                ingredientManager,
                pizzaManager,
                crustManager,
                orderManager);

        menu.start();
    }
}
