package com.teamaurora.bayou_blues.core.mixin;

import com.teamaurora.bayou_blues.common.block.BeardMossBlockBlock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBlockRenderTypes.class)
public class ItemBlockRenderTypesMixin {
    @Shadow
    private static boolean renderCutout;

    @Inject(method = "getChunkRenderType", at = @At("HEAD"), cancellable = true)
    private static void getChunkRenderType(BlockState blockState, CallbackInfoReturnable<RenderType> cir) {
        if (blockState.getBlock() instanceof BeardMossBlockBlock)
            cir.setReturnValue(renderCutout ? RenderType.cutoutMipped() : RenderType.solid());
    }

    @Inject(method = "getMovingBlockRenderType", at = @At("HEAD"), cancellable = true)
    private static void getMovingBlockRenderType(BlockState blockState, CallbackInfoReturnable<RenderType> cir) {
        if (blockState.getBlock() instanceof BeardMossBlockBlock)
            cir.setReturnValue(renderCutout ? RenderType.cutoutMipped() : RenderType.solid());
    }
}
