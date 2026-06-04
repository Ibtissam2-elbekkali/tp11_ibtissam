```markdown
# TP11 — Géolocalisation Android & PHP
**Réalisé par : Ibtissam**

##  Description
Application Android de géolocalisation en temps réel.
L'app récupère la position GPS de l'utilisateur et l'envoie vers un serveur PHP qui la stocke en base de données.
Un dashboard web permet de visualiser toutes les positions enregistrées.

##  Technologies utilisées
- **Frontend** : Android (Java)
- **Backend** : PHP (architecture DAO/Service)
- **Base de données** : MySQL/SQLite
- **Communication** : HTTP (POST JSON)

##  Structure du projet

TP11_ibtissam/
├── frontend/   → Application Android
└── backend/    → Serveur PHP + Dashboard web

## ⚙️ Fonctionnalités
- Récupération de la position GPS (latitude, longitude, altitude)
- Envoi de la position au serveur via HTTP
- Stockage sécurisé en local (SharedPreferences chiffrées)
- Dashboard web pour visualiser les positions
- Gestion des permissions de localisation

```
