package io.github.nuclearfarts.gravestone;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class GravestoneBlockEntity extends BlockEntity {

	public List<ItemStack> inventory = new ArrayList<>();
	public int xp = 0;
	
	public GravestoneBlockEntity() {
		super(GravestoneMod.GRAVESTONE_BLOCK_ENTITY);
	}
	
	public void fromTag(CompoundTag tag) {
		super.fromTag(tag);
		inventory.clear();
		ListTag stacks = (ListTag)tag.get("Items");
		if(stacks != null) {
			for(Tag t : stacks) {
				inventory.add(ItemStack.fromTag((CompoundTag)t));
			}
		}
		xp = tag.getInt("XP");
	}
	
	public CompoundTag toTag(CompoundTag tag) {
		super.toTag(tag);
		ListTag list = new ListTag();
		for(ItemStack s : inventory) {
			if(!s.isEmpty()) {
				list.add(s.toTag(new CompoundTag()));
			}
		}
		tag.put("Items", list);
		tag.putInt("XP", xp);
		return tag;
	}
	
}
