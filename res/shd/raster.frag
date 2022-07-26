#version 330 core

struct Camera {
	vec3 position;
	vec3 forward;
	vec3 up;
	vec3 right;
	float tany;
	float near;
	vec2 viewport;
	float aspect;
	vec2 resolution;
};

struct Ray {
	vec3 position;
	vec3 direction;
};

uniform Camera camera;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec4 rasterColor;

out vec4 outputColor;

void main() {
	vec2 ndc = -1.0 + 2.0 * floor(camera.resolution * gl_FragCoord.xy / camera.viewport) / camera.resolution;
	Ray ray = Ray(camera.position, normalize(camera.forward + camera.tany * (camera.aspect * ndc.x * camera.right + ndc.y * camera.up)));
	
	vec3 plane = normalize(vec3(0.0, 0.0, 1.0));
	float t = -dot(plane, ray.position) / dot(plane, ray.direction);
	
	vec3 point = ray.position + t * ray.direction;
	vec4 coord = projectionMatrix * viewMatrix * vec4(point, 1.0);
	
	float pattern = mod(floor(2.0 * point.x) + floor(2.0 * point.y), 2.0);
	outputColor = (0.1 * pattern + 0.8) * rasterColor;
	
	gl_FragDepth = (camera.near + coord.z) / coord.w;
}
