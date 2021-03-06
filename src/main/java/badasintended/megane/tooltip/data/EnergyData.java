package badasintended.megane.tooltip.data;

import badasintended.megane.api.registry.EnergyTooltipRegistry;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.EnergyTooltipRegistry.get;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class EnergyData implements IServerDataProvider<BlockEntity> {

    public static final EnergyData INSTANCE = new EnergyData();

    @SuppressWarnings({"rawtypes", "unchecked"})
    void appendInternal(CompoundTag data, BlockEntity blockEntity) {
        EnergyTooltipRegistry.Provider provider = get(blockEntity);
        if (provider != null) {
            data.putBoolean(key("hasEnergy"), true);
            data.putDouble(key("storedEnergy"), provider.getStored(blockEntity));
            data.putDouble(key("maxEnergy"), provider.getMax(blockEntity));
        }
    }

    @Override
    public final void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!config().energy.isEnabled() || config().energy.getBlacklist().contains(Registry.BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }
        appendInternal(data, blockEntity);
    }

}
