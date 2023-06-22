#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;
uniform sampler2D MainDepthSampler;

uniform vec4 ColorModulate;
uniform float NearPlane;
uniform float FarPlane;

in vec2 texCoord;

out vec4 fragColor;

void main(){
    vec4 CurrTexel = texture(DiffuseSampler, texCoord);
    float CurrDepth = texture(DiffuseDepthSampler, texCoord).r;
    float BgDepth = texture(MainDepthSampler, texCoord).r;
    float currNdc = CurrDepth * 2.0 - 1.0;
    float bgNdc = BgDepth * 2.0 - 1.0;
    float linearCurrDepth = (2.0 * NearPlane * FarPlane) / (FarPlane + NearPlane - currNdc * (FarPlane - NearPlane));
    float linearBgDepth = (2.0 * NearPlane * FarPlane) / (FarPlane + NearPlane - bgNdc * (FarPlane - NearPlane));
    float softness = 0.1 * (linearBgDepth - linearCurrDepth);

    fragColor = vec4(CurrTexel.r, CurrTexel.g, CurrTexel.b, min(softness, CurrTexel.a));
}
