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
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.infinitum.infinitummod.util.RegistryHandler.BASIC_GENERATOR_TILE;

public class BasicGeneratorTile extends TileEntity implements ITickableTileEntity {

    private final LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of( this::createEnergy);
    private int counter;

    public BasicGeneratorTile() {
        super(BASIC_GENERATOR_TILE.get());
    }


    @Override
    public void tick() {

    }

    @Override
    @MethodsReturnNonnullByDefault
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compound);
        });
        energy.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("energy", compound);
        });

        tag.putInt("counter", counter);
        return super.write(tag);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void read(BlockState state, CompoundNBT nbt) {
        CompoundNBT invTag = nbt.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        CompoundNBT energyTag = nbt.getCompound("energy");
        energy.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(energyTag));

        counter = nbt.getInt("counter");
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
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

    private IEnergyStorage createEnergy() {
        return new EnergyStorage(100000, 0);
    }

}
