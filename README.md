# Firewall Simulator UI

## Simple packet filtering firewall simulator UI

This project under construction is planned to provide a user interface to the Firewall Simulator code. Plans are to add logic that loads packets from a packet capture file (pcap format), loads rules in text format from a file, and will check packets one-by-one against the rules, indicate which rule matched, and the action that would be taken.

The goal is to illustrate basic packet filter firewall functionality showing how packets are filtered based on protocols, IP addresses, and TCP or UDP port numbers.

This project depends on the Firewall Simulator code, which in turn depends on [Pcap4J](https://www.pcap4j.org/) for network packets and [Apache Commons](https://commons.apache.org/) projects. It uses Maven for the build.

## Features

Not many features so far. The UI is under construction.
