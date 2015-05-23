package today.created.blendle.hal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jolandaverhoef on 21/05/15.
 * Represents a link in HAL.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Link {
    @JsonProperty("href")
    public String href;
}
