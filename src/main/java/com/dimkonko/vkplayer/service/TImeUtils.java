package com.dimkonko.vkplayer.service;

public class TImeUtils {
    private static final int SECONDS_IN_MINUTE = 60;
    private static final String TIME_FORMAT = "%02d:%02d";

    /** 00:00 String */
    public static final String ZERO_TIME = getFormattedTime(0);

    /**
     * Generate time String from given seconds
     * Example: 00:15
     * @param totalSeconds seconds to generate string from
     * @return generated string
     */
    public static final String getFormattedTime(int totalSeconds) {
        int minutes = totalSeconds / SECONDS_IN_MINUTE;
        int seconds = totalSeconds % SECONDS_IN_MINUTE;
        return String.format(TIME_FORMAT, minutes, seconds);
    }
}
