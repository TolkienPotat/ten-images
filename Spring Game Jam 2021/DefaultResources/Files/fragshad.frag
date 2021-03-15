#version 150 core

in vec3 vertexColor;
in vec2 textureCoord;
in vec2 positiono;
flat in float jungleOut;

out vec4 fragColor;

uniform sampler2D texImage;





void main() {
	
	vec4 jungleColor;
	
	if (jungleOut > 0) {
    	jungleColor = vec4(0.39, 0.6, 0.09, 1);
    	
    } else {
    	jungleColor = vec4(1, 1, 1, 1);
    }
	
	

    vec4 textureColor = texture(texImage, textureCoord);
    
     
    // lighting fragColor = vec4(vertexColor, 1.0) * textureColor * normalizeTo(0, 1.5, lighting);
    fragColor = vec4(vertexColor, 1.0) * textureColor * jungleColor;
    
    
    
}

