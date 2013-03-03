package wiyarmir.scopes.block;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wiyarmir.scopes.Scopes;
import wiyarmir.scopes.Utils;
import wiyarmir.scopes.gui.GuiHandler;
import wiyarmir.scopes.tileentity.TileEntityScope;

public class BasicScope extends GenericScope {
	private static int textureID = 4;

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
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess BA, int x, int y, int z, int side) {

		int metadata = BA.getBlockMetadata(x, y, z);
		if (side == metadata) {
			return textureID - 1;
		} else {
			return textureID;
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
