#version 150 core

in vec3 vertexColor;
in vec2 textureCoord;
in vec2 positiono;

out vec4 fragColor;

uniform sampler2D texImage;





void main() {
	
	
	
	

    vec4 textureColor = texture(texImage, textureCoord);
    
     
    // lighting fragColor = vec4(vertexColor, 1.0) * textureColor * normalizeTo(0, 1.5, lighting);
    fragColor = vec4(vertexColor, 1.0) * textureColor;
}

