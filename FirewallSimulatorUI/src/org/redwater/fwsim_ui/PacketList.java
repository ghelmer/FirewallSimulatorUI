package org.redwater.fwsim_ui;

import java.io.EOFException;
import java.io.File;
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
public class PacketInput {
	private PcapHandle pcapHandle;
	private List<Packet> packets;
	
	public PacketInput() {
		pcapHandle = null;
	}
	
	/**
	 * Open the specified file to read packets.
	 * @param f - File containing packet capture
	 * @throws PcapNativeException
	 */
	public void open(File f) throws PcapNativeException {
		close();
		pcapHandle = Pcaps.openOffline(f.getAbsolutePath());
		loadPackets();
	}
	
	/**
	 * Load the packets from the current pcap file.
	 */
	private void loadPackets() {
		List<Packet> packetList = new ArrayList<>();
	    try {
	        Packet packet;
	        while ((packet = pcapHandle.getNextPacketEx()) != null) {
	            packetList.add(packet);
	        }
	    } catch (EOFException e) {
	    } catch (PcapNativeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public Iterator<Packet> iterator() {
		return packets.iterator();
	}
}
