# 🏥 Projet Stage — Système d'approvisionnement Pharmaceutique

Application web multi-modules dédiée à la gestion des stocks, commandes et approvisionnement dans le secteur pharmaceutique. Elle repose sur un backend Spring Boot et plusieurs frontends Angular indépendants.

---

## 🗂️ Structure du projet

```
projet_stage/
├── backend-spring/          # API REST — Spring Boot (Java 17)
├── grossiste/               # Frontend Angular — Interface grossiste
├── grossiste 2/             # Variante frontend grossiste
├── grossiste 3/             # Variante frontend grossiste
└── pharmacie/               # Frontend Angular — Interface pharmacie
```

### Modules Angular (dans `pharmacie/src/app/`)

| Module | Description |
|---|---|
| `chatbot` | Assistant conversationnel intégré |
| `commande` | Gestion des commandes |
| `dashboard` | Tableau de bord analytique |
| `footer` / `navbar` | Composants de navigation |
| `grossistes` | Gestion des grossistes |
| `interceptors` | Intercepteurs HTTP (auth, erreurs) |
| `login-pharmacie` | Authentification pharmacie |
| `notifications` | Système de notifications |
| `services` | Services partagés Angular |
| `stock` | Suivi des stocks |

---

## 🛠️ Stack technique

| Couche | Technologie |
|---|---|
| Backend | Spring Boot 3.x · Java 17 · Maven |
| Frontend | Angular 17 · TypeScript |
| Base de données | MySQL |
| Sécurité | JWT (JSON Web Tokens) |

---

## 🚀 Lancement en local

### Backend

```bash
cd backend-spring
mvn -f pom.xml clean package
mvn -f backend-spring spring-boot:run
```

L'API sera disponible sur `http://localhost:8080`.

### Frontend (exemple avec `grossiste`)

```bash
cd grossiste
npm ci
npx ng serve --port 4300 --host 0.0.0.0
```

Accès via `http://localhost:4300`.

> Pour `pharmacie`, `grossiste 2`, `grossiste 3` ou `lotfi`, répliquer les mêmes commandes depuis leur répertoire respectif en adaptant le port.

---

## ⚙️ Prérequis

- Java 17+
- Maven 3.6+
- Node.js 18+ et npm
- Angular CLI : `npm install -g @angular/cli`
- MySQL (instance locale ou distante)

---

## 🔧 Configuration

Avant de lancer le backend, configurer les paramètres de connexion à la base de données dans :

```
backend-spring/src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nom_de_la_base
spring.datasource.username=votre_utilisateur
spring.datasource.password=votre_mot_de_passe
```

> ⚠️ Ne jamais committer ce fichier avec des identifiants réels. Utiliser des variables d'environnement ou un fichier `.env` ignoré par Git.

---

## 📌 Notes

- Les dossiers `target/` et `node_modules/` ne doivent pas être versionnés (ajouter au `.gitignore`).
- Les trois variantes de frontend (`grossiste`, `grossiste 2`, `grossiste 3`) sont à consolider ou documenter selon leur rôle distinct.
- Aucun pipeline CI/CD n'est encore configuré.

---

## 👤 Auteur

Projet réalisé dans le cadre d'un stage en développement web full-stack.
