# 🛡️ Sistema de Detecção de Fraude em Cartões de Crédito / Credit Card Fraud Detection System

## 📌 PT-BR – Descrição do Projeto

Este repositório contém um sistema desenvolvido em **Java com Spring Boot** para **detecção de fraudes em transações com cartão de crédito**. O sistema permite o cadastro de usuários e cartões, e registra transações, aplicando regras de negócio para detectar atividades suspeitas.

Optamos por uma **arquitetura monolítica**, com pacotes estruturados em português, e foco em segurança, organização e boas práticas de desenvolvimento.

---

### 🚀 Tecnologias Utilizadas
- Java 21+
- Spring Boot
- Spring Data JPA
- Spring Security (com BCrypt)
- H2 Database (ou outro banco relacional)
- Maven
- Postman (para testes)

---

### 🧱 Estrutura do Projeto
- `entidade/` → Classes que representam as tabelas do banco (Usuário, Cartão, Transação)
- `repositorio/` → Interfaces que acessam o banco via JPA
- `servico/` → Regras de negócio (cadastro, verificação de duplicidade, etc.)
- `controle/` → Camada REST API
- `dto/` → Objetos de transporte de dados
- `config/` → Segurança e criptografia com BCrypt

---

### 🔐 Funcionalidades
- Cadastro de usuários com criptografia de senha
- Prevenção de e-mails duplicados
- Cadastro de cartões e transações
- Exposição de endpoint via API REST
- Estrutura extensível para validações futuras de fraude

------------------------------------------------------------------------------------------------------------------------

# EN - USA
This repository contains a Spring Boot-based system built in Java for detecting fraud in credit card transactions. It includes user registration, card and transaction management, and business rules to flag suspicious activities.

We opted for a monolithic architecture, structured the codebase in Portuguese, and focused on clean design, security, and maintainability.

🚀 Technologies Used

Java 17+

Spring Boot

Spring Data JPA

Spring Security (with BCrypt encryption)

H2 Database (or any SQL-based DB)

Maven

Postman (for API testing)

🧱 Project Structure

entidade/ → Entity classes (User, Card, Transaction)

repositorio/ → JPA repositories

servico/ → Business logic layer

controle/ → REST controller (API)

dto/ → Data transfer objects

config/ → Security configurations (BCrypt)

🔐 Features

User registration with password encryption

Email duplication prevention

Card and transaction registration

REST API for external integration

Modular structure for future fraud validation logic
