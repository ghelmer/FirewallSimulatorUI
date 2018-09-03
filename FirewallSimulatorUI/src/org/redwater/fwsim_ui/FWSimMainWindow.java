package org.redwater.fwsim_ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.pcap4j.packet.IpPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.util.Packets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class FWSimMainWindow {

	private JFrame frame;
	private JTextField pcapFilenameTextField;
	private JTextField rulesFilenameTextField;
	private JTextField resultTextField;

	private JTable packetList;
	//private DefaultListModel<String> packetListModel;
	Vector<Vector<String>> packetListRows;
	Vector<String> packetListColumnNames;
	
	private PacketList packets;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FWSimMainWindow window = new FWSimMainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FWSimMainWindow() {
		packets = new PacketList();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 720, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel rulesPanel = new JPanel();
		frame.getContentPane().add(rulesPanel, BorderLayout.CENTER);
		rulesPanel.setLayout(new MigLayout("", "[80.00px:80.00px][500px,grow][][][80px]", "[29px][29px][29px][29px][200px,grow]"));
		
		
		JLabel rulesLabel = new JLabel("Rules");
		rulesLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		rulesPanel.add(rulesLabel, "cell 0 0,alignx left,aligny center");
		
		rulesFilenameTextField = new JTextField();
		rulesPanel.add(rulesFilenameTextField, "cell 1 0,growx");
		rulesFilenameTextField.setColumns(10);
		
		JList<String> rulesList = new JList<>();
		rulesPanel.add(rulesList, "cell 0 1 2 4,grow");
		
		JButton btnAddRule = new JButton("Add Rule");
		rulesPanel.add(btnAddRule, "cell 4 1,growx,aligny center");
		
		JButton btnRemoveRule = new JButton("Remove Rule");
		rulesPanel.add(btnRemoveRule, "cell 4 2,growx,aligny center");
		
		JButton btnMoveRule = new JButton("Move Rule");
		rulesPanel.add(btnMoveRule, "cell 4 3,growx,aligny center");
		
		JScrollBar scrollBar = new JScrollBar();
		rulesPanel.add(scrollBar, "cell 2 1 1 4,grow");
				
		JPanel packetsPanel = new JPanel();
		frame.getContentPane().add(packetsPanel, BorderLayout.NORTH);
		packetsPanel.setLayout(new MigLayout("", "[80.00px:80.00px][500px,grow][][80px,grow]", "[][29px][29px][29px][120px,grow]"));
		
		JLabel packetsLabel1 = new JLabel();
		packetsLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		packetsLabel1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		packetsLabel1.setText("Packets");
		packetsPanel.add(packetsLabel1, "cell 0 0,alignx left,aligny center");
		
		pcapFilenameTextField = new JTextField();
		pcapFilenameTextField.setToolTipText("Pcap Filename");
		pcapFilenameTextField.setEditable(false);
		packetsPanel.add(pcapFilenameTextField, "cell 1 0,growx,aligny top");
		pcapFilenameTextField.setColumns(80);
		
		packetListRows = new Vector<>();
		packetListColumnNames = new Vector<>();
		packetListColumnNames.addElement("#");
		packetListColumnNames.addElement("Description");
		packetList = new JTable(packetListRows, packetListColumnNames);
		JScrollPane packetScrollPane = new JScrollPane(packetList);
		packetList.setFillsViewportHeight(true);
		packetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		packetList.setRowSelectionAllowed(true);
		packetList.setColumnSelectionAllowed(false);
		packetScrollPane.setViewportView(packetList);
		packetsPanel.add(packetScrollPane, "cell 0 1 2 4,grow");
		
		// JScrollBar packetListScrollBar = new JScrollBar();
		// packetsPanel.add(packetListScrollBar, "cell 2 1 1 4,grow");
		
		JButton btnTestSelectedPacket = new JButton("Test Selected Packet");
		packetsPanel.add(btnTestSelectedPacket, "cell 3 1,alignx center,aligny center");
		
		JLabel resultLabel = new JLabel("Result");
		packetsPanel.add(resultLabel, "cell 3 2");
		
		resultTextField = new JTextField();
		resultTextField.setEditable(false);
		packetsPanel.add(resultTextField, "cell 3 3,growx");
		resultTextField.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenPcap = new JMenuItem("Open Pcap File");
		mntmOpenPcap.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
		        openPcapFile();
		    }
		});
		mnFile.add(mntmOpenPcap);
		
		JMenuItem mntmClosePcapFile = new JMenuItem("Close Pcap File");
		mntmOpenPcap.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
		        closePcapFile();
		    }
		});
		mnFile.add(mntmClosePcapFile);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmOpenRulesFile = new JMenuItem("Open Rules File");
		mnFile.add(mntmOpenRulesFile);
		
		JMenuItem mntmSaveRulesFile = new JMenuItem("Save Rules File");
		mnFile.add(mntmSaveRulesFile);
		
		JMenuItem mntmCloseRulesFile = new JMenuItem("Close Rules File");
		mnFile.add(mntmCloseRulesFile);
		
		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
		        System.exit(0);
		    }
		});
		mnFile.add(mntmExit);
	}

	/**
	 * Open and load the packet capture file.
	 */
	private void openPcapFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Pcap", "pcap");
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter("PcapNG", "pcapng");
		chooser.addChoosableFileFilter(filter1);
		chooser.addChoosableFileFilter(filter2);
		//chooser.setCurrentDirectory("<YOUR DIR COMES HERE>");
		int returnVal = chooser.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				packets.open(chooser.getSelectedFile());
				Iterator<WrappedPacket> it = packets.iterator();
				Vector<Vector<String>> updatedPacketListRows = new Vector<>();
				//packetListModel.clear();
				int packetNumber = 0;
				while (it.hasNext()) {
					packetNumber++;
					WrappedPacket p = it.next();
					//packetListModel.addElement(String.format("%4d %s", packetNumber, p.toString()));
					Vector<String> packetListRow = new Vector<>();
					packetListRow.addElement(Integer.toString(packetNumber));
					packetListRow.addElement(p.toString());
					updatedPacketListRows.addElement(packetListRow);
				}
				packetListRows = updatedPacketListRows;
				((DefaultTableModel)packetList.getModel()).setDataVector(packetListRows, packetListColumnNames);
			}
			catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
					    e.getMessage(),
					    "Pcap File Error",
					    JOptionPane.ERROR_MESSAGE);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(frame,
					    e.getMessage(),
					    "Packet Analysis Error",
					    JOptionPane.ERROR_MESSAGE);				
			}
		}
	}
	
	/**
	 * Close the packet capture file.
	 */
	private void closePcapFile() {
		packets.close();
		//packetListModel.clear();
		packetListRows = new Vector<>();
		((DefaultTableModel)packetList.getModel()).setDataVector(packetListRows, packetListColumnNames);
	}
}
