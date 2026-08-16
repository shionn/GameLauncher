
# GameLauncher

Ce projet permet d'installer et de lancer des jeux windows depuis le backup des installeurs fourni par GoG. Ce projet fonctionne grâce à umu. 
Il est conseillé également d'avoir steam d'installer.

![Liste des jeux](docs/game-list.png)
![Installation](docs/installation.png)
![Options](docs/options.png)



# Configuration

## Fichier d'installation

Les fichiers d'instalation doivent être organiser comme suit : 

~~~
/<configuration.propertie:folder.instalers>
|--A
|  |--Alan Wake
|  |  |-- setup<...>.exe et setup<...>.bin d'installation
|  |  |-- un .jpg (necessaire pour l'affichage)
|  |  |-- umu-<gameid>.gameid pour les protonfixes
|  |  |-- gog.store (gog/steam/abandonware/egs)
|  |  |-- windows.platform (windows/linux)
~~~

## Platoforme

### windows.platform

Par défaut un jeu est concidérer comme etant originelement pour la plateforme `Windows`. 
Mais vous pouvez le précisez en ajoutant un fichier `windows.platform` dans le dossier source. 

### linux.platform

Pour faire reconnaitre un jeu pour la platform `Linux` vous devez ajouter un fichier `linux.platform` dans le dossier source du jeu. Cependant, actuellement le launcher ne supporte pas encore ces jeux.

## Store

Vous devez ajouter un des troix fichiers suivant en fonction de la plateforme d'origine. 

### gog.store

Indique que le jeu provien de la plateforme GoG. 


L'insatllation requier un fichier 

Pour trouver un protonfixe :  [umu-database](https://github.com/Open-Wine-Components/umu-database) et [umu-protonfixes](https://github.com/Open-Wine-Components/umu-protonfixes)


# TODO

## Epic :
 https://www.pcgamingwiki.com/wiki/List_of_DRM-free_games_on_Epic_Games_Store


# Truc de dev pour note

steam steam://install/4183110
steam steam://install/1628350
steam steam://install/1391110
steam steam://install/1070560


https://www.gaminglinux.fr/wine-proton-et-umu-executer-des-jeux-windows-sur-linux/

https://github.com/Open-Wine-Components/umu-launcher/blob/main/docs/umu.1.scd

https://github.com/10xJSChad/proton-standalone

https://github.com/Sinfolke/Proton-outside-steam


à backuper : 
- /home/shionn/.local/share/umu/steamrt3 (1/2/4)
- /home/shionn/.cache/winetricks/
- /home/shionn/.config/protonfixes ??

