package zan.ray.cam;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;

import zan.lib.app.Window;
import zan.lib.utl.Utility;

public class TestCamera extends BaseCamera implements WorldCamera {

	public static class State {

		public Vector3f target = new Vector3f();

		public float distance;
		public float altitude;
		public float azimuth;

		public void set(State state) {
			target.set(state.target);
			distance = state.distance;
			altitude = state.altitude;
			azimuth = state.azimuth;
		}

		public void interp(State prev, State next, float theta) {
			target.set(prev.target).lerp(next.target, theta);
			distance = Utility.lerp(prev.distance, next.distance, theta);
			altitude = Utility.slerp(prev.altitude, next.altitude, 360.0f, theta);
			azimuth = Utility.slerp(prev.azimuth, next.azimuth, 360.0f, theta);
		}

	}

	private static final Vector3f positiveY = new Vector3f(0.0f, 1.0f, 0.0f);
	private static final Vector3f positiveZ = new Vector3f(0.0f, 0.0f, 1.0f);

	private Window window;

	State curr = new State();
	State prev = new State();
	State next = new State();

	private float fovv;
	private float tanv;

	private float near;
	private float far;

	public TestCamera(Window window, State state, float fovv, float near, float far) {
		this.window = window;
		this.curr.set(state);
		this.prev.set(state);
		this.next.set(state);
		setFieldOfViewV(fovv);
		setClipDistance(near, far);
	}

	public void preset() {
		prev.set(next);
	}

	public void setState(State state) {
		next.set(state);
	}

	public Vector2f project(Vector3fc position) {
		Vector4f ndc = new Matrix4f(proj).mulAffine(view).transform(new Vector4f(position, 1.0f));
		float scx = (ndc.x / ndc.z + 1.0f) * height / 2.0f;
		float scy = height - (ndc.y / ndc.z + 1.0f) * height / 2.0f;
		return new Vector2f(scx, scy);
	}

	public Vector3f raycast(Vector2fc screen) {
		Vector2f ndc = new Vector2f(screen.x() / window.getWidth(), 1.0f - screen.y() / window.getHeight()).mul(2.0f).sub(1.0f, 1.0f);
		Vector3f dir = getForwardDirection().add(getRightDirection().mul((float) window.getWidth() / (float) window.getHeight() * ndc.x).add(getUpDirection().mul(ndc.y)).mul(getTangentV())).normalize();
		return dir;
	}

	@Override
	public void capture(float theta) {
		curr.interp(prev, next, theta);

		if (width != window.getWidth() || height != window.getHeight()) {
			width = window.getWidth();
			height = window.getHeight();
			aspect = (float) width / (float) height;
			proj.setPerspective((float) Math.toRadians(fovv), aspect, near, far);
		}

		view.setLookAlong(positiveY, positiveZ)
			.translate(0.0f, curr.distance, 0.0f)
			.rotateX((float) Math.toRadians(curr.altitude))
			.rotateZ((float) Math.toRadians(curr.azimuth))
			.translate(new Vector3f(curr.target).negate());
	}

	@Override
	public Vector3f getPosition() {
		return new Vector3f(0.0f, -curr.distance, 0.0f)
			.rotateX((float) Math.toRadians(-curr.altitude))
			.rotateZ((float) Math.toRadians(-curr.azimuth))
			.add(curr.target);
	}

	@Override
	public Vector3f getForwardDirection() {
		float altitude = (float) Math.toRadians(curr.altitude);
		float azimuth = (float) Math.toRadians(curr.azimuth);
		return new Vector3f((float) (Math.sin(azimuth) * Math.cos(altitude)),
							(float) (Math.cos(azimuth) * Math.cos(altitude)),
							(float) (-Math.sin(altitude)));
	}

	@Override
	public Vector3f getUpDirection() {
		float altitude = (float) Math.toRadians(curr.altitude);
		float azimuth = (float) Math.toRadians(curr.azimuth);
		return new Vector3f((float) (Math.sin(azimuth) * Math.sin(altitude)),
							(float) (Math.cos(azimuth) * Math.sin(altitude)),
							(float) Math.cos(altitude));
	}

	@Override
	public Vector3f getRightDirection() {
		return new Vector3f((float) Math.cos(Math.toRadians(curr.azimuth)), (float) (-Math.sin(Math.toRadians(curr.azimuth))), 0.0f);
	}

	@Override
	public void setFieldOfViewV(float fovv) {
		this.fovv = fovv;
		this.tanv = (float) Math.tan(Math.toRadians(fovv / 2.0f));
	}

	@Override
	public float getFieldOfViewV() {
		return fovv;
	}

	@Override
	public float getTangentV() {
		return tanv;
	}

	@Override
	public void setClipDistance(float near, float far) {
		this.near = near;
		this.far = far;
	}

	@Override
	public float getNearClipDistance() {
		return near;
	}

	@Override
	public float getFarClipDistance() {
		return far;
	}

}
