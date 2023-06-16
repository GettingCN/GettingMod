package cn.cngetting.gettingmod.block;

import cn.cngetting.gettingmod.block.sapling.RichSaplingGenerator;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import cn.cngetting.gettingmod.block.entities.BoxBlockEntity;
import cn.cngetting.gettingmod.block.entities.ContainerEntity;
import cn.cngetting.gettingmod.screen.handler.BoxBlockScreenHandler;
import cn.cngetting.gettingmod.util.ModId;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public class ModBlocks {
    /*  方块创建
        public static final Block * = new Block(FabricBlockSettings.of(Material.*).hardness(*));
     */

    public static final Block GOLD_BLOCK_DISGUISE;
    public static final Block DECO_BLOCK;
    public static final ContainsWaterBlock RED_FRAME;
    public static final ContainerBlock CONTAINER_BLOCK;
    public static final Block FRAME;
    public static final Block FOUR_SIDED_FURNACE;
    public static final BoxBlock BOX_BLOCK;
    public static final SaplingBlock RICH_SAPLING;

    public static BlockEntityType<ContainerEntity> CONTAINER_ENTITY;
    public static BlockEntityType<BoxBlockEntity> BOX_ENTITY;

    // 我们在这里使用 registerSimple 因为实体不是 ExtendedScreenHandlerFactory
    // 而是 NamedScreenHandlerFactory.
    // 后面的教程中，你将会看到 ExtendedScreenHandlerFactory 能做什么！
    public static final ScreenHandlerType<BoxBlockScreenHandler> BOX_BLOCK_SCREEN_HANDLER;

    static {
        GOLD_BLOCK_DISGUISE = new DisguiseBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
        DECO_BLOCK = new DecoBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
        RED_FRAME = new ContainsWaterBlock(FabricBlockSettings.of(Material.GLASS).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque().dropsNothing().allowsSpawning(ContainsWaterBlock::never).solidBlock(ContainsWaterBlock::never).suffocates(ContainsWaterBlock::never).blockVision(ContainsWaterBlock::never));
        CONTAINER_BLOCK = new ContainerBlock(FabricBlockSettings.of(Material.STONE));
        FRAME = new Block(FabricBlockSettings.of(Material.GLASS).hardness(0.3F).nonOpaque());
        FOUR_SIDED_FURNACE = new Block(FabricBlockSettings.of(Material.STONE).hardness(4.0f));
        BOX_BLOCK = new BoxBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0F));
        RICH_SAPLING = new /*Rich*/SaplingBlock(new RichSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING));

        CONTAINER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, ModId.of("container_block"), FabricBlockEntityTypeBuilder.create(ContainerEntity::new, CONTAINER_BLOCK).build(null));
        BOX_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, ModId.of("four_sided_furnace"),FabricBlockEntityTypeBuilder.create(BoxBlockEntity::new, FOUR_SIDED_FURNACE).build(null));

        BOX_BLOCK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(ModId.of("box_block"), BoxBlockScreenHandler::new);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, GOLD_BLOCK_DISGUISE);
    }

    public static void register() {
        ModOre.register();
        //方块实体注册
        register("gold_block_disguise", GOLD_BLOCK_DISGUISE);
        register("deco_block", DECO_BLOCK);
        register("red_frame", RED_FRAME);
        register("container_block", CONTAINER_BLOCK);
        register("frame", FRAME);
        register("four_sided_furnace", FOUR_SIDED_FURNACE);
        register("box_block", BOX_BLOCK);
        register("rich_sapling", RICH_SAPLING);
    }

    //  方块 与 方块物品 注册
    static void register(String id, Block block) {
        register(id, new BlockItem(block, new Item.Settings()), id, block);
    }

    static void register(String itemID, Item item, String blockID , Block block){
        Identifier identifier = ModId.of(blockID);
        if (Objects.equals(itemID, blockID)) {
            Registry.register(Registry.BLOCK, identifier, block);
            Registry.register(Registry.ITEM, identifier, item);
        }else {
            Registry.register(Registry.BLOCK, identifier, block);
            Registry.register(Registry.ITEM, ModId.of(itemID), item);

        }
    }
}
