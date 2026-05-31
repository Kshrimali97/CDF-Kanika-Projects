# Acme Product Catalog API Documentation

## Summary

This repository contains basic API documentation for a fictional **Acme Product Catalog API**. The API lets developers retrieve a product by ID and create a new product record.

The documentation uses the **OpenAPI Specification 3.0.3** and is rendered with **Swagger UI**, an open-source API documentation tool that generates interactive documentation from OpenAPI files.

Tool reference: https://github.com/swagger-api/swagger-ui

## Repository Contents

- `openapi.json` - OpenAPI Specification file for the fictional Product Catalog API.
- `docs/index.html` - Swagger UI documentation page that renders `openapi.json`.
- `README.md` - Project summary and viewing instructions.

## Documented Endpoints

### `GET /products/{id}`

Retrieves a single product by ID.

Includes:

- Path parameter: `id`
- Query parameters: `includeReviews`, `currency`
- Response schemas for `200`, `400`, and `404`
- JSON examples for success and error responses

### `POST /products`

Creates a new product record.

Includes:

- Query parameter: `publish`
- JSON request body schema
- Response schemas for `201`, `400`, and `409`
- JSON examples for request and response payloads

## How to View the Rendered Documentation

Because the Swagger UI page loads `openapi.json`, serve the repository with a local static web server instead of opening the HTML file directly.

From the repository root:

```powershell
python -m http.server 8080
```

Then open:

```text
http://localhost:8080/docs/
```

If Python is not installed, use any static file server and open the same `/docs/` path.

## Validation Checklist

- The repository contains a valid OpenAPI JSON file.
- The API includes one GET endpoint and one POST endpoint.
- Both endpoints include descriptions, parameters, schemas, and examples.
- Swagger UI can render the OpenAPI specification from `docs/index.html`.

## Publishing Note

To satisfy an assignment requirement for a repository link, publish this folder to GitHub, GitLab, or another code hosting service and submit the hosted repository URL.
