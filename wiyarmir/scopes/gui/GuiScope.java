package wiyarmir.scopes.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import wiyarmir.scopes.Scopes;
import wiyarmir.scopes.tileentity.TileEntityScope;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScope extends GuiScreen {
	private static final int ID_BUTTON_PLUS = 1;
	private static final int ID_BUTTON_MINUS = 2;

	/** Stacks renderer. Icons, stack size, health, etc... */
	protected static RenderItem itemRenderer = new RenderItem();

	/** The X size of the inventory window in pixels. */
	protected int xSize = 250;

	/** The Y size of the inventory window in pixels. */
	protected int ySize = 165;

	/** A list of the players inventory slots. */
	public TileEntityScope scope;

	private static int displayXSize = 154, displayYSize = 126,
			displayBeginX = 86, displayBeginY = 8;

	/**
	 * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiLeft;

	/**
	 * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiTop;
	private Slot theSlot;

	public GuiScope(TileEntityScope scp) {
		this.scope = scp;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		super.initGui();
		// this.mc.thePlayer.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		// id, x, y, width, height, text
		controlList.add(new GuiButton(ID_BUTTON_MINUS, guiLeft + 189,
				guiTop + 138, 20, 20, "-"));
		controlList.add(new GuiButton(ID_BUTTON_PLUS, guiLeft + 217,
				guiTop + 138, 20, 20, "+"));
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		int left = this.guiLeft;
		int top = this.guiTop;
		this.drawGuiContainerBackgroundLayer(par3, par1, par2);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		super.drawScreen(par1, par2, par3);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glTranslatef((float) left, (float) top, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		this.theSlot = null;
		short var6 = 240;
		short var7 = 240;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
				(float) var6 / 1.0F, (float) var7 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int var9;
		this.drawGuiContainerForegroundLayer(par1, par2);
		InventoryPlayer var15 = this.mc.thePlayer.inventory;

		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

	private void drawItemStack(ItemStack par1ItemStack, int par2, int par3) {
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		this.zLevel = 200.0F;
		itemRenderer.zLevel = 200.0F;
		itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer,
				this.mc.renderEngine, par1ItemStack, par2, par3);
		itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer,
				this.mc.renderEngine, par1ItemStack, par2, par3);
		this.zLevel = 0.0F;
		itemRenderer.zLevel = 0.0F;
	}

	/**
	 * Draws an inventory slot
	 */
	protected void drawSlotInventory(Slot par1Slot) {
		int var2 = par1Slot.xDisplayPosition;
		int var3 = par1Slot.yDisplayPosition;
		ItemStack var4 = par1Slot.getStack();

		this.zLevel = 100.0F;
		itemRenderer.zLevel = 100.0F;

		if (var4 == null) {
			int var6 = par1Slot.getBackgroundIconIndex();

			if (var6 >= 0) {
				GL11.glDisable(GL11.GL_LIGHTING);
				this.mc.renderEngine.bindTexture(this.mc.renderEngine
						.getTexture(par1Slot.getBackgroundIconTexture()));
				this.drawTexturedModalRect(var2, var3, var6 % 16 * 16,
						var6 / 16 * 16, 16, 16);
				GL11.glEnable(GL11.GL_LIGHTING);
			}
		}

		itemRenderer.zLevel = 0.0F;
		this.zLevel = 0.0F;
	}

	/**
	 * Called when the mouse is clicked.
	 */
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);

	}

	/**
	 * Called when the mouse is moved or a mouse button is released. Signature:
	 * (mouseX, mouseY, which) which==-1 is mouseMove, which==0 or which==1 is
	 * mouseUp
	 */
	protected void mouseMovedOrUp(int mouseX, int mouseY, int which) {

	}

	/**
	 * Returns if the passed mouse position is over the specified slot.
	 */
	private boolean isMouseOverSlot(Slot par1Slot, int par2, int par3) {
		return this.isPointInRegion(par1Slot.xDisplayPosition,
				par1Slot.yDisplayPosition, 16, 16, par2, par3);
	}

	/**
	 * Args: left, top, width, height, pointX, pointY. Note: left, top are local
	 * to Gui, pointX, pointY are local to screen
	 */
	protected boolean isPointInRegion(int par1, int par2, int par3, int par4,
			int par5, int par6) {
		int var7 = this.guiLeft;
		int var8 = this.guiTop;
		par5 -= var7;
		par6 -= var8;
		return par5 >= par1 - 1 && par5 < par1 + par3 + 1 && par6 >= par2 - 1
				&& par6 < par2 + par4 + 1;
	}

	protected void handleMouseClick(Slot par1Slot, int par2, int par3, int par4) {

	}

	/**
	 * Fired when a key is typed. This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e).
	 */
	protected void keyTyped(char par1, int par2) {
		if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.keyCode) {
			this.mc.thePlayer.closeScreen();
		}

		this.checkHotbarKeys(par2);

		if (par2 == this.mc.gameSettings.keyBindPickBlock.keyCode
				&& this.theSlot != null && this.theSlot.getHasStack()) {
			this.handleMouseClick(this.theSlot, this.theSlot.slotNumber,
					this.ySize, 3);
		}
	}

	/**
	 * This function is what controls the hotbar shortcut check when you press a
	 * number key when hovering a stack.
	 */
	protected boolean checkHotbarKeys(int par1) {
		if (this.mc.thePlayer.inventory.getItemStack() == null
				&& this.theSlot != null) {
			for (int var2 = 0; var2 < 9; ++var2) {
				if (par1 == 2 + var2) {
					this.handleMouseClick(this.theSlot,
							this.theSlot.slotNumber, var2, 2);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	public void onGuiClosed() {

	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		super.updateScreen();

		if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
			this.mc.thePlayer.closeScreen();
		}
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		fontRenderer.drawString("Scope", 8, 6, 4210752);
		fontRenderer.drawString("Range:", 90, 144, 0x000000);

		fontRenderer
				.drawString("" + scope.getRange() + "x", 126, 144, 0x000000);
		// // draws "Inventory" or your regional equivalent
		// fontRenderer.drawString(
		// StatCollector.translateToLocal("container.inventory"), 8,
		// ySize - 96 + 2, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the
	 * items)
	 */

	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		// draw your Gui here, only thing you need to change is the path
		int background = mc.renderEngine
				.getTexture("/wiyarmir/scopes/gui/scope_bg.png");
		int grid = mc.renderEngine
				.getTexture("/wiyarmir/scopes/gui/scope_grid.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(background);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		// scope stuff
		Integer[] data = new Integer[scope.getData().size()];
		scope.getData().toArray(data);
		// Scopes.logger.info("data: " + data);

		// Plot data
		for (int i = 0; i < data.length; i++) {
			for (int j = data[i]; j > 0; j--) {
				this.drawTexturedModalRect(x + displayBeginX + 10 * i, y
						+ displayBeginY + displayYSize - (8 * j), 0, 166, 10, 8);
			}
		}

		// Plot grid
		// TODO test efficiency
		this.mc.renderEngine.bindTexture(grid);
		this.drawTexturedModalRect(x + displayBeginX - 1,
				y + displayBeginY - 2, 0, 0, 151, 128);
	}

	protected void actionPerformed(GuiButton guibutton) {
		Packet250CustomPayload packet = new Packet250CustomPayload();
		char ch = '?';
		switch (guibutton.id) {
		case ID_BUTTON_MINUS:
			ch = '-';
			scope.decreaseRange();
			break;
		case ID_BUTTON_PLUS:
			ch = '+';
			scope.increaseRange();
			break;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1);
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeByte((int) ch);
		} catch (IOException e) {
			e.printStackTrace();
		}
		packet.channel = Scopes.ID;
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToServer(packet); // send packet
	}
}
