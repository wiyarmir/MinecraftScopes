package wiyarmir.scopes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class Utils {

	public static int dirZPos = 3;
	public static int dirZNeg = 2;
	public static int dirXPos = 5;
	public static int dirXNeg = 4;
	public static int dirYPos = 1;
	public static int dirYNeg = 6;

	public static int yaw2dir(float yaw) {
		int dir = (MathHelper
				.floor_double((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3) + 3;
		if (dir > 4) {
			dir -= 4;
		}
		switch (dir) {
		case 1:
			return dirZPos;
		case 2:
			return dirXNeg;
		case 3:
			return dirZNeg;
		case 4:
			return dirXPos;
		default:
			return 0;
		}
	}

	public static Packet packetFromTileEntity(TileEntity te) {
		NBTTagCompound tag = new NBTTagCompound();
		te.writeToNBT(tag);
		return packetFromNBT(0, tag);
	}

	public static Packet packetFromNBT(int type, NBTTagCompound tag) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(bytes);
		try {
			stream.write(type);
			NBTBase.writeNamedTag(tag, stream);
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}
		byte[] data = bytes.toByteArray();
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Scopes.ID;
		packet.length = data.length;
		packet.data = data;
		return packet;
	}

	public static NBTTagCompound nbtFromPacket(Packet250CustomPayload pkt) {
		DataInput stream = new DataInputStream(new ByteArrayInputStream(
				pkt.data, 1, pkt.length - 1));
		try {
			NBTBase tag = NBTBase.readNamedTag(stream);
			return (NBTTagCompound) tag;
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}
	}
}
