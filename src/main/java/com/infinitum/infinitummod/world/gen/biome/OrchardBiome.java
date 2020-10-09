package com.infinitum.infinitummod.world.gen.biome;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import static com.infinitum.infinitummod.world.gen.feature.ModFeatures.*;

public class OrchardBiome extends MyBiomeTemplate {

    @Override
    void configureBiome(Biome.Builder builder) {

        BiomeAmbience.Builder ambience = new BiomeAmbience.Builder();

        ambience.setFogColor(1);
        ambience.setWaterColor(10);
        ambience.setWaterFogColor(15);
        ambience.withSkyColor(20);
        builder.precipitation(Biome.RainType.RAIN)
        .category(Biome.Category.FOREST)
        .depth(0.0F).scale(0.0F)
        .temperature(0.8F)
        .setEffects(ambience.build())
        .downfall(0.4F);

    }

    @Override
    void configureGeneration(BiomeGenerationSettings.Builder builder) {
        // builder.surfaceBuilder()
        builder.withSurfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));

        // Structures
        DefaultBiomeFeatures.withBadlandsStructures(builder);
        builder.withStructure(StructureFeatures.field_244159_y);

        // Underground
        DefaultBiomeFeatures.withCavesAndCanyons(builder); // Caves and Canyons
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder); // Water and Lava Lakes
        //DefaultBiomeFeatures.func_243731_ao(builder); // Nether Ore things
        DefaultBiomeFeatures.withInfestedStone(builder);  // Infested Ore
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder); // Ores odd
        DefaultBiomeFeatures.withOverworldOres(builder);
        //DefaultBiomeFeatures.func_243761_u(builder); // bamboo, bamboo vegetation
        DefaultBiomeFeatures.withMushroomBiomeVegetation(builder);

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GLOW_ORE);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DIAMOND_BLOCK_ORE);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, TIN_ORE_FEATURE);

        //builder.func_242513_a(p_242513_1_, p_242513_2_)
        ////////////////////////////////////////////////////////////

        // Vegetation
        /*
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BOPConfiguredFeatures.ORCHARD_TREES);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BOPConfiguredFeatures.ORCHARD_FLOWERS);

        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BOPConfiguredFeatures.ROSE_BUSH_1);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BOPConfiguredFeatures.SPROUTS_5);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BOPConfiguredFeatures.STANDARD_GRASS_12);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUGAR_CANE);
        */
        ////////////////////////////////////////////////////////////

        // Other Features
        /*
        DefaultBiomeFeatures.addDefaultSprings(builder);
        DefaultBiomeFeatures.addSurfaceFreezing(builder);
        */

    }

    @Override
    void configureMobSpawns(MobSpawnInfo.Builder builder) {
        // Entity spawning
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4));            // builder.addSpawn(EntityClassification...)
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));
        builder.withSpawner(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 8, 8));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SPIDER, 100, 4, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE, 95, 4, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 100, 4, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.CREEPER, 100, 4, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 100, 4, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 10, 1, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.WITCH, 5, 1, 1));

    }

    
}
