# BOSS_Middleware_project 


## Description du Projet
Mise en place d'un jeu de combat joueurs contre 1 boss, le joueurs infligeant des dégats au boss et inverssement, à travers des Abilitées pariculières.

### Objectif

Ce projet Java a pour objectif de mettre en place une infrastructure réseau permettant le développement d'un jeu multi-joueur. Le jeu présente un combat simple de boss contre plusieurs joueurs, chaque joueur représentant 1 thread pour une synchornisation et une distibutivité algorithmique. 
Le but pricipal du projet est concentré sur la gestion des threads, la communication réseau via UDP, la synchronisation, et l'utilisation de RMI (Remote Method Invocation).

### Outils Utilisés

Java 1.17

### Architecture du Projet
Le projet se divise principalement en trois parties clés :

#### Gestion des Threads et Synchronisation :

Un thread principal (mainThread) de l'application.
Threads distincts pour l'envoi et la réception des données via UDP.
Utilisation de moniteurs, RMI, et queues pour assurer la synchronisation entre les différentes parties.
Ensuite, la classe Lobby utilise RMI pour gérer le lobby du jeu.
Synchronisation avec des moniteurs pour garantir une entrée/sortie sécurisée des joueurs dans le lobby.
Enfin, la classe ServerUDP gère l'envoi et la réception de données via le protocole UDP.
Utilisation de CRC32 pour vérifier l'intégrité des données.

### Classes importantes du projet :

1. Gestion du **Lobby avec RMI :
La classe **Lobby** utilise RMI pour gérer le lobby du jeu.
Elle synchronise l'entrée/sortie des joueurs dans le lobby et notifie les threads en attente lorsque le lobby est plein.

2. Communication via UDP avec **ServerUDP** :
La classe **ServerUDP** gère l'envoi et la réception de données via le protocole UDP.
L'intégrité des données est vérifiée à l'aide de CRC32.

3. Noyau du Jeu avec **Core** :
La classe **Core** représente le cœur du jeu, initialisant la communication réseau et gérant la logique du jeu.
Elle utilise des threads pour communiquer avec d'autres joueurs via UDP.

4. La classe **NetworkPlayer**
Encapsule les fonctionnalités réseau, telles que l'hébergement et la connexion à une partie, l'envoi et la réception d'objets via UDP.

## Problèmes Rencontrés
Certaines fonctionnalités n'ont pas été entièrement implémentées en raison de contraintes de temps liées à d'autres projets. 

Ces points comprennent :

- La synchronisation améliorée via l'utilisation du timestamp pour les paquets UDP.
- L'implémentation du temps causale.
- Des problèmes de sérialisation ont été rencontrés et partiellement résolus.

## Apperçu du projet

![Cover](https://github.com/LouAnneSVTR/BOSS_Middleware_project/blob/main/image.png)

Joueurs connectés en local contre 1 boss avec leur barre de vie respecives.

## Comment Utiliser le Projet

### Importation du Projet :
```
git clone https://github.com/LouAnneSVTR/BOSS_Middleware_project.git
```

### Ajouter des fichiers
```
cd existing_repo
git remote add origin https://github.com/LouAnneSVTR/BOSS_Middleware_project
git branch -M main
git push -uf origin main
```

### Lancer le projet 
```
cd existing_repo
bash launch.sh
./launch.sh
```
ou via Intellij, lancer simplement la classe Main.java

## Auteurs

- [@HugoCBNN](https://github.com/faceslog)
- [@LouAnneSVTR](https://github.com/LouAnneSVTR)
- [@SofianeCDL](https://github.com/SofianeCDL)
- [@ValentinGBN](https://github.com/TinkyValou)



### Documentation 
- [RMI](https://alain-defrance.developpez.com/articles/Java/J2SE/micro-rmi/)
