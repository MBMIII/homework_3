package reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import list.ProducerList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadProducerFromJson implements Reader<ProducerList> {
    @Override
    public ProducerList read(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        ProducerList producerList;
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(String.valueOf(path)));
            String json = new String(encoded, StandardCharsets.UTF_8);
            producerList = objectMapper.readValue(json, ProducerList.class);
        } catch (IOException e) {
            producerList = new ProducerList();
        }
        return producerList;
    }
}
