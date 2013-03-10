package wiyarmir.scopes.block;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wiyarmir.scopes.Scopes;
import wiyarmir.scopes.Utils;
import wiyarmir.scopes.gui.GuiHandler;
import wiyarmir.scopes.tileentity.TileEntityScope;

public class BasicScope extends GenericScope {
	private static int textureDefault = 2;
	private static int textureFront = 3;

	private void setup() {
		setBlockName("basicScope");

	}

	public BasicScope(int id) {
		super(id, textureFront);
		setup();
	}

	protected BasicScope(int id, int texture, Material material) {
		super(id, texture, material);
		setup();
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		super.onBlockAdded(w, x, y, z);
		w.setBlockTileEntity(x, y, z,
				this.createTileEntity(w, w.getBlockMetadata(x, y, z)));
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		if (metadata == 0 && side == 3) {
			return textureFront;
		}
		if (side == metadata) {
			return textureFront;
		}
		return textureDefault;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityScope();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int par6, float par7, float par8, float par9) {

		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null || player.isSneaking()) {
			return false;
		}

		// Open GUI
		player.openGui(Scopes.instance, GuiHandler.GUI_BASIC_SCOPE, world, x,
				y, z);
		return true;

	}

}
