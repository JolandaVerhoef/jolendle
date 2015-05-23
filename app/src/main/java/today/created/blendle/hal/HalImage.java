package today.created.blendle.hal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jolandaverhoef on 23/05/15.
 * Represents an image that belongs to an item
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class HalImage {
    @JsonProperty("featured")
    public boolean featured;

    @JsonProperty("_links")
    private Links links;

    public Link getLargestImageLink() {
        if(links.original != null) return links.original;
        if(links.large != null) return links.large;
        if(links.medium != null) return links.medium;
        if(links.small != null) return links.small;
        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public class Links {
        @JsonProperty("small")
        public Link small;
        @JsonProperty("medium")
        public Link medium;
        @JsonProperty("large")
        public Link large;
        @JsonProperty("original")
        public Link original;
    }
}
