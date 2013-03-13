package wiyarmir.scopes.block;

import java.util.Random;

import wiyarmir.scopes.Scopes;

public class PortableScopeBlock extends BasicScope {
	protected static int textureDefault = 5;
	protected static int textureFront = 6;

	private void setup(){

		setBlockName("portableScopeBlock");
		setTextureFront(textureFront);
		setTextureDefault(textureDefault);
		
		
	}
	public PortableScopeBlock(int id) {
		super(id, textureDefault);
		setup();
	}

	
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return Scopes.portableScopeItem.itemID;
	}
}
