package cn.cngetting.gettingmod.feature;

import cn.cngetting.gettingmod.feature.record.ExampleFeatureConfig;
import com.mojang.serialization.Codec;
import cn.cngetting.gettingmod.block.ModOre;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.util.registry.Registry;

public class ExampleFeature extends Feature<ExampleFeatureConfig> {
    public ExampleFeature(Codec<ExampleFeatureConfig> configCodec) {
        super(configCodec);
    }

    // this method is what is called when the game tries to generate the feature.
    // 此方法是游戏尝试生成特征时所调用的方法。
    @Override
    public boolean generate(FeatureContext<ExampleFeatureConfig> context) {
        // it is where the actual blocks get placed into the world.
        // 这是实际块被放置在世界中的地方。
        StructureWorldAccess world = context.getWorld();
        // the origin is the place where the game starts trying to place the feature
        // 原点是游戏开始尝试放置特征的位置
        BlockPos origin = context.getOrigin();
        // we won't use the random here, but we could if we wanted to
        // 我们不会在这里使用随机，但如果我们愿意，我们可以
        Random random = context.getRandom();
        ExampleFeatureConfig config = context.getConfig();
        // don't worry about where these come from-- we'll implement these methods soon
        // 不用担心这些方法来自哪里 - 我们将很快实现这些方法
        int number = config.number();
        Identifier blockID = config.blockID();
        BlockState blockState = Registry.BLOCK.get(blockID).getDefaultState();
        // ensure the ID is okay
        // 确保 ID 正常
        if (blockState == null) throw new IllegalStateException(blockID + " could not be parsed to a valid block identifier!");
        // find the surface of the world
        // 寻找世界的表面
        BlockPos testPos = new BlockPos(origin);
        for (int y = 0; y < world.getHeight(); y++) {
            testPos = testPos.up();
            // the tag name is dirt, but includes grass, mud, podzol, etc.
            // 标签名称是泥土，但包括草、泥、豆佐尔等。
            if (world.getBlockState(testPos).isIn(BlockTags.DIRT)) {
                if (world.getBlockState(testPos.up()).isOf(Blocks.AIR)) {
                    // create a simple pillar of blocks
                    // 创建简单的块柱
                    testPos = testPos.up();
                    world.setBlockState(testPos, ModOre.TIN_BLOCK.getDefaultState(), 0x10);
                    for (int i = 0; i < number; i++) {
                        testPos = testPos.up();
                        world.setBlockState(testPos, blockState, 0x10);
                        // ensure we don't try to place blocks outside the world
                        // 确保我们不会试图在世界之外放置块
                        if (testPos.getY() >= world.getTopY()) break;
                    }
                    return true;
                }
            }
        }
        // the game couldn't find a place to put the pillar
        // 游戏找不到放置柱子的地方
        return false;
    }
}