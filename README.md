# 🏦 Ledger-Java

A simple ledger API implementation built with **Spring Boot** that handles basic financial transactions (deposits, withdrawals), balance tracking, and transaction history.

---

## 📌 Quick Links

- [Project Overview](#-project-overview)
- [Design Decisions](#-design-decisions)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Example API Usage](#-example-api-usage)
- [Implementation Details](#-implementation-details)
- [Testing](#white_check_mark-testing)
- [Assumptions](#-assumptions)
- [Future Improvements](#-future-improvements)

---

## 📌 Project Overview

This project implements a minimal RESTful API for a **ledger system** that supports:

- ✅ Recording money movements (Deposits and Withdrawals)
- ✅ Viewing the current balance
- ✅ Retrieving transaction history

Data is held in-memory using simple Java collections and is lost on application shutdown.

---

## 🏗️ Design Decisions

### 1️⃣ Architecture

- **Layered Architecture** using the principles of clean separation of concerns:
  - `Controller` → handles API requests
  - `Service` → business logic
  - `Model` → internal domain models
  - `DTO` → request and response objects for APIs
  - `Exception` → centralized error handling
- **Interface-based design**: The `LedgerService` interface allows the application to swap storage mechanisms (e.g., database) without changing business logic.

### 2️⃣ Technology Stack

| Component     | Tech                         |
|---------------|------------------------------|
| Language      | Java 17                      |
| Framework     | Spring Boot 3.4.11           |
| Build Tool    | Maven                        |
| Testing       | JUnit 5                      |
| Validation    | Jakarta Bean Validation      |
| Storage       | In-memory via Java Collections |
| API           | REST (no authentication)     |

---

## 📁 Project Structure

```
Ledger-Java/
├── controller/            # REST API Controllers
├── service/               # Business Logic (InMemoryLedgerService)
├── model/                 # Internal Models (Transaction, Balance)
├── dto/                   # API DTOs (TransactionRequest, TransactionResponse)
├── exception/             # Custom exceptions and global error handler
├── test/
│   ├── java/com/example/ledger/
│   │   ├── LedgerControllerTest.java
│   │   ├── InMemoryLedgerServiceTest.java
│   │   └── TestUtils.java
│   └── resources/testdata/ledger/
│       ├── valid_deposit.json
│       ├── valid_withdrawal.json
│       ├── missing_amount.json
│       ├── missing_type.json
│       └── invalid_type.json
├── README.md
└── pom.xml
```

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 17+
- Maven

### 🛠 Installation & Run

```bash
# Clone the repo
git clone https://github.com/thomas-marg/Ledger-Java.git
cd Ledger-Java

# Run the application
./mvnw spring-boot:run
```

📍 Server will start on: `http://localhost:8080`

---

## 🔗 API Endpoints

| Method | Endpoint                         | Description                        |
|--------|----------------------------------|------------------------------------|
| POST   | `/api/ledger/transaction`        | Record a deposit or withdrawal     |
| GET    | `/api/ledger/balance`            | Get current balance                |
| GET    | `/api/ledger/transactionHistory` | Get all recorded transactions      |

---

## 📊 Example API Usage

### 1️⃣ Record a Deposit

```bash
curl -X POST http://localhost:8080/api/ledger/transaction   -H "Content-Type: application/json"   -d '{"amount": 100.00, "type": "DEPOSIT"}'
```

### 2️⃣ Record a Withdrawal

```bash
curl -X POST http://localhost:8080/api/ledger/transaction   -H "Content-Type: application/json"   -d '{"type": "WITHDRAWAL", "amount": 50.00}'
```

### 3️⃣ Get Current Balance

```bash
curl http://localhost:8080/api/ledger/balance
```

### 4️⃣ Get Transaction History

```bash
curl http://localhost:8080/api/ledger/transactionHistory
```

---

## 🛠️ Implementation Details

### 🔐 Validation

- Jakarta Bean Validation (`@NotNull`, `@Valid`) ensures proper input.
- Custom exceptions:
  - `InsufficientBalanceException`
- Enum deserialization issues (like invalid transaction types) are caught using `InvalidFormatException` handler.

### 🧠 Concurrency Handling

- `synchronized` methods in `InMemoryLedgerService` ensure **thread-safety** under concurrent requests.

### 📦 Transaction Design

- Each transaction has:
  - UUID-based `id`
  - `amount`
  - `type` (DEPOSIT or WITHDRAWAL)
  - `timestamp`

---

## ✅ Testing

### 🧪 Run tests

```bash
./mvnw test
```

### Test Structure

- ✅ **Unit tests** using JUnit for:
  - Controller (`LedgerControllerTest`)
  - Service (`InMemoryLedgerServiceTest`)
- ✅ **Test data** for request validation in `/resources/testdata/ledger`
- ✅ **Test utility class** (`TestUtils.java`) to load JSON files

### ✅ What’s covered

| Test Area             | What’s Tested                            |
|-----------------------|------------------------------------------|
| Valid deposit/withdraw | ✅ Creates transaction, updates balance  |
| Invalid inputs        | ✅ Missing type/amount, invalid enum     |
| Insufficient funds    | ✅ Error on invalid withdrawal           |
| Transaction history   | ✅ Returns all past transactions         |


---

## 📌 Assumptions

| Assumption                | Description                          |
|---------------------------|--------------------------------------|
| Storage                  | In-memory only                       |
| Authentication           | None, open API                       |
| Currency                 | Only one "EUR", no conversion logic    |
| Scheduling               | Immediate transactions only          |
| Pagination               | Not supported (all history returned) |

---

## 🔮 Future Improvements

| Area                  | Suggestion                                    |
|-----------------------|-----------------------------------------------|
| Persistence           | Add PostgreSQL or MongoDB backend             |
| Multi-account support | Add user ID or account ID per transaction     |
| Authentication        | Add JWT-based auth                            |
| Swagger Docs          | Generate OpenAPI/Swagger UI                   |
| Pagination            | Add limit/offset to transaction history       |
| Dockerization         | Provide Dockerfile for container deployment   |
| CI/CD                 | Add GitHub Actions to run tests automatically |
| Monitoring            | Add health checks                             |
| Logging               | Enhance logging                               |
