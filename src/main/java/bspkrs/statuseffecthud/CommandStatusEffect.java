package bspkrs.statuseffecthud;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import bspkrs.statuseffecthud.fml.gui.GuiSEHConfig;
import bspkrs.fml.util.DelayedGuiDisplayTicker;

public class CommandStatusEffect extends CommandBase
{
    @Override
    public String getName()
    {
        return "statuseffect";
    }

    @Override
    public String getUsage(ICommandSender var1)
    {
        return "commands.statuseffect.usage";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 1;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        try
        {
            new DelayedGuiDisplayTicker(10, new GuiSEHConfig(null));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(ICommand p_compareTo_1_)
    {
        if (p_compareTo_1_ instanceof CommandBase)
            return this.getName().compareTo(((CommandBase)p_compareTo_1_).getName());

        return 0;
    }
}
