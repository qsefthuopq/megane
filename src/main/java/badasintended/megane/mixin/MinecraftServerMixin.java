package badasintended.megane.mixin;

import badasintended.megane.util.MeganeUtils;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "runServer", at = @At("HEAD"))
    private void loadConfig(CallbackInfo ci) {
        MeganeUtils.reloadConfig();
    }

    @Inject(method = "reloadResources", at = @At("HEAD"))
    private void reloadConfig(Collection<String> collection, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        MeganeUtils.reloadConfig();
    }

}
