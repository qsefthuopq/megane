package badasintended.megane.runtime;

import badasintended.megane.runtime.component.*;
import badasintended.megane.runtime.data.*;
import badasintended.megane.runtime.renderer.*;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import net.minecraft.block.*;
import net.minecraft.util.Identifier;

import static badasintended.megane.util.MeganeUtils.hasMod;
import static badasintended.megane.util.MeganeUtils.id;
import static mcp.mobius.waila.api.TooltipPosition.BODY;

public class MeganeWaila implements IWailaPlugin {

    public static final Identifier INVENTORY = id("inventory");
    public static final Identifier BAR = id("bar");
    public static final Identifier PROGRESS = id("progress");

    private static final Class<Block> BLOCK = Block.class;

    @Override
    public void register(IRegistrar r) {
        // Renderer
        r.registerTooltipRenderer(INVENTORY, InventoryRenderer.INSTANCE);
        r.registerTooltipRenderer(BAR, BarRenderer.INSTANCE);
        r.registerTooltipRenderer(PROGRESS, ProgressRenderer.INSTANCE);

        // Component
        r.registerComponentProvider(BarResetComponent.INSTANCE, BODY, BLOCK);
        r.registerComponentProvider(EnergyComponent.INSTANCE, BODY, BLOCK);
        r.registerComponentProvider(FluidComponent.INSTANCE, BODY, BLOCK);
        r.registerComponentProvider(InventoryComponent.INSTANCE, BODY, BLOCK);
        r.registerComponentProvider(ProgressComponent.INSTANCE, BODY, BLOCK);

        r.registerComponentProvider(CauldronComponent.INSTANCE, BODY, CauldronBlock.class);
        r.registerComponentProvider(ComposterComponent.INSTANCE, BODY, ComposterBlock.class);

        // Server Data
        r.registerBlockDataProvider(InventoryData.INSTANCE, BLOCK);

        r.registerBlockDataProvider(EnergyData.INSTANCE, BLOCK);
        if (hasMod("team_reborn_energy")) r.registerBlockDataProvider(TrEnergyData.INSTANCE, BLOCK);

        r.registerBlockDataProvider(FluidData.INSTANCE, BLOCK);
        if (hasMod("libblockattributes_fluids")) r.registerBlockDataProvider(LbaFluidData.INSTANCE, BLOCK);

        r.registerBlockDataProvider(ProgressData.INSTANCE, BLOCK);
    }

}