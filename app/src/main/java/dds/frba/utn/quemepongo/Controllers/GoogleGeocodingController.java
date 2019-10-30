package dds.frba.utn.quemepongo.Controllers;

import android.content.Context;

import java.util.List;

import dds.frba.utn.quemepongo.Helpers.CustomRetrofitCallback;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.WebServices.Error;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Geocoding.NominatimGeocodeResponse;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.Repository.GoogleGeocodingRepository;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import retrofit2.Call;
import retrofit2.Response;

public class GoogleGeocodingController {
    private final String ARG = "Argentina";
    private final String RESPONSE_FORMAT = "json";
    private final Integer RESPONSE_LIMIT = 6;

    GoogleGeocodingRepository repository;
    QueMePongo application;
    public GoogleGeocodingController(Context context) {
        repository = RetrofitInstanciator.instanciateRepository(GoogleGeocodingRepository.class);
        application = (QueMePongo) context.getApplicationContext();
    }

    public void fetchAddressDetails(String addres, OnCompleteListenerWithStatus listenerWithStatus){
        Call<List<NominatimGeocodeResponse>> call = repository.getAddresFromString(ARG,addres, RESPONSE_FORMAT, RESPONSE_LIMIT);
        call.enqueue(new ErrorHelper().showToastErrorInCaseIsNeeded(application, listenerWithStatus)
        );
    }
}
