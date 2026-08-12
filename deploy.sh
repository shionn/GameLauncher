#!/bin/sh

mvn clean package

echo "deploy jar"
sudo cp -f target/GameLauncher-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/share/java/


echo "Build Desktop"
echo "[Desktop Entry]
Name=Game Launcher
Comment=Game Launcher
GenericName=Game Launcher
X-GNOME-FullName=Game Launcher
Exec=java -jar /usr/share/java/GameLauncher-1.0-SNAPSHOT-jar-with-dependencies.jar
Terminal=false
X-MultipleArgs=false
Type=Application
Categories=Game
Icon=None.png
StartupWMClass=Game Launcher
StartupNotify=true" | sudo tee /usr/share/applications/GameLauncher.desktop > /dev/null