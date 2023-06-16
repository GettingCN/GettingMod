package cn.cngetting.gettingmod.feature.tree;

import cn.cngetting.gettingmod.feature.ModFeature;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class RichTreeDecorator extends TreeDecorator {
    public static final RichTreeDecorator INSTANCE = new RichTreeDecorator();
    // Our constructor doesn't have any arguments, so we create a unit codec that returns the singleton instance
    public static final Codec<RichTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);


    private RichTreeDecorator() {}

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModFeature.RICH_TREE_DECORATOR;
    }

    @Override
    public void generate(TreeDecorator.Generator generator) {
        // Iterate through block positions
        // 遍历区块位置
        generator.getLogPositions().forEach(pos -> {
            Random random = generator.getRandom();
            // Pick a value from 0 (inclusive) to 4 (exclusive) and if it's 0, continue
            // This is the chance for spawning the gold block
            // 从 0（含）到 4（不含）之间选择一个值，如果为 0，则继续 这是生成黄金块的机会
            if (random.nextInt(4) == 0) {
                // Pick a random value from 0 to 4 and determine the side where the gold block will be placed using it
                // 0 到 4 中选择一个随机值，并使用它确定放置金块的一侧
                int sideRaw = random.nextInt(4);
                Direction side = switch (sideRaw) {
                    case 0 -> Direction.NORTH;
                    case 1 -> Direction.SOUTH;
                    case 2 -> Direction.EAST;
                    case 3 -> Direction.WEST;
                    default -> throw new ArithmeticException("The picked side value doesn't fit in the 0 to 4 bounds");
                };
                // Offset the log position by the resulting side
                // 将原木位置偏移结果侧
                /*logPosition.offset(side, 1);*/
                BlockPos targetPosition = generator.getLogPositions().get(0).up().offset(side,1);/*
                BlockPos targetPosition = generator.getLeavesPositions().get(0).down().offset(side,1);*/

                // Place the gold block using the replacer BiConsumer
                // This is the standard way of placing blocks in TrunkPlacers, FoliagePlacers and TreeDecorators
                // 使用替代物放置金块 BiConsumer
                // 这是在树干放置器、树叶放置器和树装饰器中放置块的标准方法
                /*replacer.accept(targetPosition, Blocks.GOLD_BLOCK.getDefaultState());*/
                generator.replace(targetPosition, Blocks.GOLD_BLOCK.getDefaultState());
            }
        });
    }


}
