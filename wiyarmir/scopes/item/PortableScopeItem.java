package wiyarmir.scopes.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import wiyarmir.scopes.Scopes;
import wiyarmir.scopes.Utils;
import wiyarmir.scopes.block.PortableScopeBlock;

public class PortableScopeItem extends GenericTool {

	protected static final int texture = 0;

	public PortableScopeItem(int id) {
		super(id, 1, CreativeTabs.tabRedstone, texture, "Portable Scope");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int par7, float par8, float par9, float par10) {
		if (world.getBlockId(x, y, z) != Block.snow.blockID) {
			if (par7 == 0) {
				--y;
			}

			if (par7 == 1) {
				++y;
			}

			if (par7 == 2) {
				--z;
			}

			if (par7 == 3) {
				++z;
			}

			if (par7 == 4) {
				--x;
			}

			if (par7 == 5) {
				++x;
			}

			if (!world.isAirBlock(x, y, z)) {
				return false;
			}
		}

		if (!player.canPlayerEdit(x, y, z, par7, stack)) {
			return false;
		} else {
			if (Scopes.portableScopeBlock.canPlaceBlockAt(world, x, y, z)) {
				--stack.stackSize;
				world.setBlockWithNotify(x, y, z,
						Scopes.portableScopeBlock.blockID);
				world.setBlockMetadata(x, y, z,
						Utils.yaw2dir(player.rotationYaw));
			}

			return true;
		}
	}

}
