#version 150 core

in vec2 position;
in vec3 color;
in vec2 texcoord;
in vec2 posInGame;
in float jungle;


out vec3 vertexColor;
out vec2 textureCoord;
out vec2 positiono;
flat out float jungleOut;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;



void main() {
    vertexColor = color;
    textureCoord = texcoord;
    jungleOut = jungle;
    positiono = posInGame;
    
    mat4 mvp = projection * view * model;
    gl_Position = mvp * vec4(position, 0.0, 1.0);
    
    
}
