public enum PizzaSize {
    SMALL(1.0),
    MEDIUM(1.25),
    LARGE(1.5);

    private final double coef;

    PizzaSize(double coef) {
        this.coef = coef;
    }

    public double getCoef() {
        return coef;
    }
}