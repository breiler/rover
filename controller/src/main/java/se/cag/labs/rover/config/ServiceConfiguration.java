package se.cag.labs.rover.config;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.cag.labs.rover.service.DroidRaceApiService;

@Configuration
@Log4j
public class ServiceConfiguration {

    @Value("${server.current.race.base.uri}")
    private String baseUrl;

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public DroidRaceApiService droidRaceApiService(Retrofit retrofit) {
        return retrofit.create(DroidRaceApiService.class);
    }
}