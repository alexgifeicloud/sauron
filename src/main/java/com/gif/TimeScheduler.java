package com.gif;

import java.util.Random;

public final class TimeScheduler {

    private final int minSeconds;
    private final int maxSeconds;

    private TimeScheduler(int minSeconds, int maxSeconds) {
        this.minSeconds = minSeconds;
        this.maxSeconds = maxSeconds;
    }

    public static TimeScheduler of(int minSeconds, int maxSeconds) {
        return new TimeScheduler(minSeconds, maxSeconds);
    }

    public int nextRunInterval() {
        final Random random = new Random(System.nanoTime());
        return random.nextInt(maxSeconds - minSeconds) + minSeconds;
    }
}
