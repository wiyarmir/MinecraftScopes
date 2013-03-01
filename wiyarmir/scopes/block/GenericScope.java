package wiyarmir.scopes.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import wiyarmir.scopes.CommonProxy;

public class GenericScope extends Block {

	private static int textureID = 0;
	private static Material defaultMaterial = Material.circuits;

	private void setup() {
		setCreativeTab(CreativeTabs.tabRedstone);
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
	}

	protected GenericScope(int id) {
		super(id, textureID, defaultMaterial);
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

}