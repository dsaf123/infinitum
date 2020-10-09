package com.infinitum.infinitummod.world.gen.biome;

import com.infinitum.infinitummod.InfinitumMod;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infinitum.infinitummod.world.gen.feature.ModFeatures.*;

@Mod.EventBusSubscriber(modid = InfinitumMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeEventSubscriber {

    @SubscribeEvent(priority=EventPriority.HIGH)
    public static void onBiomeGetsLoaded(BiomeLoadingEvent ble) {
        if (ble.getCategory() == Biome.Category.NETHER) {

        } else if (ble.getCategory() == Biome.Category.THEEND) {

        } else {
            ble.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DIAMOND_BLOCK_ORE);
            ble.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GLOW_ORE);
            ble.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, TIN_ORE_FEATURE);
            ble.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MITHRIL_ORE_FEATURE);
            ble.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ADAMANT_ORE_FEATURE);
        }
    }
}
