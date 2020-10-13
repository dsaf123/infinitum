package com.infinitum.infinitummod.util;

import com.infinitum.infinitummod.blocks.*;
import com.infinitum.infinitummod.blocks.basicgenerator.BasicGeneratorBlock;
import com.infinitum.infinitummod.blocks.basicgenerator.BasicGeneratorContainer;
import com.infinitum.infinitummod.blocks.basicgenerator.BasicGeneratorTile;
import com.infinitum.infinitummod.blocks.ores.TinOre;
import com.infinitum.infinitummod.world.gen.biome.OrchardBiome;
import com.infinitum.infinitummod.InfinitumMod;
import com.infinitum.infinitummod.armor.ModArmorMaterial;
import com.infinitum.infinitummod.items.CircuitBoard;
import com.infinitum.infinitummod.items.ItemBase;
import com.infinitum.infinitummod.items.PoisonApple;
import com.infinitum.infinitummod.tools.ModItemTier;

import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;


public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS =  DeferredRegister.create(ForgeRegistries.ITEMS, InfinitumMod.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, InfinitumMod.MOD_ID);
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, InfinitumMod.MOD_ID);
    public static final DeferredRegister<Placement<?>> DECORATORS = DeferredRegister.create(ForgeRegistries.DECORATORS, InfinitumMod.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, InfinitumMod.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, InfinitumMod.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, InfinitumMod.MOD_ID);



    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
        DECORATORS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    // Items
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", ItemBase::new);
    public static final RegistryObject<PoisonApple> POISON_APPLE = ITEMS.register("poison_apple", PoisonApple::new);
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", ItemBase::new);
    public static final RegistryObject<Item> MITHRIL_INGOT = ITEMS.register("mithril_ingot", ItemBase::new);
    public static final RegistryObject<Item> ADAMANT_INGOT = ITEMS.register("adamant_ingot", ItemBase::new);

    public static final RegistryObject<Item> METAL_WIRING = ITEMS.register("iron_wire", ItemBase::new);
    public static final RegistryObject<Item> UNBAKED_CIRCUIT_BOARD = ITEMS.register("unbaked_circuit_board", ItemBase::new);
    public static final RegistryObject<CircuitBoard> CIRCUIT_BOARD = ITEMS.register("circuit_board", CircuitBoard::new);

    // Blocks
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", RubyBlock::new);
    public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore", TinOre::new);
    public static final RegistryObject<Block> MITHRIL_ORE = BLOCKS.register("mithril_ore", TinOre::new);
    public static final RegistryObject<Block> ADAMANT_ORE = BLOCKS.register("adamant_ore", TinOre::new);
    public static final RegistryObject<Block> BASIC_GENERATOR = BLOCKS.register("basic_generator", BasicGeneratorBlock::new);

    // Block Items
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItemBase(RUBY_BLOCK.get()));
    public static final RegistryObject<Item> TIN_ORE_ITEM = ITEMS.register("tin_ore", () -> new BlockItemBase(TIN_ORE.get()));
    public static final RegistryObject<Item> MITHRIL_ORE_ITEM = ITEMS.register("mithril_ore", () -> new BlockItemBase(MITHRIL_ORE.get()));
    public static final RegistryObject<Item> ADAMANT_ORE_ITEM = ITEMS.register("adamant_ore", () -> new BlockItemBase(ADAMANT_ORE.get()));
    public static final RegistryObject<Item> BASIC_GENERATOR_ITEM = ITEMS.register("basic_generator", () -> new BlockItemBase(BASIC_GENERATOR.get()));

    // Tools
    public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register("ruby_sword", () -> 
        new SwordItem(ModItemTier.RUBY, 2, -2.4F, new Item.Properties().group(InfinitumMod.TAB))
    );
    public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> 
        new PickaxeItem(ModItemTier.RUBY, 0, -2.8F, new Item.Properties().group(InfinitumMod.TAB))
    );
    // Armor
    public static final RegistryObject<ArmorItem> RUBY_HELMET = ITEMS.register("ruby_helmet", () -> 
        new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.HEAD, new Item.Properties().group(InfinitumMod.TAB)));

    public static final RegistryObject<ArmorItem> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate", () -> 
        new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.CHEST, new Item.Properties().group(InfinitumMod.TAB)));

    public static final RegistryObject<ArmorItem> RUBY_LEGGINGS = ITEMS.register("ruby_leggings", () -> 
        new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.LEGS, new Item.Properties().group(InfinitumMod.TAB)));

    public static final RegistryObject<ArmorItem> RUBY_BOOTS = ITEMS.register("ruby_boots", () -> 
        new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.FEET, new Item.Properties().group(InfinitumMod.TAB)));

    // TileEntities
    public static RegistryObject<TileEntityType<BasicGeneratorTile>> BASIC_GENERATOR_TILE = TILE_ENTITIES.register("basic_generator", () -> TileEntityType.Builder.create(BasicGeneratorTile::new, BASIC_GENERATOR.get()).build(null));

    // Containers
    public static final RegistryObject<ContainerType<BasicGeneratorContainer>> BASIC_GENERATOR_CONTAINER = CONTAINERS.register("basic_generator", () -> IForgeContainerType.create( (windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            World world = inv.player.getEntityWorld();
            return new BasicGeneratorContainer(windowId, world, pos, inv, inv.player);
        }
    ));

    // Screens


    // Biomes
    public static final RegistryObject<Biome> OrchardBiome = BIOMES.register("orchard_biome", () -> new OrchardBiome().build());


    
}