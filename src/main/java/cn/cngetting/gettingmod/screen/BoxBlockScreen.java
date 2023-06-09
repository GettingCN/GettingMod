package cn.cngetting.gettingmod.screen;

import cn.cngetting.gettingmod.screen.handler.BoxBlockScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BoxBlockScreen extends HandledScreen<ScreenHandler> {
    // GUI 纹理的路径，本例中使用发射器中的纹理
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");
    BoxBlockScreenHandler screenHandler;

    public BoxBlockScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        screenHandler = (BoxBlockScreenHandler) handler;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // 我们只是在容器的某个地方渲染我们同步的数字，这毕竟是一个演示
        // 最后一个参数是一个颜色代码，使字体亮绿色
        textRenderer.draw(matrices, Integer.toString(screenHandler.getSyncedNumber()), 0, 0, 65280);
        // 格式
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);


    }

    @Override
    protected void init() {
        super.init();
        // 将标题居中
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}