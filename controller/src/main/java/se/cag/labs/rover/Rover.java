package se.cag.labs.rover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.cag.labs.rover.utils.KeyStoreUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@EnableScheduling
public class Rover {

    public static void main(String[] args) throws IOException {
        // Ignore security, just download certificates and be done with it!
        KeyStoreUtils.updateKeyStore("sumorace.caglabs.se", 443, "/tmp/keystore.jks", "changeme");
        System.setProperty("javax.net.ssl.trustStore", "/tmp/keystore.jks");

        SpringApplication.run(Rover.class, args);
    }
}
