package com.example.ziyuan.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class StartupRunner {

    @PostConstruct
    public void onStartup() {
        String url = "http://localhost:8080//index.html"; // 替换为你要打开的网页地址
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux") || os.contains("nux")) {
                Runtime.getRuntime().exec("xdg-open " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}