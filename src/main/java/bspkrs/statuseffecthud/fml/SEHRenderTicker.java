package bspkrs.statuseffecthud.fml;

import java.util.List;

import bspkrs.statuseffecthud.StatusEffectHUD;
import bspkrs.util.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SEHRenderTicker
{
    private Minecraft mcClient;
    private static boolean isRegistered = false;

    public SEHRenderTicker()
    {
        mcClient = FMLClientHandler.instance().getClient();
        isRegistered = true;
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public void onTick(RenderTickEvent event)
    {
        if (event.phase.equals(Phase.START))
        {
            if (StatusEffectHUD.disableInventoryEffectList)
                if (mcClient.currentScreen != null && mcClient.currentScreen instanceof InventoryEffectRenderer)
                {
                    try
                    {
                        InventoryEffectRenderer ier = ((InventoryEffectRenderer)mcClient.currentScreen);
                        if (ReflectionHelper.getBooleanValue(InventoryEffectRenderer.class, "field_147045_u", "hasActivePotionEffects", ier, false))
                        {
                            ReflectionHelper.setBooleanValue(InventoryEffectRenderer.class, "field_147045_u", "hasActivePotionEffects", ier, false);
                            ReflectionHelper.setIntValue(GuiContainer.class, "field_147003_i", "guiLeft", ier,
                                    (ier.width - ReflectionHelper.getIntValue(GuiContainer.class, "field_146999_f", "xSize", ier, 176)) / 2);
                        }

                        List buttonList = ReflectionHelper.getListObject(GuiScreen.class, "field_146292_n", "buttonList", ier);
                        for (Object o : buttonList)
                            if (o instanceof GuiButton && ((GuiButton)o).id == 101)
                                ((GuiButton)o).x = ReflectionHelper.getIntValue(GuiContainer.class, "field_147003_i", "guiLeft", ier,
                                        (ier.width - ReflectionHelper.getIntValue(GuiContainer.class, "field_146999_f", "xSize", ier, 176)) / 2);
                            else if (o instanceof GuiButton && ((GuiButton)o).id == 102)
                                ((GuiButton)o).x = ReflectionHelper.getIntValue(GuiContainer.class, "field_147003_i", "guiLeft", ier,
                                        (ier.width - ReflectionHelper.getIntValue(GuiContainer.class, "field_146999_f", "xSize", ier, 176)) / 2) +
                                        ReflectionHelper.getIntValue(GuiContainer.class, "field_146999_f", "xSize", ier, 176) - 20;
                    }
                    catch (Throwable e)
                    {}
                }

            return;
        }

        if (!StatusEffectHUD.onTickInGame(mcClient))
        {
            FMLCommonHandler.instance().bus().unregister(this);
            isRegistered = false;
        }
    }

    public static boolean isRegistered()
    {
        return isRegistered;
    }
}
