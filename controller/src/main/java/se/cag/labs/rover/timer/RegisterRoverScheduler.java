package se.cag.labs.rover.timer;

import com.pi4j.system.SystemInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import se.cag.labs.rover.contract.Sensor;
import se.cag.labs.rover.service.DroidRaceApiService;
import se.cag.labs.rover.utils.NetworkUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

@Component
@Log4j
public class RegisterRoverScheduler {

    private final DroidRaceApiService droidRaceApiService;

    public RegisterRoverScheduler(DroidRaceApiService droidRaceApiService) {
        this.droidRaceApiService = droidRaceApiService;
    }

    @Scheduled(fixedRate = 30000)
    public void registerRover() {
        try {
            Sensor sensor = createSensorData();
            Response<Void> response = droidRaceApiService.registerSensor(sensor).execute();
            if (!response.isSuccessful()) {
                log.error("Couldn't register rover IP, got: " + response.code() + " - " + response.message());
            }
        } catch (IOException | InterruptedException e) {
            log.error("Couldn't register rover", e);
        }
    }

    private Sensor createSensorData() throws IOException, InterruptedException {
        try {
            return new Sensor("rover_" + SystemInfo.getSerial(), NetworkUtils.getIpAddress());
        } catch (UnsupportedOperationException | FileNotFoundException e) {
            log.info("We are not running on a pi...");
            return new Sensor("rover", NetworkUtils.getIpAddress());
        }
    }
}
