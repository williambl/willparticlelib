#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;
uniform sampler2D MainDepthSampler;

uniform vec4 ColorModulate;

in vec2 texCoord;

out vec4 fragColor;

void main(){
    vec4 CurrTexel = texture(DiffuseSampler, texCoord);
    float CurrDepth = texture(DiffuseDepthSampler, texCoord).r;
    float BgDepth = texture(MainDepthSampler, texCoord).r;

    fragColor = CurrDepth < BgDepth ? CurrTexel * ColorModulate : vec4(0.0);
}
