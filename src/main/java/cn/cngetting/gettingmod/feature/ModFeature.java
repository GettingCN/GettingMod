package cn.cngetting.gettingmod.feature;

import cn.cngetting.gettingmod.feature.record.ExampleFeatureConfig;
import cn.cngetting.gettingmod.feature.tree.RichFoliagePlacer;
import cn.cngetting.gettingmod.feature.tree.RichTreeDecorator;
import cn.cngetting.gettingmod.feature.tree.RichTrunkPlacer;
import cn.cngetting.gettingmod.mixin.block.FoliagePlacerTypeInvoker;
import cn.cngetting.gettingmod.mixin.block.TreeDecoratorTypeInvoker;
import cn.cngetting.gettingmod.mixin.block.TrunkPlacerTypeInvoker;
import cn.cngetting.gettingmod.util.ModId;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.Collections;
import java.util.List;

public class ModFeature {
    public static final Identifier EXAMPLE_FEATURE_ID = new Identifier( "example_feature");
    public static Feature<ExampleFeatureConfig> EXAMPLE_FEATURE = new ExampleFeature(ExampleFeatureConfig.CODEC);
    public static ConfiguredFeature<ExampleFeatureConfig, ExampleFeature> EXAMPLE_FEATURE_CONFIGURED = new ConfiguredFeature<>(
            (ExampleFeature) EXAMPLE_FEATURE,
            new ExampleFeatureConfig(
                    7,
                    new Identifier("minecraft", "netherite_block"))
    );



    // our PlacedFeature. this is what gets passed to the biome modification API to add to the biome.
    // 我们的放置功能。这是传递给生物群系修改 API 以添加到生物群系的内容。
    // the SquarePlacementModifier makes the feature generate a cluster of pillars each time
    // 方形放置修饰符使要素每次生成一簇柱子
    public static PlacedFeature EXAMPLE_FEATURE_PLACED = new PlacedFeature(
            RegistryEntry.of(EXAMPLE_FEATURE_CONFIGURED),
            List.of(
                    HeightRangePlacementModifier.uniform(
                            YOffset.aboveBottom(70),
                            YOffset.belowTop(64)),
                    SquarePlacementModifier.of())
    );

    public static final TrunkPlacerType<RichTrunkPlacer> RICH_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister(ModId.set("rich_trunk_placer"), RichTrunkPlacer.CODEC);
    public static final FoliagePlacerType<RichFoliagePlacer> RICH_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister(ModId.set("rich_foliage_placer"), RichFoliagePlacer.CODEC);
    public static final TreeDecoratorType<RichTreeDecorator> RICH_TREE_DECORATOR = TreeDecoratorTypeInvoker.callRegister(ModId.set("rich_tree_decorator"), RichTreeDecorator.CODEC);
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> TREE_RICH
            = ConfiguredFeatures.register(ModId.set("tree_rich"),
            Feature.TREE,
            // 使用构建器配置特征 Configure the feature using the builder
            // 树干方块提供程序 Trunk block provider
            // 放置一个笔直的树干 places a straight trunk
            // 树叶块提供程序 Foliage block provider
            // 将叶子作为斑点放置（半径、树干偏移、高度） places leaves as a blob (radius, offset from trunk, height)
            new TreeFeatureConfig.Builder(
          BlockStateProvider.of(Blocks.NETHERITE_BLOCK),
                    new RichTrunkPlacer(3, 3, 0),
                BlockStateProvider.of(Blocks.DIAMOND_BLOCK),
                new RichFoliagePlacer(
                        ConstantIntProvider.create(2),
                        ConstantIntProvider.create(0),
                        ConstantIntProvider.create(1)),
                    new TwoLayersFeatureSize(5, 0, 1)
          ).decorators(Collections.singletonList(RichTreeDecorator.INSTANCE)).build());
    //不同层处树的宽度;用于查看树在不剪成块的情况下可以有多高
    // The width of the tree at different layers; used to see how tall the tree can be without clipping into blocks



    public static void register(){
        // register the features
        Registry.register(Registry.FEATURE, EXAMPLE_FEATURE_ID, EXAMPLE_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, EXAMPLE_FEATURE_ID, EXAMPLE_FEATURE_CONFIGURED);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, EXAMPLE_FEATURE_ID, EXAMPLE_FEATURE_PLACED);

        // add it to overworld biomes using FAPI
        // 使用 FAPI 将其添加到主世界生物群系中
        // the feature is to be added while flowers and trees are being generated
        // 该功能将在生成花草树木时添加
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, EXAMPLE_FEATURE_ID));
    }
}
