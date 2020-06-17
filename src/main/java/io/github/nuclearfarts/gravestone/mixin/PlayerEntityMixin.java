package io.github.nuclearfarts.gravestone.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.nuclearfarts.gravestone.util.GravestoneUtil;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerEntityMixin extends PlayerEntity {

	private PlayerEntityMixin() {
		super(null, null, null);
		throw new RuntimeException("wtf");
	}

	@Inject(method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V", at = @At(value = "INVOKE", target = "net/minecraft/server/network/ServerPlayerEntity.drop(Lnet/minecraft/entity/damage/DamageSource;)V"))
	private void deathCallback(CallbackInfo info) {
		GravestoneUtil.placeGrave((PlayerEntity)(LivingEntity)this);
	}
}
