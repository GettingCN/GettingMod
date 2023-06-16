package cn.cngetting.gettingmod.block;

import cn.cngetting.gettingmod.util.ModId;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.Arrays;
import java.util.function.Predicate;

public class ModOre {
    //块匹配规则
    private static final BlockMatchRuleTest END_STONE;

    //矿物方块
    public static final Block TIN_ORE;
    private static final ConfiguredFeature<?, ?> OVERWORLD_TIN_ORE_CONFIGURED_FEATURE;
    private static final PlacedFeature OVERWORLD_TIN_ORE_PLACED_FEATURE;

    public static final Block TIN_BLOCK;
    private static final ConfiguredFeature<?, ?> END_TIN_BLOCK_CONFIGURED_FEATURE;
    private static final PlacedFeature END_TIN_BLOCK_PLACED_FEATURE;

    static {
        END_STONE = new BlockMatchRuleTest(Blocks.END_STONE);

        TIN_ORE = new Block(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
        OVERWORLD_TIN_ORE_CONFIGURED_FEATURE
                = configuredFeatures(Blocks.STONE, TIN_ORE, 9, null);
        OVERWORLD_TIN_ORE_PLACED_FEATURE = new PlacedFeature(
                RegistryEntry.of(OVERWORLD_TIN_ORE_CONFIGURED_FEATURE),
                Arrays.asList(
                        CountPlacementModifier.of(20), // 每个区块的矿脉数量
                        SquarePlacementModifier.of(), // 水平传播
                        HeightRangePlacementModifier.uniform(
                                YOffset.aboveBottom(32),
                                YOffset.belowTop(-16)) // 高度
                ));

        TIN_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
        END_TIN_BLOCK_CONFIGURED_FEATURE
                = configuredFeatures(Blocks.END_STONE, TIN_BLOCK, 9, END_STONE);
        END_TIN_BLOCK_PLACED_FEATURE = new PlacedFeature(
                RegistryEntry.of(END_TIN_BLOCK_CONFIGURED_FEATURE),
                Arrays.asList(
                        CountPlacementModifier.of(20),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(
                                YOffset.aboveBottom(-16),
                                YOffset.fixed(32))
                ));
    }

    static void register(){
            register("tin_ore", TIN_ORE,
                    OVERWORLD_TIN_ORE_CONFIGURED_FEATURE,
                    OVERWORLD_TIN_ORE_PLACED_FEATURE,
                    BiomeSelectors.foundInOverworld());

            register("tin_block", TIN_BLOCK,
                    END_TIN_BLOCK_CONFIGURED_FEATURE,
                    END_TIN_BLOCK_PLACED_FEATURE,
                    BiomeSelectors.foundInTheEnd());
    }

    private static void register (String id, Block block, ConfiguredFeature<?,?> configuredFeature, PlacedFeature placedFeature, Predicate<BiomeSelectionContext> whereToBeFound){
        Identifier identifier = ModId.of(id);
        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, new Item.Settings()));
        Registry.register(
                BuiltinRegistries.CONFIGURED_FEATURE,
                identifier,
                configuredFeature);
        Registry.register(
                BuiltinRegistries.PLACED_FEATURE,
                identifier,
                placedFeature);
        BiomeModifications.addFeature(
                whereToBeFound,
                GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(
                        Registry.PLACED_FEATURE_KEY,
                        identifier));
    }

    private static ConfiguredFeature<?, ?> configuredFeatures(Block replacedBlock, Block ore, int veinSize, BlockMatchRuleTest blockMatchRuleTest){
        ConfiguredFeature<?, ?> configuredFeature;
        if       (replacedBlock == Blocks.STONE){
            configuredFeature = new ConfiguredFeature<>(
                    Feature.ORE,
                    new OreFeatureConfig(
                        OreConfiguredFeatures.STONE_ORE_REPLACEABLES, //替换掉的方块，石头，深板岩等
                        ore.getDefaultState(),
                        veinSize)); // 矿脉大小
        }else if (replacedBlock == Blocks.DEEPSLATE){
            configuredFeature = new ConfiguredFeature<>(
                    Feature.ORE,
                    new OreFeatureConfig(
                        OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                            ore.getDefaultState(),
                        veinSize));
        }else if (replacedBlock == Blocks.NETHERRACK){
            configuredFeature = new ConfiguredFeature<>(
                    Feature.ORE,
                    new OreFeatureConfig(
                        OreConfiguredFeatures.NETHERRACK,
                        ore.getDefaultState(),
                        veinSize));
        }else   {
            configuredFeature = new ConfiguredFeature<>(
                    Feature.ORE,
                    new OreFeatureConfig(
                        blockMatchRuleTest,
                        ore.getDefaultState(),
                        veinSize));
        }
        return configuredFeature;
    }
}
