package badasintended.megane.impl.mixin.appeng;

import appeng.integration.modules.waila.BaseWailaDataProvider;
import appeng.integration.modules.waila.TileWailaDataProvider;
import appeng.integration.modules.waila.tile.PowerStorageWailaDataProvider;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(TileWailaDataProvider.class)
public class MixinTileWailaDataProvider {

    @Final
    @Shadow
    private List<BaseWailaDataProvider> providers;

    /**
     * Remove AE2's own energy tooltip, since megane provides it
     */
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void removeEnergyTooltip(CallbackInfo ci) {
        providers.removeIf(it -> it instanceof PowerStorageWailaDataProvider);
    }

}
