package writer;

public interface Writer<T> {
    void write(T t, String path);
}