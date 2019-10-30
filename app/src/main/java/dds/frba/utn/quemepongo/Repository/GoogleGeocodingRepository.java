package dds.frba.utn.quemepongo.Repository;

import java.util.List;

import dds.frba.utn.quemepongo.Model.WebServices.Response.Geocoding.NominatimGeocodeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleGeocodingRepository {

    @GET("https://nominatim.openstreetmap.org/search")
    Call<List<NominatimGeocodeResponse>> getAddresFromString(
            @Query("country") String country,
            @Query("street") String street,
            @Query("format") String format,
            @Query("limit") Integer limit
    );
}
