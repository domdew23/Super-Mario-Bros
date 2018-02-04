package com.dom.supermariobros.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dom.supermariobros.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Super Mario Bros";
		config.vSyncEnabled = true;
		config.useGL30 = true;
		config.width = 1200;
		config.height = 624;
		new LwjglApplication(new GameMain(), config);
	}
}
