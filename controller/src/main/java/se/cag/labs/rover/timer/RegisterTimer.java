package se.cag.labs.rover.timer;

import com.pi4j.system.SystemInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.cag.labs.rover.contract.Sensor;
import se.cag.labs.rover.service.DroidRaceApiService;
import se.cag.labs.rover.utils.NetworkUtils;

import java.io.IOException;

@Component
@Log4j
public class RegisterTimer {

    @Value("${server.current.race.base.uri}")
    private String baseUrl;

    @Scheduled(fixedRate = 30000)
    public void registerRover() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        try {
            DroidRaceApiService service = retrofit.create(DroidRaceApiService.class);
            Sensor sensor = createSensorData();
            Response<Void> response = service.registerSensor(sensor).execute();
            if (!response.isSuccessful()) {
                log.error("Couldn't register rover IP, got: " + response.code() + " - " + response.message());
            }
        } catch (IOException | InterruptedException e) {
            log.error("Couldn't register sensor", e);
        }

    }

    private Sensor createSensorData() throws IOException, InterruptedException {
        try {
            return new Sensor("rover_" + SystemInfo.getSerial(), NetworkUtils.getIpAddress());
        } catch (UnsupportedOperationException e) {
            log.info("We are not running on a pi...");
            return new Sensor("rover", NetworkUtils.getIpAddress());
        }
    }
}
