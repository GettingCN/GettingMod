package cn.cngetting.gettingmod.feature.tree;

import cn.cngetting.gettingmod.feature.ModFeature;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.BiConsumer;

public class RichTrunkPlacer extends TrunkPlacer {
    // Use the fillTrunkPlacerFields to create our codec
    // 使用 fillTrunkPlacerFields 创建我们的编解码器
    public static final Codec<RichTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            fillTrunkPlacerFields(instance).apply(instance, RichTrunkPlacer::new));

    public RichTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModFeature.RICH_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        // Set the ground beneath the trunk to dirt
        setToDirt(world, replacer, random, startPos.down(), config);
        setToDirt(world, replacer, random, startPos.down().east().north(), config);

        // Iterate until the trunk height limit and place two blocks using the getAndSetState method from TrunkPlacer
        // 迭代直到主干高度限制，并使用 TrunkPlacer 中的 getAndSetState 方法放置两个块
        for (int i = 0; i < height; i++) {
            this.getAndSetState(world, replacer, random, startPos.up(i), config);
            //this.getAndSetState(world, replacer, random, startPos.up(i).east(5).north(5), config);
        }

        // We create two TreeNodes - one for the first trunk, and the other for the second
        // Put the highest block in the trunk as the center position for the FoliagePlacer to use
        // 我们创建了两个树节点 - 一个用于第一个主干，另一个用于第二个树干
        // 将树干中最高的块作为 FoliagePlacer 的中心位置供 FoliagePlacer 使用
        return ImmutableList.of(
                new FoliagePlacer.TreeNode(
                startPos.up(height),
                        0,
                        false)/*,
                new FoliagePlacer.TreeNode(
                        startPos.east(5).north(5).up(height),
                        0,
                        false)*/
        );
    }
}