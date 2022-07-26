package zan.ray.cam;

import org.joml.Matrix4f;

public interface Camera {

	void capture(float theta);

	public int getWidth();

	public int getHeight();

	public float getAspectRatio();

	Matrix4f getProjectionMatrix();

	Matrix4f getViewMatrix();

}
