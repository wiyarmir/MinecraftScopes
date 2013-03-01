package wiyarmir.scopes.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wiyarmir.scopes.Scopes;
import wiyarmir.scopes.tileentity.TileEntityScope;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int GUI_BASIC_SCOPE = 0;

	// returns an instance of the Container you made earlier
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof TileEntityScope) {
			// return new ContainerTiny(player.inventory, (TileEntityTiny)
			// tileEntity);
		}
		return null;
	}

	// returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		switch (id) {
		case GUI_BASIC_SCOPE:
			if (tileEntity instanceof TileEntityScope) {
				return new GuiScope((TileEntityScope) tileEntity);
			}
			break;
		default:
			Scopes.logger.warning("WARNING: GuiHandler GUI id not in bounds.");
			break;
		}

		return null;
	}

}
