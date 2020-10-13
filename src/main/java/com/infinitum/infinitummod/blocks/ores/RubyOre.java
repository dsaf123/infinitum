package com.infinitum.infinitummod.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class RubyOre extends OreBlock {

    public RubyOre() {
        super(Block.Properties.create(Material.IRON )
            .hardnessAndResistance(5.0f, 6.0f)
            .sound(SoundType.METAL) 
            .harvestLevel(3)
            .harvestTool(ToolType.PICKAXE)
            .setLightLevel((val) -> 10)
        );
    }
    
    @Override
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return 1;
    }
    
}
