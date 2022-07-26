package zan.ray.cam;

import zan.lib.app.Window;

public class WindowCamera extends BaseCamera implements ScreenCamera {

	private Window window;

	public WindowCamera(Window window) {
		this.window = window;
	}

	@Override
	public void capture(float theta) {
		if (width != window.getWidth() || height != window.getHeight()) {
			width = window.getWidth();
			height = window.getHeight();
			aspect = (float) width / (float) height;
			proj.setOrtho2D(0, width, 0, height);
		}
	}

}
