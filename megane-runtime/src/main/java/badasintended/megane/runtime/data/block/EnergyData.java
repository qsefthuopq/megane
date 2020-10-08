package badasintended.megane.runtime.data.block;

import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.api.registry.TooltipRegistry;
import badasintended.megane.runtime.data.Appender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;

import static badasintended.megane.util.MeganeUtils.*;

public class EnergyData extends BlockData {

    public EnergyData() {
        super(() -> config().energy);
        appenders.add(new Registered());
        if (hasMod("team_reborn_energy")) {
            LOGGER.info("[megane] Found Team Reborn's Energy, loading compatibility");
            appenders.add(new TeamReborn());
        }
    }

    public static class Registered implements Appender<BlockEntity> {

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            EnergyProvider energyProvider = TooltipRegistry.ENERGY.get(blockEntity);
            if (energyProvider == null || !energyProvider.hasEnergy(blockEntity)) return false;
            data.putBoolean(key("hasEnergy"), true);
            data.putDouble(key("storedEnergy"), energyProvider.getStored(blockEntity));
            data.putDouble(key("maxEnergy"), energyProvider.getMax(blockEntity));
            return true;
        }

    }

    public static class TeamReborn implements Appender<BlockEntity> {

        @Override
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            if (Energy.valid(blockEntity)) {
                EnergyHandler energy = Energy.of(blockEntity);
                data.putBoolean(key("hasEnergy"), true);
                data.putDouble(key("storedEnergy"), energy.getEnergy());
                data.putDouble(key("maxEnergy"), energy.getMaxStored());
                return true;
            }
            return false;
        }

    }

}