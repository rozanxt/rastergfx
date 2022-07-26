package zan.ray.cam;

import org.joml.Matrix4f;

public abstract class BaseCamera implements Camera {

	protected int width;
	protected int height;

	protected float aspect;

	protected Matrix4f proj = new Matrix4f();
	protected Matrix4f view = new Matrix4f();

	@Override
	public abstract void capture(float theta);

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public float getAspectRatio() {
		return aspect;
	}

	@Override
	public Matrix4f getProjectionMatrix() {
		return proj;
	}

	@Override
	public Matrix4f getViewMatrix() {
		return view;
	}

}
