package wiyarmir.scopes.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BasicGenerator extends GenericScope {

	protected static int textureID = 4;

	private void setup() {
		setBlockName("basicGenerator");

	}

	public BasicGenerator(int id) {
		super(id, textureID);
		setup();
	}

	protected BasicGenerator(int id, int texture, Material material) {
		super(id, texture, material);
		setup();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess BA, int x, int y, int z, int side) {

		int metadata = BA.getBlockMetadata(x, y, z);
		if (side == metadata) {
			return textureID + 1;
		} else {
			return textureID;
		}
	}
}
