package wiyarmir.scopes.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import wiyarmir.scopes.CommonProxy;

public class GenericTool extends Item {

	public GenericTool(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabRedstone);
		setIconIndex(0);
		setItemName("genericItem");
	}

	public GenericTool(int id, int maxStackSize, CreativeTabs tab, int texture,
			String name) {
		super(id);
		setMaxStackSize(maxStackSize);
		setCreativeTab(tab);
		setIconIndex(texture);
		setItemName(name);
	}

	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}
}
