package shionn.game.launcher;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import shionn.game.Configuration;
import shionn.game.games.Engine;
import shionn.game.games.Game;

public class GameRunner {

//	private static final String GAME_CONTROLLER = "03000000de280000ff11000001000000,Steam Virtual Gamepad,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,03000000de280000fc11000001000000,Steam Controller,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,030000005e040000a102000007010000,X360 Wireless Controller,a:b0,b:b1,back:b6,dpdown:b14,dpleft:b11,dpright:b12,dpup:b13,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,0000000058626f782047616d65706100,XInput Controller,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,030000005e0400008e02000010010000,X360 Controller,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,";
	private Configuration configuration = new Configuration();
	private Game game;
	private Engine engine;

	public GameRunner(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
	}

	public void start() {

		try {
			ProcessBuilder processBuilder = new GameProcessBuilder(engine, game)
					.wineprefix()
					.protonPath()
					.store()
					.gameId()
					.logs()
//					.env("STEAM_COMPAT_INSTALL_PATH", new File(game.getRunfile()).getParentFile().getAbsolutePath())
//					.env("STEAM_COMPAT_CLIENT_INSTALL_PATH", "/home/shionn/.steam/steam")
//					.env("STEAM_COMPAT_DATA_PATH", game.getInstalledFolder())

//					.env("UMU_STEAM_GAME_ID", "heroic-0")
//					.env("WINE_FULLSCREEN_FSR", "0")
//					.env("PROTON_ENABLE_NVAPI", "1")
//					.env("DXVK_NVAPI_ALLOW_OTHER_DRIVERS","1")
//					.env("LD_PRELOAD", "")
					.directory()
					.umuRun()
					.runFile()
//					.arg("-locale=en")
					.build();
			Process process = processBuilder.start();
			int exitCode = process.waitFor();
			process.destroy();
			System.out.println("Code de retour : " + exitCode);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

	}

	public void startInstall() {
		try {
			ProcessBuilder processBuilder = new GameProcessBuilder(engine, game)
					.wineprefix()
					.protonPath()
					.umuRun()
					.instaler()
					.build();
			int exitCode = processBuilder.start().waitFor();
			if (exitCode == 0) {
				game.setInstalledFolder(configuration.gamesFolder() + game.getName());
				game.setInstalled(true);
			} else {
				FileUtils.deleteDirectory(new File(configuration.gamesFolder() + game.getName()));
			}
			System.out.println("Code de retour : " + exitCode);
		} catch (IOException | InterruptedException e) {
			throw new IllegalStateException(e);
		}

	}

}
