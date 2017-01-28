package se.cag.labs.rover.service;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import se.cag.labs.rover.contract.Sensor;

public interface DroidRaceApiService {
    @POST("registerSensor")
    Call<Void> registerSensor(
            @Body
            Sensor sensor);
}