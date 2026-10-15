package com.payamsdev.nightshift.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.payamsdev.nightshift.Main;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        
        // Custom FNAF Title Branding
        configuration.setTitle("Night Shift at Hare's");
        
        configuration.useVsync(true);
        
        // Force the rendering loop to synchronize perfectly at 60 FPS to match your logic timeline ticks
        configuration.setForegroundFPS(60);

        // --- GLOBAL RESOLUTION LOCK: 1024x768 (4:3 Aspect Ratio) ---
        configuration.setWindowedMode(1024, 768);
        
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        return configuration;
    }
}