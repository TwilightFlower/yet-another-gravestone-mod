package io.github.nuclearfarts.gravestone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.nuclearfarts.gravestone.GravestoneMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	private LivingEntityMixin() {
		super(null, null);
		throw new RuntimeException("wtf");
	}
	
	@Inject(method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V", at = @At("HEAD"))
	private void deathCallback(CallbackInfo info) {
		GravestoneMod.getRequiemCompat().accept((LivingEntity)(Entity)this);
	}
}
