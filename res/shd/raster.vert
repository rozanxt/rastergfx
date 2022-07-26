#version 330 core

vec2 raster[6] = vec2[](
	vec2(-1.0, -1.0), vec2(1.0, -1.0), vec2(-1.0, 1.0), vec2(-1.0, 1.0), vec2(1.0, -1.0), vec2(1.0, 1.0)
);

void main() {
	gl_Position = vec4(raster[gl_VertexID], 0.0, 1.0);
}
