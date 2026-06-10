# 💊 PharmStock — Pharmaceutical Supply Management System

PharmSupply is a full-stack web application that digitizes the supply chain between **pharmacies** and **wholesalers** (grossistes), enabling seamless order management, real-time stock tracking, and AI-assisted support.

---

## ✨ Key Features

- 🛒 **Order Management** — pharmacies place and track orders from wholesalers
- 📦 **Stock Tracking** — real-time inventory monitoring on both sides
- 📊 **Analytics Dashboard** — key supply metrics and order history
- 🤖 **Chatbot Assistant** — intelligent support for pharmacy users
- 🔔 **Notifications** — real-time alerts for order status updates
- 🔐 **Secure System** — JWT authentication + protected APIs

---

## 🛠️ Tech Stack

| Layer    | Technology                        |
|----------|-----------------------------------|
| Frontend | Angular 17 · TypeScript           |
| Backend  | Spring Boot 3 · Java 17 · Maven   |
| Database | MySQL                             |
| Security | JWT Authentication                |

---

## 🏗️ Architecture

- RESTful API (modular Spring Boot backend)
- Angular component-based UI (two independent frontends)
- MySQL relational schema
- JWT-based stateless authentication
- Role-based access: Pharmacy / Wholesaler

---

## 🚀 Quick Start

```bash
git clone <repo>
cd PharmSupply
```

**Backend**
```bash
cd backend-spring
mvn clean package
mvn spring-boot:run
```

**Frontend — Pharmacy**
```bash
cd pharmacie
npm ci && ng serve --port 4200
```

**Frontend — Wholesaler**
```bash
cd grossiste
npm ci && ng serve --port 4300
```

| Interface  | URL                       |
|------------|---------------------------|
| Pharmacy   | http://localhost:4200     |
| Wholesaler | http://localhost:4300     |
| API        | http://localhost:8080/api |

---

## ⚙️ Configuration

In `backend-spring/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pharmsupply
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your_jwt_secret
```

> ⚠️ Never commit credentials. Use environment variables in production.

---

## 🎯 Impact

PharmSupply demonstrates:

- Full-stack engineering with Spring Boot + Angular
- Clean role-based multi-frontend architecture
- Real-world supply chain digitization in healthcare
- Secure and scalable REST API design

---

## 🔮 Future Work

- 🐳 Docker Compose full-stack setup
- 📱 Mobile app for pharmacists
- 📈 Predictive stock shortage alerts
- 🔌 Integration with national drug databases
