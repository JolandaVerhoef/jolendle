package today.created.blendle.hal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jolandaverhoef on 21/05/15.
 * Represents a popular item.
 * TODO: Refactor to generic item?
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class HalItemPopular {
    @JsonProperty("_embedded")
    private Embedded embedded;

    @JsonProperty("_links")
    private Links links;

    public HalManifest getManifest() {
        return embedded.manifest;
    }

    public String getSelfLink() {
        return links.self.href;
    }

    public String getItemContentLink() {
        return links.itemContent.href;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public class Embedded {
        @JsonProperty("manifest")
        HalManifest manifest;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public class Links {
        @JsonProperty("self")
        Link self;
        @JsonProperty("item_content")
        Link itemContent;
    }
}
