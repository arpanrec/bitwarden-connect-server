package org.sourceshift.bcs.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.sourceshift.bcs.BwConnectException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CLI {

    public static byte[] runCommandForOutput(List<String> params) throws BwConnectException {
        return runCommandForOutput(params, null);
    }

    public static byte[] runCommandForOutput(List<String> params, Map<String, String> userEnvironment) throws BwConnectException {
        log.trace("Executing command {}", params);
        ProcessBuilder processBuilder = new ProcessBuilder(params);
        Map<String, String> processBuilderEnvironment = processBuilder.environment();
        if (userEnvironment != null && !userEnvironment.isEmpty()) {
            log.trace("Executing command {} with Environment variables {}", params, userEnvironment);
            processBuilderEnvironment.putAll(userEnvironment);
        }
        Process process = null;
        try {
            process = processBuilder.start();
            process.waitFor(120, TimeUnit.SECONDS);
            if (process.exitValue() != 0) {
                throw new BwConnectException(String.format("Error While Executing Command :: %s", IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8)));
            }
            return IOUtils.toByteArray(process.getInputStream());
        } catch (IOException | InterruptedException e) {
            throw new BwConnectException("Error While Executing Command", e);
        }
    }
}
