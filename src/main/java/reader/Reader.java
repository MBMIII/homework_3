package reader;

import java.io.IOException;

public interface Reader<T>{
    public T read(String path) throws IOException;
}
