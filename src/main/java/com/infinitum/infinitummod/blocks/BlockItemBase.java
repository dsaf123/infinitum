package com.infinitum.infinitummod.blocks;

import com.infinitum.infinitummod.InfinitumMod;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;


public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block blk) {
        super(blk, new Item.Properties().group(InfinitumMod.TAB));
    }
    
}
