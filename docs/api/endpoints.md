# API Documentation

## Overview

This document provides comprehensive documentation for the API endpoints of the train_2025_java_aufbau_example_project.

## Table of Contents

- [Base URL](#base-url)
- [Authentication](#authentication)
- [Common Response Formats](#common-response-formats)
- [Error Handling](#error-handling)
- [API Endpoints](#api-endpoints)
- [Examples](#examples)

## Base URL

### Development
```
http://localhost:8080/api
```

### Production
```
https://api.example.com
```

## Authentication

### Authentication Method

This API uses [specify authentication method: JWT, API Key, OAuth, or None].

#### Using JWT (Example)

Include the JWT token in the Authorization header:

```http
Authorization: Bearer <your_jwt_token>
```

#### Obtaining a Token

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600,
  "tokenType": "Bearer"
}
```

## Common Response Formats

### Success Response

```json
{
  "status": "success",
  "data": {
    // Response data
  },
  "message": "Operation completed successfully"
}
```

### Error Response

```json
{
  "status": "error",
  "error": {
    "code": "ERROR_CODE",
    "message": "Human-readable error message",
    "details": []
  }
}
```

## Error Handling

### HTTP Status Codes

| Status Code | Meaning |
|------------|---------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 204 | No Content - Request successful, no content to return |
| 400 | Bad Request - Invalid request parameters |
| 401 | Unauthorized - Authentication required |
| 403 | Forbidden - Insufficient permissions |
| 404 | Not Found - Resource not found |
| 409 | Conflict - Resource conflict (e.g., duplicate) |
| 422 | Unprocessable Entity - Validation error |
| 500 | Internal Server Error - Server error |
| 503 | Service Unavailable - Service temporarily unavailable |

### Error Codes

| Error Code | Description |
|-----------|-------------|
| INVALID_REQUEST | Request validation failed |
| RESOURCE_NOT_FOUND | Requested resource does not exist |
| DUPLICATE_RESOURCE | Resource already exists |
| UNAUTHORIZED | Authentication failed |
| FORBIDDEN | Access denied |
| INTERNAL_ERROR | Internal server error |

## API Endpoints

### Health Check

#### Check API Status

```http
GET /api/health
```

**Response:**
```json
{
  "status": "healthy",
  "timestamp": "2026-01-20T18:45:00Z",
  "version": "1.0.0"
}
```

---

### User Management

#### Get All Users

```http
GET /api/users
```

**Query Parameters:**
- `page` (optional, default: 0) - Page number
- `size` (optional, default: 20) - Page size
- `sort` (optional) - Sort field and direction (e.g., `name,asc`)

**Response:**
```json
{
  "status": "success",
  "data": {
    "users": [
      {
        "id": 1,
        "username": "john_doe",
        "email": "john@example.com",
        "firstName": "John",
        "lastName": "Doe",
        "createdAt": "2026-01-15T10:30:00Z"
      }
    ],
    "pagination": {
      "page": 0,
      "size": 20,
      "totalElements": 100,
      "totalPages": 5
    }
  }
}
```

#### Get User by ID

```http
GET /api/users/{id}
```

**Path Parameters:**
- `id` (required) - User ID

**Response:**
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "createdAt": "2026-01-15T10:30:00Z",
    "updatedAt": "2026-01-20T14:20:00Z"
  }
}
```

**Error Response (404):**
```json
{
  "status": "error",
  "error": {
    "code": "RESOURCE_NOT_FOUND",
    "message": "User with ID 1 not found"
  }
}
```

#### Create New User

```http
POST /api/users
Content-Type: application/json
```

**Request Body:**
```json
{
  "username": "jane_doe",
  "email": "jane@example.com",
  "password": "SecurePassword123!",
  "firstName": "Jane",
  "lastName": "Doe"
}
```

**Response (201):**
```json
{
  "status": "success",
  "data": {
    "id": 2,
    "username": "jane_doe",
    "email": "jane@example.com",
    "firstName": "Jane",
    "lastName": "Doe",
    "createdAt": "2026-01-20T18:45:00Z"
  },
  "message": "User created successfully"
}
```

**Validation Errors (422):**
```json
{
  "status": "error",
  "error": {
    "code": "INVALID_REQUEST",
    "message": "Validation failed",
    "details": [
      {
        "field": "email",
        "message": "Email is already in use"
      },
      {
        "field": "password",
        "message": "Password must be at least 8 characters"
      }
    ]
  }
}
```

#### Update User

```http
PUT /api/users/{id}
Content-Type: application/json
```

**Path Parameters:**
- `id` (required) - User ID

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response:**
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "username": "john_doe",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "updatedAt": "2026-01-20T18:50:00Z"
  },
  "message": "User updated successfully"
}
```

#### Delete User

```http
DELETE /api/users/{id}
```

**Path Parameters:**
- `id` (required) - User ID

**Response (204):**
```
No Content
```

---

### Example Resource (Template)

Replace with your actual resources. Below is a template for documenting additional endpoints.

#### Get All Resources

```http
GET /api/resources
```

**Query Parameters:**
- `param1` (optional) - Description
- `param2` (optional) - Description

**Response:**
```json
{
  "status": "success",
  "data": {
    "resources": []
  }
}
```

## Examples

### cURL Examples

#### Get All Users

```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json"
```

#### Create User

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "new_user",
    "email": "user@example.com",
    "password": "SecurePass123!",
    "firstName": "New",
    "lastName": "User"
  }'
```

#### Update User

```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "updated@example.com",
    "firstName": "Updated",
    "lastName": "Name"
  }'
```

#### Delete User

```bash
curl -X DELETE http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### JavaScript (Fetch API) Examples

#### Get All Users

```javascript
fetch('http://localhost:8080/api/users', {
  method: 'GET',
  headers: {
    'Authorization': 'Bearer YOUR_TOKEN',
    'Content-Type': 'application/json'
  }
})
.then(response => response.json())
.then(data => console.log(data))
.catch(error => console.error('Error:', error));
```

#### Create User

```javascript
fetch('http://localhost:8080/api/users', {
  method: 'POST',
  headers: {
    'Authorization': 'Bearer YOUR_TOKEN',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    username: 'new_user',
    email: 'user@example.com',
    password: 'SecurePass123!',
    firstName: 'New',
    lastName: 'User'
  })
})
.then(response => response.json())
.then(data => console.log(data))
.catch(error => console.error('Error:', error));
```

## Rate Limiting

- **Limit:** 100 requests per minute per IP address
- **Headers:**
  - `X-RateLimit-Limit`: Maximum requests allowed
  - `X-RateLimit-Remaining`: Remaining requests
  - `X-RateLimit-Reset`: Timestamp when limit resets

**Rate Limit Exceeded Response (429):**
```json
{
  "status": "error",
  "error": {
    "code": "RATE_LIMIT_EXCEEDED",
    "message": "Too many requests. Please try again later.",
    "retryAfter": 60
  }
}
```

## Pagination

List endpoints support pagination using query parameters:

- `page` - Page number (0-indexed)
- `size` - Number of items per page
- `sort` - Sort field and direction (e.g., `name,asc` or `createdAt,desc`)

**Example:**
```
GET /api/users?page=2&size=50&sort=createdAt,desc
```

## Versioning

This API uses URL versioning. The current version is `v1`:

```
http://localhost:8080/api/v1/users
```

When breaking changes are introduced, a new version will be created (e.g., `v2`).

## Support

For API support and questions:

- Review the [User Guide](../user-guide.md)
- Check the [Setup Guide](../setup.md)
- Consult the [Architecture Documentation](../architecture/overview.md)
- Submit an issue on the project repository

## Changelog

### Version 1.0.0 (January 2026)
- Initial API release
- User management endpoints
- Authentication support

---

**Last Updated:** January 2026  
**API Version:** 1.0.0
