
GAME="River City Girls"

WINEPREFIX="/home/shionn/Games/${GAME}"

WINEPREFIX="${WINEPREFIX}" \
PROTONPATH=/home/shionn/Games/.proton/GE-Proton10-34 \
umu-run winetricks list

mkdir -p "${WINEPREFIX}/drive_c/Steam Games"

tar -xzf "/home/shionn/Téléchargements/Steam/${GAME}/${GAME}.tar.gz" -C "${WINEPREFIX}/drive_c/Steam Games"

cd /home/shionn/Games/River\ City\ Girls/drive_c/Steam\ Games/River\ City\ Girls
pwd

GAMEID="umu-1049320" \
STORE="steam" \
WINEPREFIX="${WINEPREFIX}" \
PROTONPATH=/home/shionn/Games/.proton/GE-Proton10-34 \
umu-run RiverCityGirls.exe


# a tester avec river city girl