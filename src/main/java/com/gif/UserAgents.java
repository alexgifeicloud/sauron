package com.gif;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public final class UserAgents {

    private static List<String> VALUES;

    static {
        try (InputStream resource = UserAgents.class.getResourceAsStream("/userAgents.txt")) {
            VALUES =
                    new BufferedReader(new InputStreamReader(resource,
                            StandardCharsets.UTF_8)).lines().collect(toList());
        } catch (IOException e) {
            Logger.log("An error occurred.");
            e.printStackTrace();
        }
    }

    public String chooseRandomAgent() {
        var random = new Random(System.nanoTime());
        var nextInt = random.nextInt(1000);
        return VALUES.get(nextInt);
    }
}
