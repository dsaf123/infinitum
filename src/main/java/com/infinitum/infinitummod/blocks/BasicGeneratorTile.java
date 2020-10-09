package com.infinitum.infinitummod.blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.infinitum.infinitummod.util.RegistryHandler.BASIC_GENERATOR_TILE;

public class BasicGeneratorTile extends TileEntity implements ITickableTileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public BasicGeneratorTile() {
        super(BASIC_GENERATOR_TILE.get());
    }


    @Override
    public void tick() {

    }

    @Override
    @MethodsReturnNonnullByDefault
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.write(compound);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void read(BlockState state, CompoundNBT nbt) {

        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }
    private ItemStackHandler createHandler() {
            return new ItemStackHandler(1) {

                @Override
                public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                    if (stack.getItem() != Items.DIAMOND) {
                        return false;
                    }
                    return super.isItemValid(slot, stack);
                }
            };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

}
