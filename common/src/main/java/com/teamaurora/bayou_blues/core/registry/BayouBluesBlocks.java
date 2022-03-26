package com.teamaurora.bayou_blues.core.registry;

import com.teamaurora.bayou_blues.common.block.*;
import com.teamaurora.bayou_blues.common.block.thatch.ThatchBlock;
import com.teamaurora.bayou_blues.common.block.thatch.ThatchSlabBlock;
import com.teamaurora.bayou_blues.common.block.thatch.ThatchStairBlock;
import com.teamaurora.bayou_blues.common.item.AlgaeItem;
import com.teamaurora.bayou_blues.common.item.FollowItemLike;
import com.teamaurora.bayou_blues.common.item.FuelBlockItem;
import com.teamaurora.bayou_blues.common.item.LilyItem;
import com.teamaurora.bayou_blues.core.BayouBlues;
import com.teamaurora.bayou_blues.core.registry.util.Woodset;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author ebo2022
 */
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


    /* Other Cypress Blocks */

    public static final Supplier<Block> HANGING_CYPRESS_LEAVES = registerBlock("hanging_cypress_leaves", () -> new HangingCypressLeavesBlock(Properties.CYPRESS_LEAVES), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final Supplier<Block> CYPRESS_KNEE = registerBlock("cypress_knee", () -> new CypressKneeBlock(Properties.CYPRESS_KNEE), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> LARGE_CYPRESS_KNEE = registerBlock("large_cypress_knee", () -> new DoubleCypressKneeBlock(Properties.CYPRESS_KNEE), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    /* Lilies */

    public static final Supplier<Block> BLUE_LILY = registerLily("blue_lily");
    public static final Supplier<Block> LIGHT_GRAY_LILY = registerLily("light_gray_lily");
    public static final Supplier<Block> CYAN_LILY = registerLily("cyan_lily");
    public static final Supplier<Block> LIGHT_BLUE_LILY = registerLily("light_blue_lily");
    public static final Supplier<Block> MAGENTA_LILY = registerLily("magenta_lily");
    public static final Supplier<Block> PINK_LILY = registerLily("pink_lily");
    public static final Supplier<Block> PURPLE_LILY = registerLily("purple_lily");
    public static final Supplier<Block> WHITE_LILY = registerLily("white_lily");

    public static final Supplier<Block> POTTED_BLUE_LILY = registerPotted("potted_blue_lily", BLUE_LILY);
    public static final Supplier<Block> POTTED_LIGHT_GRAY_LILY = registerPotted("potted_light_gray_lily", LIGHT_GRAY_LILY);
    public static final Supplier<Block> POTTED_CYAN_LILY = registerPotted("potted_cyan_lily", CYAN_LILY);
    public static final Supplier<Block> POTTED_LIGHT_BLUE_LILY = registerPotted("potted_light_blue_lily", LIGHT_BLUE_LILY);
    public static final Supplier<Block> POTTED_MAGENTA_LILY = registerPotted("potted_magenta_lily", MAGENTA_LILY);
    public static final Supplier<Block> POTTED_PINK_LILY = registerPotted("potted_pink_lily", PINK_LILY);
    public static final Supplier<Block> POTTED_PURPLE_LILY = registerPotted("potted_purple_lily", PURPLE_LILY);
    public static final Supplier<Block> POTTED_WHITE_LILY = registerPotted("potted_white_lily", WHITE_LILY);



    /* Algae */

    public static final Supplier<Block> ALGAE = registerAlgae("algae");
    public static final Supplier<Block> ALGAE_THATCH = registerBlock("algae_thatch", () -> new ThatchBlock(Properties.ALGAE_THATCH), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final Supplier<Block> ALGAE_THATCH_SLAB = registerBlock("algae_thatch_slab", () -> new ThatchSlabBlock(Properties.ALGAE_THATCH), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final Supplier<Block> ALGAE_THATCH_STAIRS = registerBlock("algae_thatch_stairs" ,() -> new ThatchStairBlock(BayouBluesBlocks.ALGAE_THATCH.get().defaultBlockState(), Properties.ALGAE_THATCH), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));


    /* Other */

    public static final Supplier<Block> BEARD_MOSS_BLOCK = registerFuelBlock("beard_moss_block", () -> new BeardMossBlockBlock(Properties.BEARD_MOSS_BLOCK), 800, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> BEARD_MOSS = registerFuelBlock("beard_moss", () -> new BeardMossBlock(Properties.BEARD_MOSS), 800, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> GIANT_FERN = registerBlock("giant_fern", () -> new DoublePlantBlock(Properties.GIANT_FERN), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    private static Supplier<Block> registerBlock(String id, Supplier<Block> block, Item.Properties properties) {
        Supplier<Block> register = BLOCKS.register(id, block);
        BayouBluesItems.ITEMS.register(id, () -> new BlockItem(register.get(), properties));
        return register;
    }

    private static Supplier<Block> registerBlockNoItem(String id, Supplier<Block> block) {
        Supplier<Block> register = BLOCKS.register(id, block);
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

    private static Supplier<Block> registerLily(String id) {
        Supplier<Block> register = BLOCKS.register(id, () -> new LilyFlowerBlock(Properties.LILY));
        BayouBluesItems.ITEMS.register(id, () -> new LilyItem(register.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        return register;
    }

    private static Supplier<Block> registerPotted(String id, Supplier<Block> block) {
        Supplier<Block> register = BLOCKS.register(id, () -> new FlowerPotBlock(block.get(), Properties.POTTED_LILY));
        return register;
    }

    private static Supplier<Block> registerAlgae(String id) {
        Supplier<Block> register = BLOCKS.register(id, () -> new AlgaeBlock(Properties.ALGAE));
        BayouBluesItems.ITEMS.register(id, () -> new AlgaeItem(register.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        return register;
    }

    public static final class Properties {
        public static final BlockBehaviour.Properties CYPRESS_LEAVES = BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES);
        public static final BlockBehaviour.Properties CYPRESS_KNEE = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD).noOcclusion();
        public static final BlockBehaviour.Properties LILY = BlockBehaviour.Properties.copy(Blocks.LILY_PAD);
        public static final BlockBehaviour.Properties POTTED_LILY = BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM);
        public static final BlockBehaviour.Properties ALGAE = BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.LILY_PAD).noOcclusion().noCollission();
        public static final BlockBehaviour.Properties ALGAE_THATCH = BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_LIGHT_GREEN).strength(0.5F).sound(SoundType.GRASS).noOcclusion();
        public static final BlockBehaviour.Properties BEARD_MOSS_BLOCK = BlockBehaviour.Properties.of(Material.PLANT).strength(0.1F).sound(SoundType.MOSS);
        public static final BlockBehaviour.Properties BEARD_MOSS = BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.MOSS).noOcclusion().noCollission().randomTicks();
        public static final BlockBehaviour.Properties GIANT_FERN = BlockBehaviour.Properties.copy(Blocks.TALL_GRASS);
    }
}
