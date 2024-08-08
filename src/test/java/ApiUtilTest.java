import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ApiUtil;
import model.Breed;

public class ApiUtilTest {
    
    private ApiUtil apiUtil;
    private Map<String, Breed> map;

    @BeforeEach
    public void setUp() {
        apiUtil = new ApiUtil();
    }

    @Test
    public void testParseBreeds() throws IOException {
        String json = "{\"data\":[{\"id\":\"1\",\"attributes\":{\"name\":\"Beagle\",\"description\":\"Friendly\",\"life\":{\"min\":10,\"max\":12},\"male_weight\":{\"min\":25,\"max\":32},\"female_weight\":{\"min\":23,\"max\":30},\"hypoallergenic\":false}}]}";
        map = apiUtil.parseBreeds(json);
        assertNotNull(map);
        assertEquals(1, map.size());
        Breed breed = map.get("Beagle");
        assertNotNull(breed);
        assertEquals("1", breed.id());
        assertEquals("Beagle", breed.name());
        assertEquals("Friendly", breed.description());
        assertEquals(10, breed.lifeMin());
        assertEquals(12, breed.lifeMax());
        assertEquals(25, breed.maleWeightMin());
        assertEquals(32, breed.maleWeightMax());
        assertEquals(23, breed.femaleWeightMin());
        assertEquals(30, breed.femaleWeightMax());
        assertFalse(breed.hypoallergenic());
    }

    @Test
    public void testParseBreedsEmpty() throws IOException {
        String empty = "{\"data\":[]}";

        map = apiUtil.parseBreeds(empty);
        assertNotNull(map);
        assertEquals(0, map.size());
    }

    @Test
    public void testParseBreedsInvalid() {
        String invalid = "test";

        assertThrows(IOException.class, () -> {
            apiUtil.parseBreeds(invalid);
        });
    }

    @Test
    public void testGetBreeds() {
        assertDoesNotThrow(() -> {
            String response = apiUtil.getBreeds();
            assertNotNull(response);
            assertFalse(response.isEmpty());
        });
    }

    @Test
    public void testApiWorks() {
        try {
            map = apiUtil.parseBreeds(apiUtil.getBreeds());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(map);
        assertEquals(10, map.size());
        assertEquals("Hokkaido", map.get("Hokkaido").name());
    }
}
