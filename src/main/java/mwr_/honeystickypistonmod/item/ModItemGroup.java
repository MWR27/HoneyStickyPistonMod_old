package mwr_.honeystickypistonmod.item;

import mwr_.honeystickypistonmod.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup extends ItemGroup {
	public static final ModItemGroup HONEY_STICKY_PISTON = new ModItemGroup(ItemGroup.TABS.length,
			"my_first_mod");

	public ModItemGroup(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ModBlocks.HONEY_STICKY_PISTON.get());
	}
}
