package zan.ray.scn;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F12;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.HashMap;
import java.util.Map;

import zan.lib.app.Engine;
import zan.lib.app.Input;
import zan.lib.app.Scene;
import zan.lib.app.Window;
import zan.lib.gfx.shd.Shader;
import zan.ray.cam.ScreenCamera;
import zan.ray.cam.TestCamera;
import zan.ray.cam.TestCameraController;
import zan.ray.cam.WindowCamera;
import zan.ray.cam.WorldCamera;
import zan.ray.obj.TestGrid;

public class TestScene implements Scene {

	private Engine engine;

	private Map<String, Shader> shaders;

	private TestCameraController controller;
	private WorldCamera camera;
	private ScreenCamera screen;

	private TestGrid grid;

	public TestScene(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void init() {
		Window window = engine.getWindow();

		shaders = new HashMap<>();
		shaders.put("raster", Shader.loadFromFile("res/shd/raster.vert", "res/shd/raster.frag"));

		TestCamera.State state = new TestCamera.State();
		state.distance = 5.0f;
		state.altitude = 10.0f;
		controller = new TestCameraController(new TestCamera(window, state, 60.0f, 0.01f, 100.0f));
		camera = controller.getCamera();
		screen = new WindowCamera(window);

		grid = new TestGrid(shaders.get("raster"), camera);
	}

	@Override
	public void exit() {
		for (Shader shader : shaders.values()) {
			shader.delete();
		}
		shaders.clear();
	}

	@Override
	public void update(float delta) {
		Window window = engine.getWindow();
		Input input = engine.getInput();

		if (input.isKeyPressed(GLFW_KEY_F11)) {
			window.setFullScreen(!window.isFullScreen());
		} else if (input.isKeyPressed(GLFW_KEY_F12)) {
			window.close();
		}

		controller.preset();
		controller.handle(input);
	}

	@Override
	public void render(float theta) {
		Window window = engine.getWindow();

		camera.capture(theta);
		screen.capture(theta);

		glViewport(0, 0, window.getWidth(), window.getHeight());

		glClearColor(0.0f, 0.1f, 0.1f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);

		grid.render(theta);

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);

		glDisable(GL_BLEND);
	}

}
