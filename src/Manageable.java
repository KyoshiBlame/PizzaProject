import java.util.List;

public interface Manageable<T> {
    void add ();
    void remove();
    void edit();
    List<T> getAll();
}
