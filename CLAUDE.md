# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ULMS (统一登录管理平台, "A6广场") — a unified login management platform with employee management, course/exam systems, HR tools (mood tracking, punch cards), OA workflow, and a community forum ("A6 Voice").

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.5.7, MyBatis 3.0.5, Oracle (Druid connection pool), Redis (Lettuce), Sa-Token (session/auth), WebSocket
- **Frontend**: Vue 3 (Composition API), Vite 6, TDesign Vue Next 1.20, Pinia 3, Vue Router 4, ECharts 6, Axios
- **Build**: Maven (backend), Vite (frontend)

## Commands

### Backend (from `ulms/backend/`)
```bash
# Build
mvn clean package -DskipTests

# Run tests
mvn test

# Run single test class
mvn test -Dtest=ClassName

# Run with dev profile
mvn spring-boot:run -Dspring-boot.profiles.active=dev
```

### Frontend (from `ulms/frontend/`)
```bash
npm run dev      # Vite dev server (development mode)
npm run uat      # Vite dev server (UAT mode)
npm run prod     # Vite dev server (production mode)
npm run build:dev  # Build for development
npm run build:uat  # Build for UAT
npm run build:prod # Build for production
```

The frontend dev server runs on the port Vite assigns (default 5173). The backend runs on port 8080 with context path `/ulms`. When running both locally, the frontend proxies to `http://localhost:8080/ulms` in dev mode.

## Architecture

### Backend (`ulms/backend/`)

Standard Spring Boot layered architecture organized by business domain modules:

- **`sys/`** — Core system: login, user/menu/role management, departments, dictionary data, operation logs, ads, calendars
- **`college/`** — College training: courses, exams, sign-in, evaluation templates, study progress, teacher/group management
- **`edu/`** — Education: question banks, exam papers, daily practice groups, book management, score tracking
- **`oht/`** — Off-hour tools: status tracking, quick memos, cases/tasks, login logs
- **`hr/`** — HR: mood tracking/configuration, punch card journals
- **`flow/`** — Workflow/approval processes
- **`helper/`** — Helper/assistant features
- **`youngTalk/`** — Community forum ("Young Talk"/A6 Voice): posts, projects, surveys
- **`rpa/`** — RPA (Robotic Process Automation) integration
- **`websocket/`** — WebSocket endpoints for real-time messaging
- **`db/`** — Database table/tree metadata management
- **`common/`** — Cross-cutting: annotations (`@MyLog`), AOP, config (CORS, Druid, Sa-Token, Redis, WebSocket, scheduled tasks), base controller (`DataCache`), exception handling, utility classes

Each business module follows the same pattern: `controller/` → `service/` (interface + `Impl/`) → `dao/` (MyBatis mapper interfaces) → `domain/` (POJOs). MyBatis XML mappers live in `src/main/resources/mapper/<module>/`.

**Key backend details:**
- Authentication via **Sa-Token** (token-based, reads from header/body/cookie). Token name: `authorization`.
- CORS allows `localhost:*`, `127.0.0.1:*`, internal network IPs (`10.47.81.*`, `192.168.*`), and specific production hosts.
- File upload path configured per profile via `ulms.uploadPath`.
- Password encryption uses AES for transport, then a custom `Util.getsecret()`/`Util.getdesecret()` for storage.
- Circular dependencies are temporarily allowed (`spring.main.allow-circular-references: true`). New code should avoid introducing more.

### Frontend (`ulms/frontend/`)

- **`src/views/`** — Page components, organized by domain (`admin/`, `dashboard/`, `font/a6voice/`, `login/`, `exception/`)
- **`src/router/`** — Vue Router with `createWebHistory`. `routers/constants.js` defines static routes; dynamic routes are loaded from backend menu API at login time
- **`src/stores/`** — Pinia stores: `user`, `app`, `menu`, `dict`, `oht`, `ws`
- **`src/api/`** — Axios-based API modules mirroring backend domains (`auth.js`, `system/`, `college/`, `edu/`, `oht/`, `hr/`, etc.)
- **`src/layouts/`** — App shell: header, sider (sidebar menu), breadcrumb, main page area
- **`src/components/`** — Shared/reusable components
- **`src/utils/`** — `request.js` (Axios instance with interceptors, retry, auth header injection), `auth.js` (token helpers), `cryptoService.js`

**Key frontend details:**
- `@` alias maps to `src/`.
- Auto-imports: Vue APIs (`ref`, `reactive`, etc.) and TDesign Vue Next components are auto-imported at build time via `unplugin-auto-import` and `unplugin-vue-components`.
- The router guard in `router/index.js` fetches user info and menu tree on first navigation, then dynamically adds child routes. It handles token expiry and redirect-on-404.
- `base` path in Vite config is `/ulms/` — matches the backend context path.

### CustomTable wrapper (migration bridge)

TDesign Vue Next v1.20 Table reads columns **exclusively** from the `:columns` prop (Array of column definition objects). Unlike Element Plus where `<el-table-column>` children are live components that register with their parent table, TDesign's `TableColumn` is not a standalone component — `t-table-column` does not resolve and produces empty tables.

**Solution**: `CustomTable.vue` wraps TDesign's Table with slot-based column parsing:
- `<CustomTable>` replaces `<t-table>` (passes through all Table props/events)
- `<TableColumn>` replaces `<t-table-column>` (a stub component that only defines column props)
- CustomTable parses `<TableColumn>` VNodes from its default slot, extracts column definitions and slot render functions, then passes them to TDesign's Table via `:columns` and dynamic named slots
- Column cell templates (e.g. `<template #default="scope">`) are forwarded as TDesign cell slots keyed by `colKey`
- Props are normalized: `prop` → `colKey`, `label` → `title` for Element Plus compatibility
- Exposed methods: `clearSelection()`, `getData(key)` forward to the inner Table instance

**Important**: Always use `<CustomTable>` + `<TableColumn>` instead of `<t-table>` + `<t-table-column>`. TableColumn does not need importing when used in templates — it is globally registered in `main.js`.

### Environments

Profiles: `dev` (local), `uat` (testing), `prod` (production). The default active profile in `application.yml` is `uat` — change to `dev` for local development.

The `applog/` directory at the project root contains log files (debug, error, info, warn).

## Database

Oracle database with schema `trmusr`. PageHelper is configured for Oracle dialect. Table/column naming uses underscores; MyBatis auto-maps to camelCase Java fields.
