package com.infinitum.infinitummod.tools;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

/**
 * This CustomEnergyStorage class extends Forge's standard implementation (RF/FE) to allow for our blocks to have
 * additional functionality.
 */
public class CustomEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {

    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    protected void onEnergyChanged() {

    }

    /**
     * This function sets the energy stored
     * @param energy the new energy value that will be set
     */
    public void setEnergy(int energy) {
        this.energy = energy;
        onEnergyChanged();
    }


    /**
     * This function adds energy to the current energy value stored
     * @param energy the amount of energy that will be added
     */
    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }
        onEnergyChanged();
    }

    /**
     * This function removes energy from the current energy value stored
     * @param energy the amount of energy that will be removed
     */
    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
        onEnergyChanged();
    }

    /**
     * This function assists in serializing this class for NBT tags
     * @return returns the tag with additions needed to serialize this class
     */
    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("energy", getEnergyStored());
        return tag;
    }

    /**
     * This function assists in deserializing this class for NBT tags
     */
    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setEnergy(nbt.getInt("energy"));
    }
}