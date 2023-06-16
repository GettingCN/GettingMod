package cn.cngetting.gettingmod.block.entities;

import cn.cngetting.gettingmod.block.ModBlocks;
import cn.cngetting.gettingmod.screen.handler.BoxBlockScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.*;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BoxBlockEntity extends LootableContainerBlockEntity implements NamedScreenHandlerFactory {
    public BoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BOX_ENTITY, pos, state);
    }

    // 这是我们希望同步的整型，每刻会增加 1。
    private static int syncedInt;

    // PropertyDelegate 是一个接口，我们将在这里使用内联实现。
    // 它通常可以包含多个整型作为索引标志的数据，但是在本例中只有一个整型
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return syncedInt;
        }

        @Override
        public void set(int index, int value) {
            syncedInt = value;
        }

        // 这里应该返回你的 delegate 中整型的数量，在本例中只有一个
        @Override
        public int size() {
            return 1;
        }
    };

    //这些方法来自 NamedScreenHandlerFactory 接口

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        // 当我们的类执行完物品栏时，我们将此提供给 screenHandler
        // 只有服务器在开始时拥有物品栏，这将会在 ScreenHandler 中同步到客户端

        // 类似于物品栏：服务器获得 PropertyDelegate 并将其直接给 screen handler 的服务器实例
        return new BoxBlockScreenHandler(syncId, playerInventory, this,propertyDelegate);
    }

    @Override
    public Text getDisplayName() {
        // 对于 1.18.2 或更低版本，请使用 return new TranslatableText(getCachedState().getBlock().getTranslationKey());
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }


    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9,ItemStack.EMPTY);

    @Override
    public DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    public void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    public Text getContainerName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new BoxBlockScreenHandler(syncId, playerInventory, this, propertyDelegate);
                //new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, this, 1);
    }

    @Override
    public int size() {
        return 9;
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

    /*@Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }*/
}
