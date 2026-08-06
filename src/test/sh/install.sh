#!/bin/sh

PREFIX_PATH=/home/shionn/projects/GameLauncher/Prefix
PROTON_PATH=/home/shionn/.local/proton/GE-Proton10-34
STEAM_HOME_DIR=~/.local/share/Steam

#PROTON_COMPAT_DATA_PATH=/home/shionn/projects/GameLauncher/proton/compatdata
#WINEPREFIX=${PREFIX_PATH} WINEARCH=win64 wineboot -u
#mkdir -p ${PROTON_COMPAT_DATA_PATH}

mkdir -p ${PREFIX_PATH}

#export STEAM_COMPAT_CLIENT_INSTALL_PATH=$STEAM_HOME_DIR

export LD_LIBRARY_PATH="$LD_LIBRARY_PATH:\
$STEAM_HOME_DIR/ubuntu12_32/steam-runtime/pinned_libs_32:\
$STEAM_HOME_DIR/ubuntu12_32/steam-runtime/pinned_libs_64:\
/usr/lib64/qt-3.3/lib:/usr/lib64/tcl8.6:/usr/lib/wine:/usr/lib64/wine:/lib:/lib64:\
$STEAM_HOME_DIR/ubuntu12_32/steam-runtime/lib/i386-linux-gnu:\
$STEAM_HOME_DIR/ubuntu12_32/steam-runtime/usr/lib/i386-linux-gnu:\
$STEAM_HOME_DIR/ubuntu12_32/steam-runtime/lib/x86_64-linux-gnu:\
$STEAM_HOME_DIR/ubuntu12_32/steam-runtime/usr/lib/x86_64-linux-gnu:\
$STEAM_HOME_DIR/ubuntu12_32/steam-runtime/lib:\
$STEAM_HOME_DIR/ubuntu12_32/steam-runtime/usr/lib:"

export SDL_GAMECONTROLLERCONFIG="03000000de280000ff11000001000000,Steam Virtual Gamepad,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,03000000de280000fc11000001000000,Steam Controller,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,030000005e040000a102000007010000,X360 Wireless Controller,a:b0,b:b1,back:b6,dpdown:b14,dpleft:b11,dpright:b12,dpup:b13,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,0000000058626f782047616d65706100,XInput Controller,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,030000005e0400008e02000010010000,X360 Controller,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b8,leftshoulder:b4,leftstick:b9,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b10,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,"
export SDL_GAMECONTROLLER_ALLOW_STEAM_VIRTUAL_GAMEPAD="1"
export SDL_GAMECONTROLLER_USE_BUTTON_LABELS="1"
export SDL_VIDEO_X11_DGAMOUSE="0"


STEAM_COMPAT_CLIENT_INSTALL_PATH=~/.local/share/Steam \
STEAM_COMPAT_DATA_PATH=${PREFIX_PATH} \
$PROTON_PATH/proton run "/home/shionn/projects/GameLauncher/The Gunk/setup_the_gunk_1014.1.6.0_(74937).exe"
# "-d3d12 -ResX=1440 -ResY=900 -FullscreenMode=1 -Fullscreen"

#WINEPREFIX=${PREFIX_PATH} \
