package com.teamaurora.bayou_blues.core.registry;

import com.teamaurora.bayou_blues.common.block.BeardMossBlock;
import com.teamaurora.bayou_blues.common.block.BeardMossBlockBlock;
import com.teamaurora.bayou_blues.common.block.HangingCypressLeavesBlock;
import com.teamaurora.bayou_blues.common.item.FollowItemLike;
import com.teamaurora.bayou_blues.common.item.FuelBlockItem;
import com.teamaurora.bayou_blues.core.BayouBlues;
import com.teamaurora.bayou_blues.core.registry.util.Woodset;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;

public class BayouBluesBlocks {

    public static final PollinatedRegistry<Block> BLOCKS = PollinatedRegistry.create(Registry.BLOCK, BayouBlues.MOD_ID);

    /* Cypress Woodset */

    private static final Woodset CYPRESS = new Woodset(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_GREEN);

    public static final Supplier<Block> STRIPPED_CYPRESS_LOG = registerWoodsetBlock("stripped_cypress_log", CYPRESS::stripped_log, CreativeModeTab.TAB_BUILDING_BLOCKS, Blocks.STRIPPED_OAK_LOG);
    public static final Supplier<Block> STRIPPED_CYPRESS_WOOD = registerWoodsetBlock("stripped_cypress_wood", CYPRESS::stripped_wood, CreativeModeTab.TAB_BUILDING_BLOCKS, Blocks.STRIPPED_OAK_WOOD);
    public static final Supplier<Block> CYPRESS_LOG = registerWoodsetBlock("cypress_log", CYPRESS::log, CreativeModeTab.TAB_BUILDING_BLOCKS, Blocks.OAK_LOG);
    public static final Supplier<Block> CYPRESS_WOOD = registerWoodsetBlock("cypress_wood", CYPRESS::wood, CreativeModeTab.TAB_BUILDING_BLOCKS, Blocks.OAK_WOOD);
    public static final Supplier<Block> CYPRESS_LEAVES = registerBlock("cypress_leaves", () -> new LeavesBlock(Properties.CYPRESS_LEAVES), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> CYPRESS_PLANKS = registerWoodsetBlock("cypress_planks", CYPRESS::planks, CreativeModeTab.TAB_BUILDING_BLOCKS, Blocks.OAK_PLANKS);
    public static final Supplier<Block> CYPRESS_SLAB = registerWoodsetBlock("cypress_slab", CYPRESS::slab, CreativeModeTab.TAB_BUILDING_BLOCKS, Blocks.OAK_SLAB);
    public static final Supplier<Block> CYPRESS_STAIRS = registerWoodsetBlock("cypress_stairs", () -> CYPRESS.stairs(CYPRESS_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS, Blocks.OAK_STAIRS);
    public static final Supplier<Block> CYPRESS_PRESSURE_PLATE = registerWoodsetBlock("cypress_pressure_plate", CYPRESS::pressurePlate, CreativeModeTab.TAB_REDSTONE, Blocks.OAK_PRESSURE_PLATE);
    public static final Supplier<Block> CYPRESS_BUTTON = registerWoodsetBlock("cypress_button", CYPRESS::button, CreativeModeTab.TAB_REDSTONE, Blocks.OAK_BUTTON);
    public static final Supplier<Block> CYPRESS_FENCE = registerWoodsetBlock("cypress_fence", CYPRESS::fence, CreativeModeTab.TAB_DECORATIONS, Blocks.OAK_FENCE);
    public static final Supplier<Block> CYPRESS_FENCE_GATE = registerWoodsetBlock("cypress_fence_gate", CYPRESS::fenceGate, CreativeModeTab.TAB_REDSTONE, Blocks.OAK_FENCE_GATE);
    public static final Supplier<Block> CYPRESS_DOOR = registerWoodsetBlock("cypress_door", CYPRESS::door, CreativeModeTab.TAB_REDSTONE, Blocks.OAK_DOOR);
    public static final Supplier<Block> CYPRESS_TRAPDOOR = registerWoodsetBlock("cypress_trapdoor", CYPRESS::trapdoor, CreativeModeTab.TAB_REDSTONE, Blocks.OAK_TRAPDOOR);

    public static final Supplier<Block> HANGING_CYPRESS_LEAVES = registerBlock("hanging_cypress_leaves", () -> new HangingCypressLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final Supplier<Block> BEARD_MOSS_BLOCK = registerFuelBlock("beard_moss_block", () -> new BeardMossBlockBlock(Properties.BEARD_MOSS_BLOCK), 800, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> BEARD_MOSS = registerFuelBlock("beard_moss", () -> new BeardMossBlock(Properties.BEARD_MOSS), 800, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));


    private static Supplier<Block> registerBlock(String id, Supplier<Block> block, Item.Properties properties) {
        Supplier<Block> register = BLOCKS.register(id, block);
        BayouBluesItems.ITEMS.register(id, () -> new BlockItem(register.get(), properties));
        return register;
    }

    private static Supplier<Block> registerFuelBlock(String id, Supplier<Block> block, int burnTime, Item.Properties properties) {
        Supplier<Block> register = BLOCKS.register(id, block);
        BayouBluesItems.ITEMS.register(id, () -> new FuelBlockItem(register.get(), burnTime, properties));
        return register;
    }

    private static Supplier<Block> registerWoodsetBlock(String id, Supplier<Block> block, CreativeModeTab tab, Block followBlock) {
        Supplier<Block> register = BLOCKS.register(id, block);
        BayouBluesItems.ITEMS.register(id, () -> new FollowItemLike(register.get(), new Item.Properties(), tab, followBlock));
        return register;
    }

    public static final class Properties {
        public static final BlockBehaviour.Properties CYPRESS_LEAVES = BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES);
        public static final BlockBehaviour.Properties BEARD_MOSS_BLOCK = BlockBehaviour.Properties.of(Material.PLANT).strength(0.1F).sound(SoundType.MOSS);
        public static final BlockBehaviour.Properties BEARD_MOSS = BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.MOSS).noOcclusion().noCollission().randomTicks();
    }
}
