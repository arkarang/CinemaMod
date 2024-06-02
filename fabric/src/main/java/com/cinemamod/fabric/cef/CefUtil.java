package com.cinemamod.fabric.cef;

import com.cinemamod.fabric.CinemaModClient;
import com.cinemamod.fabric.cef.scheme.CefCinemaAppHandler;
import com.cinemamod.fabric.screen.Screen;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import me.friwi.jcefmaven.impl.progress.ConsoleProgressHandler;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;

import java.io.File;
import java.io.IOException;

public final class CefUtil {

    private CefUtil() {}

    private static boolean init;
    private static CefApp cefAppInstance;
    private static CefClient cefClientInstance;

    public static boolean init(File folder) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        String[] cefSwitches = new String[]{
                "--autoplay-policy=no-user-gesture-required",
                "--disable-web-security"
        };

        //if (!CefApp.startup(cefSwitches)) {
        //    return false;
        //}

        cefAppInstance = jcefMaven(folder, cefSwitches);
        System.out.println("initialized");

        cefClientInstance = cefAppInstance.createClient();
        System.out.println("created client");
        cefClientInstance.addLoadHandler(new CefBrowserCinemaLoadHandler());
        System.out.println("added load handler");

        return init = true;
    }

    public static CefApp jcefMaven(File workingDirectory, String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        CefAppBuilder builder = new CefAppBuilder();

        builder.setInstallDir(workingDirectory);
        //builder.addJcefArgs("--disable-gpu"); //Just an example
        builder.addJcefArgs(args);
        CefSettings cefSettings = builder.getCefSettings();
        cefSettings.windowless_rendering_enabled = true;
        cefSettings.background_color = cefSettings.new ColorType(0, 255, 255, 255);
        builder.setAppHandler(new CefCinemaAppHandler());
        builder.install();
        return builder.build();
    }

    public static boolean isInit() {
        return init;
    }

    public static CefApp getCefApp() {
        return cefAppInstance;
    }

    public static CefClient getCefClient() {
        return cefClientInstance;
    }

    public static CefBrowserCinema createBrowser(String startUrl, int widthPx, int heightPx) {
        if (!init) return null;
        CefBrowserCinema browser = new CefBrowserCinema(cefClientInstance, startUrl, false, null);
        browser.setCloseAllowed();
        browser.createImmediately();
        browser.resize(widthPx, heightPx);
        return browser;
    }

    public static CefBrowserCinema createBrowser(String startUrl, Screen screen) {
        if (!init) return null;
        CefBrowserCinema browser = new CefBrowserCinema(cefClientInstance, startUrl, true, null);
        browser.setCloseAllowed();
        browser.createImmediately();
        // Adjust screen size
        {
            float widthBlocks = screen.getWidth();
            float heightBlocks = screen.getHeight();
            float scale = widthBlocks / heightBlocks;
            int height = CinemaModClient.getInstance().getVideoSettings().getBrowserResolution();
            int width = (int) Math.floor(height * scale);
            browser.resize(width, height);
        }
        return browser;
    }

}
