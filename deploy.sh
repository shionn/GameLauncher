#!/bin/sh

mvn clean package

echo "deploy jar"
cp -f target/GameLauncher-jar-with-dependencies.jar ~/.local/bin/



echo "Build Desktop"
echo "[Desktop Entry]
Name=Game Launcher
Comment=Game Launcher
GenericName=Game Launcher
X-GNOME-FullName=Game Launcher
Exec=java -jar /home/shionn/.local/bin/GameLauncher-jar-with-dependencies.jar
Terminal=false
X-MultipleArgs=false
Type=Application
Categories=Game
Icon=None.png
StartupWMClass=Game Launcher
StartupNotify=true" > ~/.local/share/applications/GameLauncher.desktop