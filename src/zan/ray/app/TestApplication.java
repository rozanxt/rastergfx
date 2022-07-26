package zan.ray.app;

import zan.lib.app.Application;
import zan.lib.app.Engine;
import zan.lib.app.Input;
import zan.lib.app.Scene;
import zan.lib.app.Window;
import zan.ray.scn.TestScene;

public class TestApplication extends Application {

	public static void main(String[] args) {
		Application application = new TestApplication();
		application.init();

		Engine engine = new Engine(60, 20);
		Window.Attributes attrib = new Window.Attributes(1280, 720);
		attrib.title = "Test Application";
		Window window = new Window(attrib);
		Input input = new Input(window);
		Scene scene = new TestScene(engine);
		engine.setWindow(window);
		engine.setInput(input);
		engine.setScene(scene);
		engine.run();

		application.exit();
	}

}
