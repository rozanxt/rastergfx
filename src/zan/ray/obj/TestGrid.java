package zan.ray.obj;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

import org.joml.Vector2f;
import org.joml.Vector4f;

import zan.lib.gfx.shd.Shader;
import zan.ray.cam.WorldCamera;

public class TestGrid {

	private Shader raster;

	private WorldCamera camera;

	public TestGrid(Shader raster, WorldCamera camera) {
		this.raster = raster;
		this.camera = camera;
	}

	public void render(float theta) {
		raster.bind();
		raster.setUniform("projectionMatrix", camera.getProjectionMatrix());
		raster.setUniform("viewMatrix", camera.getViewMatrix());
		raster.setUniform("camera.position", camera.getPosition());
		raster.setUniform("camera.forward", camera.getForwardDirection());
		raster.setUniform("camera.up", camera.getUpDirection());
		raster.setUniform("camera.right", camera.getRightDirection());
		raster.setUniform("camera.tany", camera.getTangentV());
		raster.setUniform("camera.near", camera.getNearClipDistance());
		raster.setUniform("camera.viewport", new Vector2f(camera.getWidth(), camera.getHeight()));
		raster.setUniform("camera.aspect", camera.getAspectRatio());
		raster.setUniform("camera.resolution", new Vector2f(camera.getWidth(), camera.getHeight()));
		raster.setUniform("rasterColor", new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
		glDrawArrays(GL_TRIANGLES, 0, 6);
		raster.unbind();
	}

}
