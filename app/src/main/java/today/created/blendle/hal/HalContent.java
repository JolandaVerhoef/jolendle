package today.created.blendle.hal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jolandaverhoef on 21/05/15.
 * Represents a part of the body of a news item.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class HalContent {
    @JsonProperty("type")
    public String type;

    @JsonProperty("content")
    public String content;

    public boolean isTitle() {
        return "hl1".equals(type);
    }
}
