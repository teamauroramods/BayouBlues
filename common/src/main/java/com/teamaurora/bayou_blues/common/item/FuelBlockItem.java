package com.teamaurora.bayou_blues.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * @author JustinPlayzz
 * @author Steven
 * @author ebo2022
 */
public class FuelBlockItem extends BlockItem {
    private int burnTime;

    public FuelBlockItem(Block block, int burnTimeIn, Item.Properties properties) {
        super(block, properties);
        this.burnTime = burnTimeIn;
    }

    public int getBurnTime(ItemStack itemStack) {
        return this.burnTime;
    }
}
