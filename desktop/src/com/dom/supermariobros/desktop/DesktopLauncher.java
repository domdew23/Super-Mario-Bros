package com.dom.supermariobros.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dom.supermariobros.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Super Mario Bros";
		config.useGL30 = true;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new GameMain(), config);
	}
}