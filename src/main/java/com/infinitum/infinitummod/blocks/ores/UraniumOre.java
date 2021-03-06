package com.infinitum.infinitummod.blocks.ores;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class UraniumOre extends Block {
    public UraniumOre() {
        super(AbstractBlock.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 5.0f)
                .sound(SoundType.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setLightLevel((val) -> 1)
        );
    }
}
