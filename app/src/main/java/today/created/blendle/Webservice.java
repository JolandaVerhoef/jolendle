package today.created.blendle;

import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import today.created.blendle.hal.HalContent;
import today.created.blendle.hal.HalItemsPopular;
import today.created.blendle.hal.HalRoot;

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

    public HalRoot getHalRoot() throws IOException {
        return mapper.readValue(new URL("https://static.blendle.nl/api.json"), HalRoot.class);
    }

    public void getMostPopularItem() throws IOException {
        String itemsPopularLink = getHalRoot().getItemsPopularLink();
        HalItemsPopular halItemsPopular = mapper.readValue(new URL(itemsPopularLink), HalItemsPopular.class);
        final List<HalContent> body = halItemsPopular.items().get(0).getManifest().body();
        for(HalContent bodyPart : body) {
            Log.d("Webservice", bodyPart.content);
        }
    }

}
