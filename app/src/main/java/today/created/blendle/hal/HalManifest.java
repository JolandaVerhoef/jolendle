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
    private
    List<HalContent> body;

    @JsonProperty("images")
    private List<HalImage> images;

    public List<HalContent> body() {
        return body;
    }
    private List<HalImage> images() { return images; }

    public HalImage getFeaturedImage() {
        for (HalImage image : images()) {
            if(image.featured) return image;
        }
        return null;
    }

    public HalContent getTitle() {
        for (HalContent bodyPart : body()) {
            if("hl1".equals(bodyPart.type)) return bodyPart;
        }
        return body.size() > 0 ? body().get(0) : null;
    }
}
