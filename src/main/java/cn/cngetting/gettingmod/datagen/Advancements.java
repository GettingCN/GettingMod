package cn.cngetting.gettingmod.datagen;

import cn.cngetting.gettingmod.Main;
import cn.cngetting.gettingmod.sound.Music;
import cn.cngetting.gettingmod.sound.Sounds;
import cn.cngetting.gettingmod.block.ModBlocks;
import cn.cngetting.gettingmod.util.ModId;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class Advancements implements Consumer<Consumer<Advancement>> {
    Identifier adventure = new Identifier("textures/gui/advancements/backgrounds/adventure.png");

    @Override
    public void accept(Consumer<Advancement> consumer) {

        Advancement modStartAdvancement = Advancement.Builder.create()
                .display(
                        ModBlocks.DECO_BLOCK,   // 显示的图标
                        Text.literal("开始吧，无尽群星"),                // 标题
                        Text.literal("这里是歌庭学习测试模组的进度开始"),   // 描述
                        adventure,                   // 使用的背景图片
                        AdvancementFrame.TASK,       // 背景轮廓  选项: TASK, CHALLENGE, GOAL
                        false,                       // 在右上角显示
                        false,                       // 在聊天框中提示
                        false                        // 在进度页面里隐藏
                ).criterion("mod_start", InventoryChangedCriterion.Conditions.items(Items.WOODEN_PICKAXE))
                .build(consumer, ModId.ID + "/mod_start");

        Advancement brightPieceAdvancement = Advancement.Builder.create()
                .display(
                        Music.DISC_BRIGHT_PIECE,
                        Text.literal("医者仁心"),
                        Text.literal("你那留于面颊上的泪水，是希望我来为你擦拭吗"),
                        adventure,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                // Criterion 中使用的第一个字符串是其他进度在需要 'requirements' 时引用的名字
                .criterion("bright_piece", InventoryChangedCriterion.Conditions.items(Music.DISC_BRIGHT_PIECE))
                .parent(modStartAdvancement)
                .build(consumer, ModId.ID + "/bright_piece");

        Advancement iGotSmokeAdvancement = Advancement.Builder.create()
                .display(
                        Music.DISC_I_GOT_SMOKE, // 显示的图标
                        Text.literal("理塘传奇"), // 标题
                        Text.literal("或许我真的抽烟抽高了，就像你的人生糊弄一下就完了"), // 描述
                        adventure, // 使用的背景图片
                        AdvancementFrame.TASK, // 选项: TASK, CHALLENGE, GOAL
                        true, // 在右上角显示
                        true, // 在聊天框中提示
                        false // 在进度页面里隐藏
                )
                .criterion("i_got_smoke", InventoryChangedCriterion.Conditions.items(Music.DISC_I_GOT_SMOKE))
                .parent(modStartAdvancement)
                .build(consumer, ModId.ID + "/i_got_smoke");
    }
}