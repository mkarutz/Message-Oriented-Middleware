#!/bin/sh
gnome-terminal -t server1 -e "java -jar build/libs/Server.jar -lp 4000 -slp 4001 -d"
sleep 2
gnome-terminal -t server2 -e "java -jar build/libs/Server.jar -lp 4010 -slp 4011 -rh localhost -rp 4001 -d -secure"
sleep 2
gnome-terminal -t server3 -e "java -jar build/libs/LegacyServer.jar -lp 4020 -rh localhost -rp 4000 -d"
sleep 2
gnome-terminal -t server4 -e "java -jar build/libs/LegacyServer.jar -lp 4030 -rh localhost -rp 4010 -d"