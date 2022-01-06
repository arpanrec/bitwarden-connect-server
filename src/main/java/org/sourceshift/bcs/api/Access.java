package org.sourceshift.bcs.api;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import org.apache.commons.io.FileUtils;
import org.sourceshift.bcs.BwConnectException;
import org.sourceshift.bcs.utils.BitwardenUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class Access {

    @GetMapping(value = "/api/bw/status")
    @ResponseBody
    public Map<String, String> getStatus(@RequestHeader(value = "BW_SESSION", required = false) String sessionKey)
            throws BwConnectException {
        return Collections.singletonMap("status", BitwardenUtils.getStatus(sessionKey).get("status").asText());
    }

    @PostMapping(value = "/api/bw/login")
    @ResponseBody
    public Map<String, String> login(@RequestBody JsonNode loginForm) throws BwConnectException {
        BitwardenUtils.bwLogin(loginForm.get("BW_CLIENTID").asText(), loginForm.get("BW_CLIENTSECRET").asText());
        return Collections.singletonMap("status", BitwardenUtils.getStatus(null).get("status").asText());
    }

    @PostMapping(value = "/api/bw/unlock")
    @ResponseBody
    public Map<String, String> unlock(@RequestBody JsonNode unlockForm) throws BwConnectException {
        return Collections.singletonMap("BW_SESSION",
                BitwardenUtils.unlock(unlockForm.get("BW_MASTERPASSWORD").asText()));
    }

}
