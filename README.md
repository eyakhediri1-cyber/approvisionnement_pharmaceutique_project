# Approvisionnement Pharmaceutique — Spring Boot + Angular

Plateforme de gestion du stock et des commandes pour pharmacies et distributeurs (grossistes).

## 🏗️ Architecture

- **Frontends** : 4 applications Angular (Pharmacie, Grossiste 1/2/3)
- **Backend** : Spring Boot 3.2 (API REST JWT-sécurisée)
- **BD** : MySQL 8.0+

## 🚀 Lancement rapide

### Prérequis
- Java 17+, Node.js 18+, Maven 3.6+, MySQL 8.0+

### 1. MySQL

```bash
mysql -u root
CREATE USER IF NOT EXISTS 'dsi23admin'@'localhost' IDENTIFIED BY 'dsi23admin';
GRANT ALL PRIVILEGES ON *.* TO 'dsi23admin'@'localhost';
EXIT;
```

### 2. Frontends (4 terminaux)

```bash
cd grossiste && npm install && npx ng serve --port 4300 --host 0.0.0.0
cd 'grossiste 2' && npm install && npx ng serve --port 4301 --host 0.0.0.0
cd 'grossiste 3' && npm install && npx ng serve --port 4302 --host 0.0.0.0
cd pharmacie && npm install && npx ng serve --port 4303 --host 0.0.0.0
```

### 3. Backend

```bash
cd backend-spring
mvn -DskipTests -Dmaven.test.skip=true spring-boot:run
```

## 📍 URLs

| App | URL |
|---|---|
| **Grossiste 1** | http://localhost:4300 |
| **Grossiste 2** | http://localhost:4301 |
| **Grossiste 3** | http://localhost:4302 |
| **Pharmacie** | http://localhost:4303 |
| **Backend** | http://localhost:8080 |
| **Swagger** | http://localhost:8080/swagger-ui.html |

## 🔐 Credentials

**MySQL:** `dsi23admin` / `dsi23admin`

**Test Pharmacie:** `dev_test@local` / `Test1234`

## 📊 Endpoints Clés

| Endpoint | Méthode | Auth |
|---|---|---|
| `/api/medicaments` | GET | ✅ JWT |
| `/api/commandes` | GET | ✅ JWT |
| `/api/grossiste/produits` | GET | ❌ Public |
| `/api/auth/login` | POST | ❌ Public |
| `/api/auth/login-grossiste` | POST | ❌ Public |

## 📝 DB

- `pharmacie_db` — Pharmacie (tables : medicaments, commandes, users)
- `grossiste_db*` — Distributeurs (3 bases)

Données d'exemple pré-insérées.