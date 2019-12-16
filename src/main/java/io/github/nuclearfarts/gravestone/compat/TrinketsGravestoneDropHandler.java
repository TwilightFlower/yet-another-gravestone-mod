package io.github.nuclearfarts.gravestone.compat;

import java.util.List;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class TrinketsGravestoneDropHandler implements GravestoneDropHandler {

	@Override
	public void handle(PlayerEntity player, List<ItemStack> gravestoneInventory) {
		Inventory trinkets = TrinketsApi.getTrinketsInventory(player);
		for(int i = 0; i < trinkets.getInvSize(); i++) {
			gravestoneInventory.add(trinkets.removeInvStack(i));
		}
	}

}
