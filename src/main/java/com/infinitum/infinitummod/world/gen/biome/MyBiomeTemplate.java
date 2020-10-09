package com.infinitum.infinitummod.world.gen.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;

public abstract class MyBiomeTemplate {
    abstract void configureBiome(Biome.Builder builder);
    abstract void configureGeneration(BiomeGenerationSettings.Builder builder);
    abstract void configureMobSpawns(MobSpawnInfo.Builder builder);

    public final Biome build()
    {
        // Object that assists in building a new biome
        Biome.Builder biomeBuilder = new Biome.Builder();
        this.configureBiome(biomeBuilder);                                                          // Call the abstract method to make the right changes

        // Configure the biome generation
        BiomeGenerationSettings.Builder biomeGenBuilder = new BiomeGenerationSettings.Builder();    
        this.configureGeneration(biomeGenBuilder);                                                  // Call the abstract method to make the right changes
        biomeBuilder.withGenerationSettings(biomeGenBuilder.build());                                // biomeBuilder.generationSettings(biomeGenBuilder.build());

        // Configure mob spawning
        MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();
        this.configureMobSpawns(mobSpawnBuilder);                                                   // Call the abstract method to make the right changes        
        biomeBuilder.withMobSpawnSettings(mobSpawnBuilder.copy());                                // biomeBuilder.mobSpawnSettings(mobSpawnBuilder.build());

        // Configure and build the biome
        return biomeBuilder.build();                                                        // biomeBuilder.build();
    }
}
