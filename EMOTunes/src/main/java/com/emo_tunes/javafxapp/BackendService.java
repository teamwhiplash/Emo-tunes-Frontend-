package com.emo_tunes.javafxapp;

import java.util.List;

public class BackendService {
    public static List<String> getSongsByEmotion(String emotion) {
        // Example using HttpURLConnection or RestTemplate
        // Make your REST request and return a List<String> of songs
        return List.of("Song 1", "Song 2", "Song 3");
    }
}
