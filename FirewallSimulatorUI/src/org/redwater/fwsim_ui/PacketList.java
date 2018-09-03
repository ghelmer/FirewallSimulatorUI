package org.redwater.fwsim_ui;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

/**
 * Manage the current pcap input file.
 * @author ghelmer
 *
 */
public class PacketList {
	private PcapHandle pcapHandle;
	private List<WrappedPacket> packets;
	
	public PacketList() {
		pcapHandle = null;
	}
	
	/**
	 * Open the specified file to read packets.
	 * @param f - File containing packet capture
	 * @throws IOException 
	 * @throws PcapNativeException
	 * @throws NotOpenException 
	 * @throws TimeoutException 
	 */
	public void open(File f) throws IOException {
		close();
		try {
			pcapHandle = Pcaps.openOffline(f.getAbsolutePath());
			loadPackets();
		}
		catch (PcapNativeException | TimeoutException | NotOpenException e) {
			throw new IOException(String.format("Failed to open or read %s: %s", f.getAbsolutePath(), e.getMessage()));
		}
	}
	
	/**
	 * Load the packets from the current pcap file.
	 * @throws NotOpenException 
	 * @throws TimeoutException 
	 * @throws PcapNativeException 
	 */
	private void loadPackets() throws PcapNativeException, TimeoutException, NotOpenException {
		List<WrappedPacket> packetList = new ArrayList<>();
	    try {
	        Packet packet;
	        while ((packet = pcapHandle.getNextPacketEx()) != null) {
	            packetList.add(new WrappedPacket(packet));
	        }
	    } catch (EOFException e) {
	    }
	    packets = packetList;
	}
	
	/**
	 * Close the currently-open pcap file.
	 */
	public void close() {
		if (pcapHandle != null) {
			pcapHandle.close();
			pcapHandle = null;
		}
	}
	
	public Iterator<WrappedPacket> iterator() {
		return packets.iterator();
	}
}
