# BOSS_Middleware_project 


## Description du Projet
Ce projet Java 1.17 a pour objectif de mettre en place une infrastructure réseau permettant le développement d'un jeu multi-joueur. L'accent est mis sur la gestion des threads, la communication réseau via UDP, la synchronisation, et l'utilisation de RMI (Remote Method Invocation).

### Objectif

### Outils Utilisés

### Architecture du Projet
Le projet se divise principalement en trois parties clés :

#### Gestion des Threads et Synchronisation :

Un thread principal (mainThread) de l'application.
Threads distincts pour l'envoi et la réception des données via UDP.
Utilisation de moniteurs, RMI, et queues pour assurer la synchronisation entre les différentes parties.
Implémentation RMI avec Lobby :

La classe Lobby utilise RMI pour gérer le lobby du jeu.
Synchronisation avec des moniteurs pour garantir une entrée/sortie sécurisée des joueurs dans le lobby.
Communication via UDP :

La classe ServerUDP gère l'envoi et la réception de données via le protocole UDP.
Utilisation de CRC32 pour vérifier l'intégrité des données.

### Quelques classes importantes du projet incluent :

- **Lobby** : Gestion du lobby en utilisant RMI.
- **ServerUDP** : Gestion de la communication via UDP.
- **Core** : Noyau du jeu qui initialise la communication réseau et gère la logique du jeu.


## Comment Utiliser le Projet

Importation du Projet:


## Comment ajouter des fichiers

```
cd existing_repo
git remote add origin https://github.com/LouAnneSVTR/BOSS_Middleware_project
git branch -M main
git push -uf origin main
```

## Auteurs

- [@HugoCBNN](https://github.com/faceslog)
- [@LouAnneSVTR](https://github.com/LouAnneSVTR)
- [@SofianeCDL](https://github.com/SofianeCDL)
- [@ValentinGBN](https://github.com/TinkyValou)


## Ressources Additionnelles

### Documentation 
- [@RMI](https://alain-defrance.developpez.com/articles/Java/J2SE/micro-rmi/)
