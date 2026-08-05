#!/bin/sh

#export LD_LIBRARY_PATH="$LD_LIBRARY_PATH:\
#/home/shionn/.local/share/Steam/ubuntu12_32/steam-runtime/pinned_libs_32:\
#/home/shionn/.local/share/Steam/ubuntu12_32/steam-runtime/pinned_libs_64:\
#/home/shionn/.local/share/Steam/ubuntu12_32/steam-runtime/lib/i386-linux-gnu:\
#/home/shionn/.local/share/Steam/ubuntu12_32/steam-runtime/usr/lib/i386-linux-gnu:\
#/home/shionn/.local/share/Steam/ubuntu12_32/steam-runtime/lib/x86_64-linux-gnu:\
#/home/shionn/.local/share/Steam/ubuntu12_32/steam-runtime/usr/lib/x86_64-linux-gnu:\
#/home/shionn/.local/share/Steam/ubuntu12_32/steam-runtime/lib:\
#/home/shionn/.local/share/Steam/ubuntu12_32/steam-runtime/usr/lib:\
#/usr/lib64/qt-3.3/lib:/usr/lib64/tcl8.6:/usr/lib/wine:/usr/lib64/wine:/lib:/lib64:"



UMU_LOG=1 \
PROTON_LOG=1 \
PROTONPATH=/home/shionn/Games/.proton/GE-Proton10-34 \
WINEPREFIX=/home/shionn/Games/Beyond\ Good\ and\ Evil \
GAMEID=0 \
STEAM_LINUX_RUNTIME_VERBOSE=1 \
STEAM_LINUX_RUNTIME_LOG=1 \
umu-run /home/shionn/Games/Beyond\ Good\ and\ Evil/drive_c/GOG\ Games/Beyond\ Good\ and\ Evil/SettingsApplication.exe -opengl -SkipBuildPatchPrereq

#ROTON_ENABLE_NVAPI=1 \
#STEAM_COMPAT_DATA_PATH=/home/shionn/Games/Beyond\ Good\ and\ Evil \
#STEAM_COMPAT_CLIENT_INSTALL_PATH=/home/shionn/.steam/steam \
#STEAM_COMPAT_SHADER_PATH=/home/shionn/Games/Beyond\ Good\ and\ Evil/shadercache \
#STEAM_COMPAT_MOUNTS=/home/shionn/Games/.proton/GE-Proton10-34:/home/shionn/.local/share/umu/steamrt3 \
#DXVK_NVAPI_ALLOW_OTHER_DRIVERS=1 \
#EXE=/home/shionn/.Heroic/Prefixes/default/Beyond\ Good\ and\ Evil/drive_c/GOG\ Games/Beyond\ Good\ and\ Evil/SettingsApplication.exe \


#STEAM_RUNTIME_LIBRARY_PATH=/home/shionn/.Heroic/Prefixes/default/Beyond\ Good\ and\ Evil/drive_c/GOG\ Games/Beyond\ Good\ and\ Evil:/usr/lib:/usr/lib/x86_64-linux-gnu:/usr/lib/i386-linux-gnu \
