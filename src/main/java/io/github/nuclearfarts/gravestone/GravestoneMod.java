package io.github.nuclearfarts.gravestone;

import com.google.common.collect.ImmutableSet;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GravestoneMod implements ModInitializer {
	
	public static final String MODID = "yet_another_gravestone_mod";
	
	public static final Block GRAVESTONE = new GravestoneBlock(Block.Settings.of(Material.PISTON).strength(0.5f, 1200));
	
	public static final BlockEntityType<GravestoneBlockEntity> GRAVESTONE_BLOCK_ENTITY = new BlockEntityType<GravestoneBlockEntity>(GravestoneBlockEntity::new, ImmutableSet.of(GRAVESTONE), null);
	
	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, id("gravestone"), GRAVESTONE);
		
		Registry.register(Registry.BLOCK_ENTITY, id("gravestone"), GRAVESTONE_BLOCK_ENTITY);
	}
	
	public static Identifier id(String id) {
		return new Identifier(MODID, id);
	}
}
