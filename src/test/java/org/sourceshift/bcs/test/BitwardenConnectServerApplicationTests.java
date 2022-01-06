package org.sourceshift.bcs.test;

import org.junit.jupiter.api.Test;
import org.sourceshift.bcs.utils.BitwardenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BitwardenConnectServerApplicationTests {

    @Autowired
    @Qualifier("bitwardenStore")
    BitwardenUtils bitwardenUtils;

    @Test
    void getStatus() {
    }

}
