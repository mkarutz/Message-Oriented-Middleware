#!/bin/sh
gnome-terminal -e "java -jar build/libs/Server.jar -lp 4035 -d"
sleep 1
gnome-terminal -e "java -jar build/libs/Server.jar -rh localhost -rp 4035 -d -lp 4135"
sleep 1
gnome-terminal -e "java -jar build/libs/Server.jar -rh localhost -rp 4135 -d -lp 4235"
sleep 1
gnome-terminal -e "java -jar build/libs/Client.jar -rh localhost -rp 4135 -u anonymous"
sleep 1
gnome-terminal -e "java -jar build/libs/Client.jar -rh localhost -rp 4035 -u anonymous"
sleep 1
gnome-terminal -e "java -jar build/libs/Client.jar -rh localhost -rp 4035 -u anonymous"