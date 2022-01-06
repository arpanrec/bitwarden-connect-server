package org.sourceshift.bcs;

import java.io.IOException;

import org.sourceshift.bcs.utils.BitwardenUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BitwardenConnectServerApplication implements CommandLineRunner {
    public static void main(String... args) {
        SpringApplication.run(BitwardenConnectServerApplication.class, args);
    }

    public void run(String... args) throws BwConnectException, IOException {
        BitwardenUtils.logout();
    }
}
