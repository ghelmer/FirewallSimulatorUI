package org.redwater.fwsim_ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollBar;

public class FWSimMainWindow {

	private JFrame frame;
	private JTextField pcapFilenameTextField;
	private JTextField rulesFilenameTextField;
	private JTextField resultTextField;

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
		
		JList rulesList = new JList();
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
		
		JList packetList = new JList();
		packetsPanel.add(packetList, "cell 0 1 2 4,grow");
		
		JScrollBar packetListScrollBar = new JScrollBar();
		packetsPanel.add(packetListScrollBar, "cell 2 1 1 4,grow");
		
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
		
		JMenuItem mntmOpen = new JMenuItem("Open Pcap File");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmClosePcapFile = new JMenuItem("Close Pcap File");
		mnFile.add(mntmClosePcapFile);
		
		JMenuItem mntmOpenRulesFile = new JMenuItem("Open Rules File");
		mnFile.add(mntmOpenRulesFile);
		
		JMenuItem mntmSaveRulesFile = new JMenuItem("Save Rules File");
		mnFile.add(mntmSaveRulesFile);
		
		JMenuItem mntmCloseRulesFile = new JMenuItem("Close Rules File");
		mnFile.add(mntmCloseRulesFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
	}

}
