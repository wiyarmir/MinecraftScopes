package wiyarmir.scopes.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wiyarmir.scopes.tileentity.TileEntityGenerator;

public class BasicGenerator extends GenericScope {

	protected static int textureDefault = 4;
	protected static int textureFront = 5;

	private void setup() {
		setBlockName("basicGenerator");

	}

	public BasicGenerator(int id) {
		super(id, textureDefault);
		setup();
	}

	protected BasicGenerator(int id, int texture, Material material) {
		super(id, texture, material);
		setup();
	}

	/*
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public int getBlockTexture(IBlockAccess BA, int x,
	 * int y, int z, int side) {
	 * 
	 * int metadata = BA.getBlockMetadata(x, y, z); metadata = (metadata < 2 ? 2
	 * : metadata); if (side == metadata) { return textureID + 1; } else {
	 * return textureID; } }
	 */

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
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityGenerator();
	}
}
