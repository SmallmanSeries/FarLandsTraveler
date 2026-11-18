package com.smallmanseries.farlandstraveler.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.client.render.entity.layers.GlowLayer;
import com.smallmanseries.farlandstraveler.common.entity.HerobrineEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

/**
 * Herobrine 的渲染器
 * <p>
 * 模型直接用 {@link HumanoidModel}（玩家形状的网格，见 {@code EventHandlerClient} 里注册的模型层），
 * 所以外观与玩家一致；皮肤用 {@code herobrine/char}，再用模组的 {@link GlowLayer} 叠加
 * {@code herobrine/eyes} 作为发光的眼睛。
 * <p>
 * 早前这里复用了玩家的 {@code PlayerModel} + {@code AvatarRenderState}，实测眼睛层不生效，
 * 因此改成与模组内其它生物一致的 {@link HumanoidModel} + {@link HumanoidRenderState} 组合。
 * 名称标签也在这里统一隐藏。
 */
public class HerobrineRender extends LivingEntityRenderer<HerobrineEntity, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "textures/entity/herobrine/char.png");

    public HerobrineRender(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(FLTLayers.HEROBRINE)), 0.5F);
        this.addLayer(new GlowLayer<>(this, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "textures/entity/herobrine/eyes.png")));
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return TEXTURE;
    }

    /**
     * 不显示名称标签
     */
    @Override
    protected boolean shouldShowName(HerobrineEntity entity, double distanceToCameraSq) {
        return false;
    }

    /**
     * 与玩家一致的显示比例（玩家渲染时会整体缩小到 0.9375）
     */
    @Override
    protected void scale(HumanoidRenderState state, PoseStack poseStack) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
