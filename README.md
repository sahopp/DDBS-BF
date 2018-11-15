# DDBS-BF

Servers:

172.23.93.116 MasterServer
172.23.93.118 SubServer1
172.23.93.119 SubServer2
172.23.93.120 Subserver3


Access Server: ssh ubuntu@IP

Transfer Directory: scp -r <path> ubuntu@IP:~/<path>
Transfer File: scp <path>/file.txt ubuntu@IP:~/<path>/file.txt

Delete: rm -rf <path> 

Run Application: java MasterServerApplication
