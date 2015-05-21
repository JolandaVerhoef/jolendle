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
    protected Embedded embedded;

    @JsonProperty("_links")
    protected Links links;

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
        Link self;
    }
}
