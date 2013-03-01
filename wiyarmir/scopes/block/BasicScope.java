package wiyarmir.scopes.block;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import wiyarmir.scopes.Scopes;
import wiyarmir.scopes.Utils;
import wiyarmir.scopes.gui.GuiHandler;
import wiyarmir.scopes.tileentity.TileEntityScope;

public class BasicScope extends GenericScope {
	private static int textureID = 3;

	private int resolution = 10;

	private void setup() {
		setBlockName("basicScope");

	}

	public BasicScope(int id) {
		super(id, textureID);
		setup();
	}

	protected BasicScope(int id, int texture, Material material) {
		super(id, texture, material);
		setup();
	}

	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z,
			EntityLiving placer) {
		super.onBlockPlacedBy(par1World, x, y, z, placer);

		int orient = Utils.yaw2dir(placer.rotationYaw);
		par1World.setBlockMetadataWithNotify(x, y, z, orient);
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

	/*
	 * @Override public int getBlockTextureFromSide(int side) { if (side ==
	 * Utils.dirXPos) { return textureID; } else { return textureID + 1;
	 * } }
	 */

	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		if (side == metadata) {
			return textureID;
		} else {
			return textureID + 1;
		}
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
