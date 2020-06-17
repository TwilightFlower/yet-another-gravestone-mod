package io.github.nuclearfarts.gravestone.compat;

import java.util.function.Consumer;

import io.github.nuclearfarts.gravestone.util.GravestoneUtil;
// import ladysnake.requiem.api.v1.possession.Possessable;
import net.minecraft.entity.LivingEntity;

public class RequiemCompat implements Consumer<LivingEntity> {
	
	@Override
	public void accept(LivingEntity entity) {
		// if(entity instanceof Possessable && ((Possessable)entity).isBeingPossessed()) {
		// 	GravestoneUtil.placeGrave(((Possessable)entity).getPossessor());
		// }
	}

}
