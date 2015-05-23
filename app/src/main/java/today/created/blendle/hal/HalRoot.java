package today.created.blendle.hal;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jolandaverhoef on 21/05/15.
 * Represents the root of the HAL API. Follow links from here.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class HalRoot {
    @JsonProperty("_links")
    private RootLinks links;

    public String getSelfLink() {
        return links.self.href;
    }

    public String getItemsPopularLink() {
        return links.itemsPopular.href;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public class RootLinks {
        @JsonProperty("self")
        Link self;
        @JsonProperty("items_popular")
        Link itemsPopular;
    }
}
