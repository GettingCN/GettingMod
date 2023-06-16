package cn.cngetting.gettingmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DisguiseBlock extends Block {
    public DisguiseBlock(Settings settings) {
        super(settings);
        //setDefaultState(getDefaultState().with(CHARGED, false));
    }

    //public static final BooleanProperty CHARGED = BooleanProperty.of("charged");

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            //友好问候
            player.sendMessage(Text.of("傻逼!"), false);
            player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1);
            //向玩家发起雷击
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
            assert lightningEntity != null;
            lightningEntity.refreshPositionAfterTeleport(player.getPos());
            world.spawnEntity(lightningEntity);
        }

        return ActionResult.SUCCESS;
    }

    /*@Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 1.0f, 0.5f);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return super.getCollisionShape(state, world, pos, context);
    }*/

    /*@Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (world.getBlockState(pos).get(CHARGED)){
            // 在方块的位置召唤闪电
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
            assert lightningEntity != null;
            lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
            world.spawnEntity(lightningEntity);
        }
        world.setBlockState(pos, state.with(CHARGED, false));
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGED);
    }*/


}
