package writer;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import list.SouvenirList;

import java.nio.file.Paths;

public class WriteSouvenirToJson implements Writer<SouvenirList> {
    @Override
    public void write(SouvenirList souvenirList, String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get(path).toFile(), souvenirList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}