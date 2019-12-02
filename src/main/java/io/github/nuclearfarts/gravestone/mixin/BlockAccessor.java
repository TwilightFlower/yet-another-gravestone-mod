package io.github.nuclearfarts.gravestone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;

@Mixin(Block.class)
public interface BlockAccessor {
	
	@Accessor
	boolean getCollidable();
}
