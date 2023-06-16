package cn.cngetting.gettingmod.datagen;

import cn.cngetting.gettingmod.Main;
import cn.cngetting.gettingmod.util.ModId;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;

public class ModDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(TagGenerator::new);
        generator.addProvider(AdvancementsProvider::new);
        generator.addProvider(BlockLootTables::new);
    }

    private static class TagGenerator extends FabricTagProvider<Item> {
        // 创建名为“smelly_items”的物品标签。
        private static final TagKey<Item> SMELLY_ITEMS = TagKey.of(Registry.ITEM_KEY, ModId.of("smelly_items"));

        public TagGenerator(FabricDataGenerator dataGenerator) {
            super(dataGenerator, Registry.ITEM);
        }

        @Override
        protected void generateTags() {
            // 创建一个 tag builder，我们添加粘液球、腐肉以及物品标签 minecraft:dirt 中的所有内容。
            getOrCreateTagBuilder(SMELLY_ITEMS)
                    .add(Items.SLIME_BALL)
                    .add(Items.ROTTEN_FLESH)
                    .addOptionalTag(ItemTags.DIRT);
            // 在“generated”文件夹中，会自动生成“assets/tutorial/tags/items/smelly_items.json”。

        }
    }

    private static class BlockLootTables extends SimpleFabricLootTableProvider {
        public BlockLootTables(FabricDataGenerator dataGenerator) {
            super(dataGenerator, LootContextTypes.BLOCK);
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        //  BlockLootTableGenerator类包含大量的实用工具方法。只需花一些时间并浏览可用于覆盖的方法。
            biConsumer.accept(ModId.of("chests/box_block_loot_1"), LootTable.builder()
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                            .with(ItemEntry.builder(Items.DIAMOND)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)))
                            )
                            .with(ItemEntry.builder(Items.DIAMOND_SWORD)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)))
                            )
                    ));
        }
    }

}
