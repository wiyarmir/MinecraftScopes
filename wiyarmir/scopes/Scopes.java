package wiyarmir.scopes;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import wiyarmir.scopes.block.BasicGenerator;
import wiyarmir.scopes.block.BasicScope;
import wiyarmir.scopes.gui.GuiHandler;
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
	public static final String VERSION = "0.0.1";

	public final static Block basicScope = new BasicScope(500);
	public final static Block basicGenerator = new BasicGenerator(501);

	// public final static Block genericOre = new GenericOre(501, 1,
	// Material.iron);

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
		// See Basic items tutorial for Generic Ingot
		// LanguageRegistry.addName(genericIngot, "Generic Ingot");

		LanguageRegistry.addName(basicScope, "Basic Scope");
		MinecraftForge.setBlockHarvestLevel(basicScope, "pickaxe", 0);
		GameRegistry.registerBlock(basicScope, "basicScope");

		LanguageRegistry.addName(basicGenerator, "Basic Generator");
		MinecraftForge.setBlockHarvestLevel(basicGenerator, "pickaxe", 0);
		GameRegistry.registerBlock(basicGenerator, "basicGenerator");

		// End Basic Blocks

		// Tile entities
		GameRegistry.registerTileEntity(TileEntityScope.class, "scopeEntity");

		// GUIs
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

		proxy.registerRenderers();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
}