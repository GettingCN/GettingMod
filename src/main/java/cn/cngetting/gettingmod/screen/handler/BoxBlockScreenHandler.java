package cn.cngetting.gettingmod.screen.handler;

import cn.cngetting.gettingmod.block.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BoxBlockScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    PropertyDelegate propertyDelegate;

    // 服务器想要客户端开启 screenHandler 时，客户端调用这个构造器。
    // 如有空的物品栏，客户端会调用其他构造器，screenHandler 将会自动
    // 在客户端将空白物品栏同步给物品栏。
    public BoxBlockScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9),new ArrayPropertyDelegate(1));
    }

    // 这个构造器是在服务器的 BlockEntity 中被调用的，无需线调用其他构造器，服务器知道容器的物品栏
    // 并直接将其作为参数传入。然后物品栏在客户端完成同步。
    public BoxBlockScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModBlocks.BOX_BLOCK_SCREEN_HANDLER, syncId);
        checkSize(inventory, 9);
        this.inventory = inventory;
        // 玩家开启时，一些物品栏有自定义的逻辑。
        inventory.onOpen(playerInventory.player);
        // 我们需要告诉 screenHandler 关于 propertyDelegate 里的数据，否则它不会同步这里面的数据
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);

        // 这会将槽位放置在 3×3 网格的正确位置中。这些槽位在客户端和服务器中都存在！
        // 但是这不会渲染槽位的背景，这是 Screens job
        int m;
        int l;
        //Our inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 3; ++l) {
                this.addSlot(new Slot(inventory, l + m * 3, 62 + l * 18, 17 + m * 18){
                    @Override
                    public boolean canInsert(ItemStack itemStack){
                        return true;
                    }
                });
            }
        }
        {// 玩家物品栏
            for (m = 0; m < 3; ++m) {
                for (l = 0; l < 9; ++l) {
                    this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18){});
                }
            }
         // 玩家快捷栏
            for (m = 0; m < 9; ++m) {
                this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // 我们为已同步的整型提供了 getter， 所以 Screen 可以访问它并且在屏幕上显示它。
    public int getSyncedNumber(){
        return propertyDelegate.get(0);
    }

    // Shift + 鼠标左键
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                //从 方块库存 到 玩家物品栏
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                //从 玩家物品栏 到 方块库存
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }
}