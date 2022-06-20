package com.teamaurora.bayou_blues.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author JustinPlayzz
 * @author Steven
 * @author ebo2022
 */
public class LilyItem extends TabInsertBlockItem {
    public LilyItem(Block flower, Item.Properties properties) {
        super(Items.WITHER_ROSE, flower, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() == Blocks.LILY_PAD) {
            world.setBlockAndUpdate(pos, super.getBlock().defaultBlockState());
            ItemStack stack = context.getItemInHand();
            stack.shrink(1);
            SoundType soundtype = SoundType.LILY_PAD;

            // Never know what other mods might do; player could be null
            if (context.getPlayer() != null) {
                Player playerentity = context.getPlayer();
                world.playSound(playerentity, pos, this.getPlaceSound(state), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                playerentity.swing(context.getHand());
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected SoundEvent getPlaceSound(BlockState state) {
        return state.getSoundType().getPlaceSound();
    }
}