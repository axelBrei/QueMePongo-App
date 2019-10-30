package dds.frba.utn.quemepongo.Model.WebServices.Response.Geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NominatimGeocodeResponse {
    String lat;
    String lon;
    String display_name;
    String type;
    String place_id;
    String licence;
    String osm_type;
    String osm_id;
    Double importance;
    List<Double> boundingbox;
    String icon;


    @JsonProperty("class")
    String addresClass;

    @Override
    public String toString(){
        return display_name;
    }

}
