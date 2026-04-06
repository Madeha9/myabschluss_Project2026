# API Documentation - IntelliInvoice

## Overview

This document provides the API specifications for **IntelliInvoice**.
The system allows users to upload invoices, store them securely in **AWS S3**, and automatically extract structured data
using **AI (Anthropic / LangChain4j)**.

## Table of Contents
- [Base URL](#base-url)
- [Live Interactive Docs](#live-interactive-docs)
- [Common Response Formats](#common-response-formats)
- [API Endpoints](#api-endpoints)
- [Tech Stack Mapping](#tech-stack-mapping)

## Base URL

### Development
```http
http://localhost:8080