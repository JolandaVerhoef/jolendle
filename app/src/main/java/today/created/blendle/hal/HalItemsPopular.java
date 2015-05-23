package today.created.blendle.hal;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by jolandaverhoef on 21/05/15.
 * Represents the list of popular news items in the Blendle API.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class HalItemsPopular {
    @JsonProperty("_embedded")
    private Embedded embedded;

    @JsonProperty("_links")
    public Links links;

    public List<HalItemPopular> items() {
        return embedded.items;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public class Embedded {
        @JsonProperty("items")
        List<HalItemPopular> items;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public class Links {
        @JsonProperty("self")
        public Link self;
        @JsonProperty("next")
        public Link next;
    }
}
