package cn.cngetting.gettingmod.block.sapling;

import cn.cngetting.gettingmod.feature.ModFeature;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

public class RichSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> getTreeFeature(Random random, boolean bees) {
        return ModFeature.TREE_RICH;
    }
}