package writer;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import list.ProducerList;

import java.nio.file.Paths;

public class WriteProducersToJson implements Writer<ProducerList> {
    @Override
    public void write(ProducerList producerList, String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get(path).toFile(), producerList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}