gnome-terminal -e "java -jar build/libs/Server.jar -lp 4035 -d"
sleep 1
gnome-terminal -e "java -jar build/libs/Server.jar -rh localhost -rp 4035 -d -lp 4135"