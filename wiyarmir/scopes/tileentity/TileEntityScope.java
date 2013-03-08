package wiyarmir.scopes.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import wiyarmir.scopes.Scopes;
import wiyarmir.scopes.Utils;
import wiyarmir.scopes.util.ScopeMemory;

public class TileEntityScope extends TileEntity {
	private static int vRes = 15, hRes = 15;

	int resolution = 2;

	private ScopeMemory data = new ScopeMemory(hRes);

	int ticcount = 0;
	private static final float[] ranges = { 1.0f, 2.5f, 5.0f, 10.0f };
	int indexOfRanges = 2;

	private boolean tilt = false;

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(
				(double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
				(double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void updateEntity() {
		if ((ticcount % (ranges[indexOfRanges] * resolution)) == 0) {
			ticcount = 0;// overflow?

			boolean power = this.worldObj.isBlockGettingPowered(this.xCoord,
					this.yCoord, this.zCoord);
			if (power) {
				
				// Makes red dot tilt only if powered
				tilt = !tilt;
				
				int strength = getCurrentStrength(this.worldObj,
						this.xCoord - 1, this.yCoord, this.zCoord)
						+ getCurrentStrength(this.worldObj, this.xCoord + 1,
								this.yCoord, this.zCoord)
						+ getCurrentStrength(this.worldObj, this.xCoord,
								this.yCoord, this.zCoord + 1)
						+ getCurrentStrength(this.worldObj, this.xCoord,
								this.yCoord, this.zCoord - 1);
				data.add(strength > 15 ? 15 : strength);
			} else {
				//If not powered, keep light off
				tilt = false;
				
				data.add(0);
			}
		}
		ticcount++;
	}

	/**
	 * Returns the current strength at the specified block if it is greater than
	 * the passed value, or the passed value otherwise. Signature: (world, x, y,
	 * z, strength)
	 */
	private int getCurrentStrength(World par1World, int par2, int par3, int par4) {
		if (par1World.getBlockId(par2, par3, par4) != Block.redstoneWire.blockID) {
			return 0;
		} else {
			int strength = par1World.getBlockMetadata(par2, par3, par4);
			return strength;
		}
	}

	public TileEntityScope() {
		super();
	}

	public ScopeMemory getData() {
		return data;
	}

	@Override
	public Packet getDescriptionPacket() {
		return Utils.packetFromTileEntity(this);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
	}

	public float getRange() {
		// TODO Auto-generated method stub
		return ranges[indexOfRanges];
	}

	public void decreaseRange() {
		if (--indexOfRanges < 0) {
			indexOfRanges = 0;
		}
		onRangeChange(ranges[indexOfRanges]);
	}

	public void increaseRange() {
		if (++indexOfRanges >= ranges.length) {
			indexOfRanges = ranges.length - 1;
		}
		onRangeChange(ranges[indexOfRanges]);
	}

	private void onRangeChange(float newRange) {
		for (int i = 0; i < data.size(); i++) {
			data.add(0);
		}
	}

	public boolean getTilt() {
		return tilt;
	}
}
