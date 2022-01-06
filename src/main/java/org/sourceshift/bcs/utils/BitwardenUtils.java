package org.sourceshift.bcs.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.sourceshift.bcs.BwConnectException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BitwardenUtils {

    public static JsonNode cliOutConverter(byte[] status) throws BwConnectException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(new String(status));
        } catch (JsonProcessingException e) {
            throw new BwConnectException("Unable to parse BW CLI output", e);
        }
    }

    public static JsonNode logout() throws BwConnectException {
        try {
            byte[] status = CLI.runCommandForOutput(Arrays.asList("bw logout --raw".split(" ")));
            return cliOutConverter(status);
        } catch (BwConnectException ex) {
            if (ex.getMessage().equals("Error While Executing Command :: You are not logged in.")) {
                return null;
            } else
                throw ex;
        }

    }

    public JsonNode getStatus() throws BwConnectException {
        return getStatus(null);
    }

    public static JsonNode getStatus(String sessionKey) throws BwConnectException {
        final Map<String, String> environments = new HashMap<>();
        if (StringUtils.isNoneEmpty(sessionKey)) {
            environments.put("BW_SESSION", sessionKey);
        }
        byte[] status = CLI.runCommandForOutput(Arrays.asList("bw status --raw".split(" ")), environments);
        return cliOutConverter(status);
    }

    public static void bwLogin(String clientId, String clientSecret) throws BwConnectException {
        logout();
        final Map<String, String> environments = new HashMap<>();
        environments.put("BW_CLIENTID", clientId);
        environments.put("BW_CLIENTSECRET", clientSecret);
        CLI.runCommandForOutput(Arrays.asList("bw login --apikey --raw".split(" ")), environments);
    }

    public static String unlock(String password) throws BwConnectException {

        File passwordFile = null;
        try {
            passwordFile = File.createTempFile("prefix-", "-suffix");
            FileUtils.writeStringToFile(passwordFile, password, StandardCharsets.UTF_8);
            return unlock(passwordFile);
        } catch (IOException e) {
            throw new BwConnectException("Unable to unlock", e);
        } finally {
            if (passwordFile != null && passwordFile.exists()) {
                passwordFile.delete();
            }
        }

    }

    public static String unlock(File password) throws BwConnectException {
        byte[] sessionKeyBytes = CLI.runCommandForOutput(
                Arrays.asList(
                        String.format("bw unlock --passwordfile %s --raw", password.getAbsolutePath()).split(" ")));
        return new String(sessionKeyBytes);
    }

}
