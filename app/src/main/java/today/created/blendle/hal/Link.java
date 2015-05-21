package today.created.blendle.hal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jolandaverhoef on 21/05/15.
 * Represents a link in HAL.
 */
public class Link {
    @JsonProperty("href")
    public String href;
}
