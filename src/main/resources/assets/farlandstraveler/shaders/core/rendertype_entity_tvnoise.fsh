#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:globals.glsl>

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;

#ifndef EMISSIVE
in vec4 lightMapColor;
#endif

in vec2 texCoord0;

out vec4 fragColor;

float noise(vec2 coord) {
    return fract(sin(dot(coord + GameTime, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    vec4 color = texture(Sampler0, texCoord0);
        if (color.a < 0.1) {
            discard;
        }

    vec4 newColor = vec4(noise(texCoord0), noise(texCoord0), noise(texCoord0), 0.72);
    newColor *= ColorModulator;

#ifndef EMISSIVE
    newColor *= lightMapColor;
#endif

    fragColor = apply_fog(newColor, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
