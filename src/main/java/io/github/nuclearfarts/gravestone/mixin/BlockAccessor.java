package io.github.nuclearfarts.gravestone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.AbstractBlock;

@Mixin(AbstractBlock.class)
public interface BlockAccessor {
	
	@Accessor
	boolean getCollidable();
}
