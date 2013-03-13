package wiyarmir.scopes;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import wiyarmir.scopes.block.BasicGenerator;
import wiyarmir.scopes.block.BasicScope;
import wiyarmir.scopes.block.PortableScopeBlock;
import wiyarmir.scopes.gui.GuiHandler;
import wiyarmir.scopes.item.PortableScopeItem;
import wiyarmir.scopes.tileentity.TileEntityGenerator;
import wiyarmir.scopes.tileentity.TileEntityScope;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = Scopes.ID, name = Scopes.NAME, version = Scopes.VERSION, dependencies = "required-after:Forge@[6.5.0.0,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { Scopes.ID }, packetHandler = PacketHandler.class)
public class Scopes {

	public static final String ID = "Scopes";
	public static final String NAME = "Scopes";
	public static final String VERSION = "0.0.2";

	public static final int blockIDRangeStart = 1551;

	public final static Block basicScope = new BasicScope(blockIDRangeStart + 0);
	public final static Block basicGenerator = new BasicGenerator(
			blockIDRangeStart + 1);
	public final static Block portableScopeBlock = new PortableScopeBlock(
			blockIDRangeStart + 2);

	public final static Item portableScopeItem = new PortableScopeItem(5000);

	@Instance(ID)
	public static Scopes instance;

	@SidedProxy(clientSide = "wiyarmir.scopes.client.ClientProxy", serverSide = " wiyarmir.scopes.CommonProxy")
	public static CommonProxy proxy;

	public static Logger logger;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		logger = Logger.getLogger(ID);
		logger.setParent(FMLLog.getLogger());
		logger.info("Starting " + ID);

		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		Configs.load(config);
	}

	@Init
	public void load(FMLInitializationEvent event) {
		blockRegistration();

		itemRegistration();

		tileEntityRegistration();

		addRecipes();

		// GUIs
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

		proxy.registerRenderers();
	}

	private void addRecipes() {
		ItemStack ironStack = new ItemStack(Item.ingotIron);
		ItemStack diodeStack = new ItemStack(Item.redstoneRepeater);
		ItemStack redstoneStack = new ItemStack(Item.redstone);
		ItemStack basicScopeStack = new ItemStack(basicScope);
		ItemStack portableScopeItemStack = new ItemStack(portableScopeItem);
		// GameRegistry.addRecipe(new ShapedOreRecipe(basicScope, true,
		// new Object[] { "SSS", "STS", "SRS", Character.valueOf('S'),
		// "ingotIron", Character.valueOf('T'), "ingotTin",
		// Character.valueOf('R'), "ingotRedstone" }));
		GameRegistry.addRecipe(basicScopeStack, "III", "ICI", "IRI", 'I',
				ironStack, 'C', diodeStack, 'R', redstoneStack);
		GameRegistry.addShapelessRecipe(new ItemStack(portableScopeBlock),
				basicScopeStack);

	}

	private void blockRegistration() {
		LanguageRegistry.addName(basicScope, "Basic Scope");
		MinecraftForge.setBlockHarvestLevel(basicScope, "pickaxe", 0);
		GameRegistry.registerBlock(basicScope, "basicScope");

		LanguageRegistry.addName(basicGenerator, "Basic Generator");
		MinecraftForge.setBlockHarvestLevel(basicGenerator, "pickaxe", 0);
		GameRegistry.registerBlock(basicGenerator, "basicGenerator");

		LanguageRegistry.addName(portableScopeBlock, "Portable Scope");
		MinecraftForge.setBlockHarvestLevel(portableScopeBlock, "pickaxe", 0);
		GameRegistry.registerBlock(portableScopeBlock, "portableScopeBlock");

	}

	private void itemRegistration() {
		LanguageRegistry.addName(portableScopeItem, "Portable Scope");
	}

	private void tileEntityRegistration() {
		GameRegistry.registerTileEntity(TileEntityScope.class, "scopeEntity");
		GameRegistry.registerTileEntity(TileEntityGenerator.class,
				"generatorEntity");
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
}