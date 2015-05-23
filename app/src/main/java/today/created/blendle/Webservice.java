package today.created.blendle;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

import today.created.blendle.hal.HalItemPopular;
import today.created.blendle.hal.HalItemsPopular;
import today.created.blendle.hal.HalRoot;
import today.created.blendle.hal.Link;

/**
 * Created by jolandaverhoef on 21/05/15.
 * This class communicates with the Blendle Api (https://static.blendle.nl/api.json) using
 * HAL+JSON.
 */
public class Webservice {

    private final ObjectMapper mapper;

    public Webservice() {
        JsonFactory jsonFactory = new JsonFactory();
        mapper = new ObjectMapper(jsonFactory);
    }

    private HalRoot getHalRoot() throws IOException {
        return mapper.readValue(new URL("https://static.blendle.nl/api.json"), HalRoot.class);
    }

    public HalItemsPopular getPopularItems() throws IOException {
        String itemsPopularLink = getHalRoot().getItemsPopularLink();
        return mapper.readValue(new URL(itemsPopularLink), HalItemsPopular.class);
    }

    public HalItemsPopular getNextPopularItems(Link next) throws IOException {
        return mapper.readValue(new URL(next.href), HalItemsPopular.class);
    }

    public HalItemPopular getMostPopularItem() throws IOException {
        return getPopularItems().items().get(0);
    }
}
