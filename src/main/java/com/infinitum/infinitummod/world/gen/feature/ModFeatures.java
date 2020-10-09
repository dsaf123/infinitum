package com.infinitum.infinitummod.world.gen.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import com.infinitum.infinitummod.util.RegistryHandler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

import static com.infinitum.infinitummod.util.RegistryHandler.*;

public class ModFeatures {
    public static final ConfiguredFeature<?, ?> GLOW_ORE = createOreGenFeature("glow_ore_feature", 20, 100, 128, 10, 2, Blocks.GLOWSTONE.getDefaultState());
    public static final ConfiguredFeature<?, ?> DIAMOND_BLOCK_ORE = createOreGenFeature("diamond_block_feature", 0, 120, 128, 3, 1, Blocks.DIAMOND_BLOCK.getDefaultState());
    public static final ConfiguredFeature<?, ?> TIN_ORE_FEATURE = createOreGenFeature("tin_ore_feature", 20, 60, 128, 20, 6, TIN_ORE.get().getDefaultState());
    public static final ConfiguredFeature<?, ?> MITHRIL_ORE_FEATURE = createOreGenFeature("mithril_ore_feature", 20, 80, 128, 4, 1, MITHRIL_ORE.get().getDefaultState());
    public static final ConfiguredFeature<?, ?> ADAMANT_ORE_FEATURE = createOreGenFeature("adamant_ore_feature", 20, 90, 128, 4, 1, ADAMANT_ORE.get().getDefaultState());
    /*
    public static TopSolidRangeConfig DIAMOND_PLACEMENT = new TopSolidRangeConfig(0, 120, 128);
    public static ConfiguredPlacement<?> DIAMOND_CONFIGURED_PLACEMENT = new ConfiguredPlacement<TopSolidRangeConfig>(Placement.field_242907_l , DIAMOND_PLACEMENT).func_242731_b(1);
    public static OreFeatureConfig DIAMOND_CONFIG = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Blocks.DIAMOND_BLOCK.getDefaultState(), 6);
    public static ConfiguredFeature<?, ?> DIAMOND_BLOCK_ORE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "diamond_block_feature", Feature.ORE.withConfiguration(DIAMOND_CONFIG).withPlacement(DIAMOND_CONFIGURED_PLACEMENT));
    */


    public static ConfiguredFeature<?,? > createOreGenFeature(String featureName ,int bottomOffset, int topOffset, int max, int veinSize, int veinCount, BlockState blk) {

        // TopSolidRangeConfig(Bottom Offset, Top Offset, Maximum)
        //  Puts Veins Randomly between Bottom offset and MAX- Top Offset
        //  For Placement.field_242907_l (unbiased generation) its not such a big deal but with the others setting these makes more of a difference
        TopSolidRangeConfig tempPlacement = new TopSolidRangeConfig(bottomOffset, topOffset, max);
        // <> could be TopSolidRangeConfig, but since we want to change the vein count we do that with .func_242731_b (don't know a good mapping name) and it no longer is a TopSolidRangeConfig
        ConfiguredPlacement<?> tempConfiguredPlacement = new ConfiguredPlacement<TopSolidRangeConfig>(Placement.field_242907_l , tempPlacement).func_242731_b(veinCount);

        // OreFeatureConfig(TARGET, STATE, SIZE)
        //  TARGET: Blocks that get replaced (certain blocks have 'tags' in this case we are targeting blocks with the overworld base block, stone, dirt, gravel etc)
        //  STATE: BlockState to replace it with, typically the default state of our block
        //  SIZE: average VEIN Size....
        OreFeatureConfig tempOreConfig = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, blk, veinSize);
        ConfiguredFeature<?, ?> tempOreGenFeature = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, featureName, Feature.ORE.withConfiguration(tempOreConfig ).withPlacement(tempConfiguredPlacement));
        return tempOreGenFeature;

    }

}
