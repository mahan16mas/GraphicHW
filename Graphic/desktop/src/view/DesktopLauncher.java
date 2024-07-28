package view;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import model.User;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Atomic Bomb");
		Graphics.DisplayMode[] displayModes = Lwjgl3ApplicationConfiguration.getDisplayModes();
		Graphics.DisplayMode displayMode = displayModes[2];
		config.setFullscreenMode(displayMode);
        config.setResizable(false);
        User.setUsers();
        config.setWindowIcon("icon.png");
        new Lwjgl3Application(new MainGame(), config);
    }
}
