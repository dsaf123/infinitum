package com.infinitum.infinitummod.blocks.basicgenerator;

import com.infinitum.infinitummod.tools.CustomEnergyStorage;
import com.infinitum.infinitummod.util.Config;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.concurrent.atomic.AtomicInteger;

import static com.infinitum.infinitummod.util.RegistryHandler.BASIC_GENERATOR_TILE;
import static net.minecraftforge.common.ForgeHooks.getBurnTime;

public class BasicGeneratorTile extends TileEntity implements ITickableTileEntity {

    private ItemStackHandler itemHandler = createHandler();
    private CustomEnergyStorage energyStorage = createEnergy();

    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    private int counter;



    public BasicGeneratorTile() {
        super(BASIC_GENERATOR_TILE.get());
    }


    @Override
    public void tick() {

        // Only let the server make these calculations
        if (world.isRemote) {
            return;
        }
        // If we are burning
        if (counter > 0) {
            counter--;
            energyStorage.addEnergy(Config.BASIC_GENERATOR_GENERATION.get());
            markDirty();
        }
        // If we are not burning after last change
        if (counter <= 0) {
            ItemStack stack = itemHandler.getStackInSlot(0);
            if (isItemValid(0, stack)) {

                // Remove that item and start generating
                ItemStack extracted = itemHandler.extractItem(0, 1, true);

                // TODO : If item is fuel in a bucket (lava + other mod items) leave the bucket

                // Vanilla getBurnTime gets how long fuel tagged items will burn for
                counter = getBurnTime(extracted);
                stack.shrink(1);
                markDirty();
            }
        }

        BlockState blockState = world.getBlockState(pos);
        if (blockState.get(BlockStateProperties.POWERED) != counter > 0) {
            world.setBlockState(pos, blockState.with(BlockStateProperties.POWERED, counter > 0),
                    Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);
        }
        sendOutPower();

    }


    private void sendOutPower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());

        if (capacity.get() > 0) {
            for (Direction direction : Direction.values()) {
                TileEntity te = world.getTileEntity(pos.offset(direction));
                if (te != null) {
                    boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
                                if (handler.canReceive()) {
                                    int received = handler.receiveEnergy(Math.min(capacity.get(), Config.BASIC_GENERATOR_MAX_EXTRACT.get()), false);
                                    capacity.set(capacity.get() - received);
                                    energyStorage.consumeEnergy(received);
                                    markDirty();
                                    return capacity.get() > 0;
                                } else {
                                    return true;
                                }
                            }
                    ).orElse(true);
                    if (!doContinue) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
        energy.invalidate();
    }

    @Override
    @MethodsReturnNonnullByDefault
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());
        tag.putInt("counter", counter);
        return super.write(tag);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void read(BlockState state, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        energyStorage.deserializeNBT(tag.getCompound("energy"));

        counter = tag.getInt("counter");

        super.read(state, tag);
    }
    private ItemStackHandler createHandler() {
            return new ItemStackHandler(1) {
                @Override
                protected void onContentsChanged(int slot) {
                    markDirty();
                    super.onContentsChanged(slot);
                }

                @Override
                public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                    if (!BasicGeneratorTile.isItemValid(slot, stack)) {
                        return false;
                    }
                    return true;
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

    public static boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return getBurnTime(stack) > 0;
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(Config.BASIC_GENERATOR_CAPACITY.get(), 0) {
            @Override
            protected void onEnergyChanged() {
                markDirty();
            }
        };
    }



}
