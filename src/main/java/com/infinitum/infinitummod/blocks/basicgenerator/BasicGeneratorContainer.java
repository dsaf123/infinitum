package com.infinitum.infinitummod.blocks.basicgenerator;

import com.infinitum.infinitummod.tools.CustomEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;


import javax.annotation.Nonnull;

import static com.infinitum.infinitummod.util.RegistryHandler.BASIC_GENERATOR;
import static com.infinitum.infinitummod.util.RegistryHandler.BASIC_GENERATOR_CONTAINER;

/**
 * BasicGeneratorContainer is a container (holds information about the items and energy stored in the Generator and the
 * playerEntity that opened it) and keeps track of it at the server side as well as handles the transfer of items and
 * energy
 */
public class BasicGeneratorContainer extends Container {

    private final TileEntity tileEntity;
    private final PlayerEntity playerEntity;
    private final IItemHandler playerInventory;

    /**
     * BasicGeneratorConstructor needs a windowId for the GUI, the world, its position and information about the player
     *      * that opened it and it saves this information in member variables
     * @param windowId id of the gui to open
     * @param world world the block is located in
     * @param pos the position of the block in the corresponding world
     * @param playerInventory the player's inventory
     * @param player the actual player
     */
    public BasicGeneratorContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(BASIC_GENERATOR_CONTAINER.get(), windowId);
        tileEntity = world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        layoutPlayerInventorySlots(8, 84);
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> addSlot(new SlotItemHandler(h, 0, 80, 40)));
        }
        trackPower();
    }

    /**
     * addSlotRange function adds a row of slots for the container (used for displaying the player's inventory)
     * @param handler an ItemHandler for the slots to display
     * @param index the row that will be displayed for appropriate positioning
     * @param x offset in the x (left to right) direction
     * @param y offset in the y (up to down) direction
     * @param amount the number of slots to create in each row
     * @param dx how much to space the slots by in the x direction
     * @return
     */
    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    /**
     * addSlotBox function creates multiple rows of container slots
     * @param handler an ItemHandler for the slots to display
     * @param index the row that will be displayed for appropriate positioning
     * @param x offset in the x (left to right) direction
     * @param y offset in the y (up to down) direction
     * @param horAmount the number of slots to create in each row
     * @param dx how much to space the slots by in the x direction
     * @param verAmount the number of columns
     * @param dy how much to space the slots by in the y direction
     * @return
     */
    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    /**
     * layoutPlayerInventorySlots lays out the entire player's inventory
     * @param leftCol how much to offset in the x direction from the left
     * @param topRow how much to offset in the y direction from the top
     */
    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    /**
     * canInteractWith determines if a player can intreact with the container
     * @param playerIn player that gets checked for the ability to interact
     * @return true if the player can interact with the container, false if they cannot
     */
    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        if (tileEntity.getWorld() == null) {
            return false;
        }
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, BASIC_GENERATOR.get());
    }

    /**
     * getEnergy gets the amount of energy that is stored (in the tile entity)
     * @return amount of energy that is stored
     */
    public int getEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    /**
     * trackPower() sets up power tracking on the server
     * Setup syncing of power from server to client so that the GUI can show the amount of power in the block
     */
    private void trackPower() {
        // track energy level on server (Only handles shorts so convert the int to two shorts).
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((CustomEnergyStorage)h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((CustomEnergyStorage)h).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }

    /**
     * transferStackInSlot transfers an item between the player and the container
     * @param playerIn player that is transferring the item
     * @param index index of the item slot to transfer
     * @return ItemStack of the item's that were NOT transferred
     */
    @Override
    @Nonnull
    public ItemStack transferStackInSlot(@Nonnull PlayerEntity playerIn, int index) {
        // Function taken from Furnace implementation
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            // IF clicking from inventory TO the Container
             if (index != 36) {
                if (BasicGeneratorTile.isItemValid(index, itemstack)) {

                    if (!this.mergeItemStack(itemstack1, 36, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                    // IF in inventory section (and not able to go into the container we put it in the hot bar)
                } else if (index >= 1 && index < 28) {
                    if (!this.mergeItemStack(itemstack1, 30, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                    // If in hot bar section (and not able to go into the container we put it in the inventory)
                } else if (index >= 30 && index < 38 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
                // If clicking from Container (index = 0 because only 1 slot) to inventory
            } else if (!this.mergeItemStack(itemstack1, 0, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }


}
