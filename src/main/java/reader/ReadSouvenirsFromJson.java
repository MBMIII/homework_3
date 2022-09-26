package reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import list.SouvenirList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadSouvenirsFromJson implements Reader<SouvenirList> {

    @Override
    public SouvenirList read(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        SouvenirList souvenirList;
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(String.valueOf(path)));
            String json = new String(encoded, StandardCharsets.UTF_8);
            souvenirList = objectMapper.readValue(json, SouvenirList.class);
        } catch (IOException e) {
            souvenirList = new SouvenirList();
        }
        return souvenirList;
    }
}
