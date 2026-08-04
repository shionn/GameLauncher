package shionn.game.launcher;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import shionn.game.Configuration;
import shionn.game.games.Engine;
import shionn.game.games.Game;

public class GameRunner {

	private static final String GAME_CONTROLLER = "03000000de280000ff11000001000000,Steam Virtual Gamepad,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,03000000de280000fc11000001000000,Steam Controller,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,030000005e040000a102000007010000,X360 Wireless Controller,a:b0,b:b1,back:b6,dpdown:b14,dpleft:b11,dpright:b12,dpup:b13,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,0000000058626f782047616d65706100,XInput Controller,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,030000005e0400008e02000010010000,X360 Controller,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,";
	private Configuration configuration = new Configuration();
	private Game game;
	private Engine engine;

	public GameRunner(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void startInstall() {

		try {
			new File(configuration.gamesFolder() + game.getName()).mkdir();

			StringBuilder command = new StringBuilder()
					.append(engine.proton(game.getProton()).getPath())
					.append("/proton");
			ProcessBuilder processBuilder = new ProcessBuilder(command.toString(), "run", game.getInstalers().get(0));
			Map<String, String> env = processBuilder.environment();
			env.put("LD_LIBRARY_PATH", buildLdLibraryPath());
//			env.put("SDL_GAMECONTROLLERCONFIG", GAME_CONTROLLER);
//			env.put("SDL_GAMECONTROLLER_ALLOW_STEAM_VIRTUAL_GAMEPAD", "1");
//			env.put("SDL_GAMECONTROLLER_USE_BUTTON_LABELS", "1");
//			env.put("SDL_VIDEO_X11_DGAMOUSE", "0");
			env.put("STEAM_COMPAT_CLIENT_INSTALL_PATH", configuration.steamFolder());
			env.put("STEAM_COMPAT_DATA_PATH", configuration.gamesFolder() + game.getName());
			System.out.println("env : " + env);
			System.out.println("Command : " + command.toString() + " run " + game.getInstalers().get(0));
			Process process = processBuilder.start();
			int exitCode = process.waitFor();
			System.out.println("Code de retour : " + exitCode);
		} catch (IOException | InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	private String buildLdLibraryPath() {
		String folder = configuration.steamFolder();
		return new StringBuilder()
				.append("$LD_LIBRARY_PATH:")
				.append(folder)
				.append("ubuntu12_32/steam-runtime/pinned_libs_32:")
				.append(folder)
				.append("ubuntu12_32/steam-runtime/pinned_libs_64:")
				.append(folder)
				.append("ubuntu12_32/steam-runtime/lib/i386-linux-gnu:")
				.append(folder)
				.append("ubuntu12_32/steam-runtime/usr/lib/i386-linux-gnu:")
				.append(folder)
				.append("ubuntu12_32/steam-runtime/lib/x86_64-linux-gnu:")
				.append(folder)
				.append("ubuntu12_32/steam-runtime/usr/lib/x86_64-linux-gnu:")
				.append(folder)
				.append("ubuntu12_32/steam-runtime/lib:")
				.append(folder)
				.append("ubuntu12_32/steam-runtime/usr/lib:")
				.append("/usr/lib64/qt-3.3/lib:")
				.append("/usr/lib64/tcl8.6:")
				.append("/usr/lib/wine:")
				.append("/usr/lib64/wine:")
				.append("/lib:")
				.append("/lib64:")
				.toString();
	}

}
