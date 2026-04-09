# Architecture Overview

**Last Updated:** April 2026
**Version:** 1.0
**Author:** Madeha Mohammed

---

## Introduction

This document describes the high-level architecture of IntelliInvoice —
an AI-powered invoice management system built with Java 21 and Quarkus.
The system allows users to upload invoice images, automatically extract
structured data using Claude Sonnet AI, store images in AWS S3, and
manage invoices through an Angular web interface.

---

## Architecture Goals

| Goal            | How it is achieved                                           |
|-----------------|--------------------------------------------------------------|
| Maintainability | Clean 3-layer architecture — controller, service, repository |
| Testability     | Each layer tested independently — JUnit, Postman, Angular UI |
| Modularity      | Maven multi-module project — backend and frontend separated  |
| Reliability     | Custom ErrorCode enum covers all failure scenarios           |
| Security        | AWS credentials stored only in environment variables         |

---

## Architecture Style

**Layered 3-tier architecture** with strict separation between layers: