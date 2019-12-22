package io.github.nuclearfarts.gravestone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GravestoneBlock extends Block implements BlockEntityProvider {

	private static final VoxelShape SHAPE = createCuboidShape(3, 0, 3, 13, 12, 5);
	
	public GravestoneBlock(Block.Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView view) {
		return new GravestoneBlockEntity();
	}
	
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity be, ItemStack stack) {
		for(ItemStack s : ((GravestoneBlockEntity)be).inventory) {
			Block.dropStack(world, pos, s);
		}
		player.addExperience(((GravestoneBlockEntity)be).xp);
	}
	
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ctx) {
		return SHAPE;
	}
}
