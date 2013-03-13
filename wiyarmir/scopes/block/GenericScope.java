package wiyarmir.scopes.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import wiyarmir.scopes.CommonProxy;
import wiyarmir.scopes.Utils;

public class GenericScope extends Block {

	protected static int textureDefault = 0, textureFront = 0;
	protected static Material defaultMaterial = Material.circuits;

	private void setup() {
		setCreativeTab(CreativeTabs.tabRedstone);
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
	}

	protected GenericScope(int id) {
		super(id, textureDefault, defaultMaterial);
		setup();
	}

	protected GenericScope(int id, int texture) {
		super(id, texture, defaultMaterial);
		setup();
	}

	protected GenericScope(int id, int texture, Material material) {
		super(id, texture, material);
		setup();
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCK_PNG;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z,
			EntityLiving placer) {
		super.onBlockPlacedBy(par1World, x, y, z, placer);

		int orient = Utils.yaw2dir(placer.rotationYaw);
		par1World.setBlockMetadataWithNotify(x, y, z, orient);
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

	public void setTextureFront(int textureID) {
		textureFront = textureID;
	}


	public void setTextureDefault(int textureID) {
		textureDefault = textureID;
	}

}