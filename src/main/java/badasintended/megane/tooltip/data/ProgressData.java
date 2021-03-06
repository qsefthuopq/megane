package badasintended.megane.tooltip.data;

import badasintended.megane.api.registry.ProgressTooltipRegistry;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static badasintended.megane.api.registry.ProgressTooltipRegistry.get;
import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class ProgressData implements IServerDataProvider<BlockEntity> {

    public static final ProgressData INSTANCE = new ProgressData();

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (!config().progress.isEnabled() || config().progress.getBlacklist().contains(Registry.BLOCK.getId(blockEntity.getCachedState().getBlock()))) {
            return;
        }

        ProgressTooltipRegistry.Provider provider = get(blockEntity);
        if (provider != null) {
            data.putBoolean(key("hasProgress"), true);

            int[] inputs = provider.getInputSlots(blockEntity);
            int[] outputs = provider.getOutputSlots(blockEntity);
            data.putInt(key("inputCount"), inputs.length);
            data.putInt(key("outputCount"), outputs.length);

            for (int i = 0; i < inputs.length; i++) {
                data.put(key("input" + i), provider.getStack(blockEntity, inputs[i]).toTag(new CompoundTag()));
            }

            data.putInt(key("percentage"), provider.getPercentage(blockEntity));

            for (int i = 0; i < outputs.length; i++) {
                data.put(key("output" + i), provider.getStack(blockEntity, outputs[i]).toTag(new CompoundTag()));
            }
        }
    }

}
