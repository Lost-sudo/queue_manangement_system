# Queue Management System — School Project

A web-based queue management system for school offices (Registrar, Cashier, etc.).  
Students join queues virtually via a website, staff call the next person from their dashboard, and a public display shows the current serving status in real time.  
Built as a complete software-only solution without physical kiosks or ticket printers.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Planning Documents](#project-planning-documents)
- [System Architecture](#system-architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
- [Running the Application](#running-the-application)
- [Usage Guide](#usage-guide)
- [API Endpoints](#api-endpoints)
- [Real-time Updates](#real-time-updates)
- [Future Enhancements](#future-enhancements)
- [Contributors](#contributors)

---

## Features

- **Student Portal**:  
  - Select an office and service.  
  - Join the queue and receive a ticket number with estimated waiting time.  
  - Track ticket status in real time.  
  - Receive browser notifications when it's almost your turn.

- **Staff Dashboard**:  
  - Secure login for counter staff.  
  - View the queue waiting list for the assigned counter.  
  - Call the next ticket, mark as done or no‑show.

- **Public Display**:  
  - A dedicated webpage (e.g., for a large monitor) that shows the currently serving ticket and counter, updating automatically.

- **Admin Panel**:  
  - Manage offices, services, counters, and user accounts.  
  - Real‑time monitoring dashboard for all queues.  
  - Generate reports (daily/weekly) with export capability.

---

## Tech Stack

| Layer       | Technology                                   |
|-------------|----------------------------------------------|
| **Backend** | Java 17+ / Spring Boot 3.x                  |
|             | Spring Data JPA (Hibernate)                 |
|             | Spring Web (REST)                           |
|             | Spring WebSocket (raw WebSocket)            |
|             | Spring Security + JWT (jjwt)                |
| **Database**| PostgreSQL 14+                              |
| **Frontend**| Next.js 14 (App Router) + TypeScript        |
|             | Tailwind CSS (optional)                     |
| **Real-time** | Native WebSocket (no Socket.IO)           |

---

## Project Planning Documents

All design artifacts were created before writing any code.  
They are available in the `/docs` folder (or referenced as separate files):

1. **User Stories** – All user roles, their goals, and acceptance criteria.  
   *File: `docs/user-stories.md`*

2. **Workflow Diagrams** – Visual flows for joining a queue, calling a ticket, completing a ticket, and admin configuration.  
   *File: `docs/workflow-diagrams.md` (including Mermaid code)*

3. **Data Model (ER Diagram)** – Database schema with entities and relationships.  
   *File: `docs/data-model.md`*

4. **API Specification** – Full REST API and WebSocket events contract.  
   *File: `docs/api-specification.md`*

5. **Wireframes / UI Sketches** – Low‑fidelity layouts for every screen.  
   *File: `docs/wireframes.md`*

---

## System Architecture

```mermaid
graph LR
    subgraph Frontend
        A[Student Next.js pages]
        B[Staff Dashboard]
        C[Admin Panel]
        D[Public Display]
    end

    subgraph Backend
        E[Spring Boot REST API]
        F[Spring Boot WebSocket Server]
        G[JWT Authentication]
    end

    H[(PostgreSQL)]

    A -->|HTTP REST| E
    B -->|HTTP REST| E
    C -->|HTTP REST| E
    D -->|HTTP REST| E
    A -->|WebSocket| F
    B -->|WebSocket| F
    C -->|WebSocket| F
    D -->|WebSocket| F
    E --> H
    F --> H