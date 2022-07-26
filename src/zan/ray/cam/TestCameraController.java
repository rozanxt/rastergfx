package zan.ray.cam;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_3;

import zan.lib.app.Input;

public class TestCameraController {

	private TestCamera camera;

	public TestCameraController(TestCamera camera) {
		this.camera = camera;
	}

	public void preset() {
		camera.preset();
	}

	public void handle(Input input) {
		if (input.isMouseDown(GLFW_MOUSE_BUTTON_3)) {
			camera.next.azimuth += input.getMouseDeltaX();
			camera.next.altitude += input.getMouseDeltaY();
		}

		camera.next.altitude = Math.min(Math.max(camera.next.altitude, -90.0f), 90.0f);
		camera.next.azimuth = ((camera.next.azimuth % 360.0f) + 360.0f) % 360.0f;
	}

	public TestCamera getCamera() {
		return camera;
	}

}
