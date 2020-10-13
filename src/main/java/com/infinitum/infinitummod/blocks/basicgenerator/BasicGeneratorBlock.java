package com.infinitum.infinitummod.blocks.basicgenerator;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

/**
 * BasicGeneratorBlock defines and modifies all of the block level data for the Basic Generator, including but not
 * limited to: BlockState information (Direction the block faces and whether it is powered on), harvestLevel,
 * harvestTool, the light level it emits how it gets placed, default state
 */
public class BasicGeneratorBlock extends Block {
    /**
     * Default constructor
     */
    public BasicGeneratorBlock() {
        super(Block.Properties.create(Material.IRON )
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE)
                .setLightLevel((v) -> 14)

        );
    }


    /**
     * BasicGenerator has a tile entity with class name name BasicGeneratorTile
     * @param state the current state of the block
     * @return true always
     */
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


    /**
     * getLightValue returns the light value that is emitted by the object. Note: BasicGenerator only emits light when
     * it is powered on
     * @param state the blocks current state
     * @param world the world the block is in
     * @param pos the position of the block in the corresponding world
     * @return if the Basic Generator is powered then its light value is returned. else 0 is returned
     */
    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(BlockStateProperties.POWERED) ? super.getLightValue(state, world, pos) : 0;
    }

    /**
     * When the Basic Generator is placed it should be powered off by default and facing the opposite direction the player is looking
     */
    /**
     * getStateForPlacement gets the state for a block when its first placed.
     * When the Basic Generator is placed it should be powered off by default and facing the opposite direction the player is looking
     * @param context current BlockItemUseContext when the block gets placed
     * @return the state for the new block when its placed
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite()).with(BlockStateProperties.POWERED, false);
    }



    /**
     * createTileEntity initializes a new TileEntity for the block. (calls default constructor for class BlockGeneratorTile)
     * @param state Blocks current state
     * @param world World the block is located in
     * @return a new BasicGeneratorTile object
     */
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BasicGeneratorTile();
    }


    /**
     * When the Basic Generator is placed by an entity it should be facing the opposite direction the player is looking
     * TODO : Check if this is obsolete since getStateForPlacement is Overridden
     * @param worldIn the world the block is located in
     * @param pos the position of the block in the world
     * @param state the current state of the block
     * @param placer who placed the block
     * @param stack the itemstack from which the block was placed
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        //super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (placer != null) {
            worldIn.setBlockState(pos, state.with(BlockStateProperties.FACING, getFacingFromEntity(pos, placer)), 2);
        }
    }

    /**
     *
     */
    /**
     * getFacingFromEntity is a helper function for onBlockPlacedBy function to determine the direction the block should be facing
     * @param clickedBlock the position of the block that was clicked
     * @param entity the entity that clicked the block
     * @return Direction the block should face
     */
    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
        return Direction.getFacingFromVector(  (entity.getPosX() - clickedBlock.getX()), (entity.getPosY() - clickedBlock.getY()), (entity.getPosZ() - clickedBlock.getZ()));
    }


    /**
     * fillStateContainer adds additional BlockState information (Facing direction and Powered from vanilla)
     * @param builder builder to add to
     */
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder); // Is empty, but perhaps it could be changed in the future to include more state information
        builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
    }
    /**
     *
     */
    /**
     * onBlockActivated Adds right click functionality. Note: is deprecated but this means to only let Minecraft call this function
     * @param state the blocks current state
     * @param worldIn the world the block is in
     * @param pos the position of the block
     * @param player the player the activated the block
     * @param handIn the hand that activated (not sure... perhaps left/right clicking?)
     * @param hit whether the block was hit or not
     * @return whether the action was successful or not
     */
    @SuppressWarnings("deprication")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof BasicGeneratorTile) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.infinitummod.basicgenerator");
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new BasicGeneratorContainer(i, worldIn, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Container Provider is missing... Perhaps this item existed before this was implemented");
            }
        }

        return ActionResultType.SUCCESS;
    }
}

