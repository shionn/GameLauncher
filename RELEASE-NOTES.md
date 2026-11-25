# GameLauncher — Release Notes 1.1.1

**Date :** 25 novembre 2026
**Version :** 1.1.1

Cette version corrige deux bugs de la release précédente (v1.1.0) : la persistance de l'option `--mangoapp` et le scan des images `.png`.

## Corrections

### Persistance de l'option `--mangoapp`
L'activation de l'option gamescope **MangoHud/MangoApps** (`--mangoapp`) n'était pas sauvegardée dans la configuration du jeu : elle disparaissait au redémarrage du launcher. Le setter `setGamescopeMangoapp()` enregistre désormais l'option dans le fichier `configuration.properties`, de même que les autres options gamescope.

### Scan des images `.png`
Le scanner ne prenait en compte que les images **`.jpg`** ; les images **`.png`** étaient ignorées et donc non listées comme captures d'écran (`instalersImgs`). Les deux extensions sont désormais acceptées.

## Détails techniques

- Persistance : `Game.setGamescopeMangoapp()` appelle maintenant `saveConfiguration()`.
- Scanner : extraction d'un prédicat de filtrage d'extensions testable (`matchByExtensions`) ; `.jpg` et `.png` reconnus, autres extensions rejetées.

---

*Release 1.1.1 — build Maven/JUnit5.*
