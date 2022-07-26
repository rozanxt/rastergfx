package zan.ray.cam;

import org.joml.Vector3f;

public interface WorldCamera extends Camera {

	Vector3f getPosition();

	Vector3f getForwardDirection();

	Vector3f getUpDirection();

	Vector3f getRightDirection();

	void setFieldOfViewV(float fov);

	float getFieldOfViewV();

	float getTangentV();

	void setClipDistance(float near, float far);

	float getNearClipDistance();

	float getFarClipDistance();

}
