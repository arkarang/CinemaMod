package com.cinemamod.fabric.cef;
import net.minecraft.client.MinecraftClient;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefBrowserFactory;
import org.cef.browser.CefRequestContext;

import java.awt.*;
import java.nio.ByteBuffer;

public class CefBrowserCinema {
    private final CefBrowser cefBrowser;
    public final CefBrowserCinemaRenderer renderer = new CefBrowserCinemaRenderer(true);

    public CefBrowserCinema(CefClient client, String url, boolean transparent, CefRequestContext context) {
        cefBrowser = CefBrowserFactory.create(client, url, true, transparent, context, null);
        MinecraftClient.getInstance().submit(renderer::initialize);
    }

    public int getIdentifier() {
        return cefBrowser.getIdentifier();
    }

    public void onPaint(boolean popup, Rectangle[] dirtyRects, ByteBuffer buffer, int width, int height) {
        renderer.onPaint(buffer, width, height);
    }

    /*
    public void sendKeyPress(int keyCode, int modifiers, long scanCode) {
        CefBrowserCinemaKeyEvent keyEvent = new CefBrowserCinemaKeyEvent(dummyComponent,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                modifiers,
                keyCode,
                KeyEvent.CHAR_UNDEFINED,
                scanCode);
        cefBrowser.sendKeyEvent(keyEvent);
    }

    public void sendKeyRelease(int keyCode, int modifiers, long scanCode) {
        CefBrowserCinemaKeyEvent keyEvent = new CefBrowserCinemaKeyEvent(dummyComponent,
                KeyEvent.KEY_RELEASED,
                System.currentTimeMillis(),
                modifiers,
                keyCode,
                KeyEvent.CHAR_UNDEFINED,
                scanCode);
        cefBrowser.sendKeyEvent(keyEvent);
    }

    public void sendKeyTyped(char c, int modifiers) {
        KeyEvent keyEvent = new KeyEvent(dummyComponent,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                modifiers,
                KeyEvent.VK_UNDEFINED,
                c);
        cefBrowser.sendKeyEvent(keyEvent);
    }

    public void sendMouseMove(int mouseX, int mouseY) {
        MouseEvent mouseEvent = new MouseEvent(dummyComponent,
                MouseEvent.MOUSE_MOVED,
                System.currentTimeMillis(),
                MouseEvent.BUTTON1_DOWN_MASK, // Allow for mouse dragging
                mouseX,
                mouseY,
                0,
                false);
        cefBrowser.sendMouseEvent(mouseEvent);
    }

    public void sendMousePress(int mouseX, int mouseY, int button) {
        MouseEvent mouseEvent = new MouseEvent(dummyComponent,
                MouseEvent.MOUSE_PRESSED,
                System.currentTimeMillis(),
                0,
                mouseX,
                mouseY,
                1,
                false,
                button + 1);
        cefBrowser.sendMouseEvent(mouseEvent);
    }

    public void sendMouseRelease(int mouseX, int mouseY, int button) {
        MouseEvent mouseEvent = new MouseEvent(dummyComponent,
                MouseEvent.MOUSE_RELEASED,
                System.currentTimeMillis(),
                0,
                mouseX,
                mouseY,
                1,
                false,
                button + 1);
        cefBrowser.sendMouseEvent(mouseEvent);

        mouseEvent = new MouseEvent(dummyComponent,
                MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(),
                0,
                mouseX,
                mouseY,
                1,
                false,
                button + 1);
        cefBrowser.sendMouseEvent(mouseEvent);
    }

    public void sendMouseWheel(int mouseX, int mouseY, int mods, int amount, int rotation) {
        MouseWheelEvent mouseWheelEvent = new MouseWheelEvent(dummyComponent,
                MouseEvent.MOUSE_WHEEL,
                System.currentTimeMillis(),
                mods,
                mouseX,
                mouseY,
                0,
                false,
                MouseWheelEvent.WHEEL_UNIT_SCROLL,
                amount,
                rotation);
        cefBrowser.sendMouseWheelEvent(mouseWheelEvent);
    }

     */

    public void resize(int width, int height) {
        cefBrowser.getUIComponent().setSize(width, height);
    }

    public void createImmediately() {
        if(cefBrowser != null) {
            cefBrowser.createImmediately();
        }
    }

    public void setCloseAllowed() {
        if(cefBrowser != null) {
            cefBrowser.setCloseAllowed();
        }
    }
    // browser.getMainFrame().executeJavaScript(startJs, browser.getURL(), 0);
    public void executeJavaScript(String js, String url, int line) {
        cefBrowser.executeJavaScript(js, url, line);
    }

    public String currentURL() {
        return cefBrowser.getURL();
    }

    public void close() {
        renderer.cleanup();
        cefBrowser.close(true);
    }
}