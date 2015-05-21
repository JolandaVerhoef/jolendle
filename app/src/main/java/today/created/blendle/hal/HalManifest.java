package today.created.blendle.hal;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by jolandaverhoef on 21/05/15.
 * Represents the a news item manifest.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class HalManifest {
    @JsonProperty("body")
    List<HalContent> body;

    public List<HalContent> body() {
        return body;
    }

}
