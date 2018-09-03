package org.redwater.fwsim_ui;

import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.util.Packets;

/**
 * Wrap a Pcap4J packet to ease representation and access. 
 * @author ghelmer
 *
 */
public class WrappedPacket {

	private Packet packet;
	
	public WrappedPacket(Packet p) {
		packet = p;
	}
	
	/**
	 * Get the Ethernet packet.
	 * @return Ethernet packet or null if not present
	 */
	public EthernetPacket getEthernetPacket() {
		if (Packets.containsEthernetPacket(packet)) {
			return (EthernetPacket)packet.get(EthernetPacket.class);
		}
		return null;
	}
	
	/**
	 * Get the IP packet.
	 * @return IP packet or null if not present
	 */
	public IpPacket getIpPacket() {
		if (Packets.containsIpPacket(packet)) {
			return (IpPacket)packet.get(IpPacket.class);
		}
		return null;
	}
	
	/**
	 * Get the TCP packet.
	 * @return TCP packet or null if not present
	 */
	public TcpPacket getTcpPacket() {
		if (Packets.containsTcpPacket(packet)) {
			return (TcpPacket)packet.get(TcpPacket.class);
		}
		return null;
	}
	
	/**
	 * Get the UDP packet.
	 * @return UDP packet or null if not present
	 */
	public UdpPacket getUdpPacket() {
		if (Packets.containsUdpPacket(packet)) {
			return (UdpPacket)packet.get(UdpPacket.class);
		}
		return null;
	}
	
	/**
	 * Get the string representation of the packet.
	 * @return string describing the packet
	 */
	public String toString() {
		String packetType = "Ether";
		StringBuilder sb = new StringBuilder();
		IpPacket ipPacket = getIpPacket();
		if (ipPacket != null) {
			sb.append(String.format("srcIp %s dstIP %s ",
					ipPacket.getHeader().getSrcAddr().getHostAddress(),
					ipPacket.getHeader().getDstAddr().getHostAddress()));
			packetType = "IP";
		}
		UdpPacket udpPacket = null;
		TcpPacket tcpPacket = getTcpPacket();
		if (tcpPacket != null) {
			sb.append(String.format("srcPort %d dstPort %d ",
					tcpPacket.getHeader().getSrcPort().valueAsInt(),
					tcpPacket.getHeader().getDstPort().valueAsInt()));
			packetType = "TCP";
		}
		else {
			udpPacket = getUdpPacket();
		}
		if (udpPacket != null) {
			sb.append(String.format("srcPort %d dstPort %d ",
					udpPacket.getHeader().getSrcPort().valueAsInt(),
					udpPacket.getHeader().getDstPort().valueAsInt()));
			packetType = "UDP";
		}
		return String.format("%s %s", packetType, sb.toString());
	}
}
