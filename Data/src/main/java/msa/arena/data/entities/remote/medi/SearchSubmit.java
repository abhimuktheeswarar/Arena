package msa.arena.data.entities.remote.medi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Abhimuktheeswarar on 05-05-2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "search_txt"
})
public class SearchSubmit {

    @JsonProperty("search_txt")
    private String search_txt;

    public SearchSubmit(String search_txt) {
        this.search_txt = search_txt;
    }
}
