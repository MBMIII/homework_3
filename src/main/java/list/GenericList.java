package list;

import java.util.List;

public interface GenericList<T> {
    void add(T t);

    List<T> getAll();

    T getById(int id);

    int size();

    boolean updateById(int souvenirId, T souvenir);

    boolean deleteById(int id);
}
