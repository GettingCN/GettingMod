package cn.cngetting.gettingmod.block.entities;

import cn.cngetting.gettingmod.block.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;

import java.util.Objects;

public class ContainerEntity extends BlockEntity {
    public ContainerEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.CONTAINER_ENTITY, pos, state);
    }
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1 ,ItemStack.EMPTY);

    public void use(PlayerEntity player){
        if(inventory.get(0).isEmpty()){
            inventory.set(0, player.getMainHandStack().split(1));
        }else{
            ItemScatterer.spawn(Objects.requireNonNull(this.getWorld()), this.getPos().getX(),this.getPos().getY(), this.getPos().getZ(), inventory.get(0));
            inventory.set(0, ItemStack.EMPTY);
        }
        markDirty();
    }

    @Deprecated
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Deprecated
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
    }

    @Environment(EnvType.CLIENT)
    public static class ContainerBlockEntityRenderer implements BlockEntityRenderer<ContainerEntity> {
        // 唱片机物品堆
        private static ItemStack stack = new ItemStack(Items.JUKEBOX, 1);

        public ContainerBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

        @Override
        public void render(ContainerEntity blockEntity, float deltaTick, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
            matrices.push();
            // 计算当前y值的偏移
            double offset = Math.sin((blockEntity.getWorld().getTime() + deltaTick) / 8.0) / 4.0;
            // 移动物品
            matrices.translate(0.5, 1.25 + offset, 0.5);

            // 旋转物品
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + deltaTick) * 4));
            int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);

            // GL 调用之后的必要调用
            matrices.pop();
        }
    }
}
