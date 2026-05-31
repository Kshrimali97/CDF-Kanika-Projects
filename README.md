# Acme Product Catalog API Documentation

**This task is part of Community Dreams Foundation (CDF) through the CDF Portal / TaskVerse.**

## Summary

This repository contains basic API documentation for a fictional **Acme Product Catalog API**. The API lets developers retrieve a product by ID and create a new product record.

The documentation uses the **OpenAPI Specification 3.0.3** and is rendered with **Swagger UI**, an open-source API documentation tool that generates interactive documentation from OpenAPI files.

Tool reference: https://github.com/swagger-api/swagger-ui

Note: this folder is a local git repository. To provide the assignment deliverable as a repository link, push this repository to GitHub, GitLab, Bitbucket, or another code hosting service and submit that hosted URL.

## Task

**Create Basic API Documentation for a Fictional Service Endpoint**

## Task Description

Clear and comprehensive API documentation is crucial for developers integrating with a product. This task focuses on using an industry standard to document a simple API endpoint.

Required steps:

1. Imagine a simple fictional API service with at least one GET endpoint and one POST endpoint.
2. Find and select an open-source tool or framework for generating API documentation.
3. Define the structure of the API using an OpenAPI Specification YAML or JSON file, including paths, HTTP methods, parameters, request/response schemas, and example values.
4. Ensure the documentation includes clear descriptions for each component.
5. If using a tool that renders the documentation, generate the rendered HTML output or provide instructions on how to view it.

Deliverable:

A link to a code repository containing the OpenAPI Specification file and, if applicable, the generated HTML documentation, posted in the task comment.

## Success Criteria

1. Link to the code repository is provided in the task comment.
2. The repository contains a valid OpenAPI Specification YAML or JSON file.
3. The specification defines at least one GET and one POST endpoint.
4. Each endpoint includes parameters, request/response schemas, and descriptions.
5. If rendered, the documentation is viewable and reflects the specification.

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

`docs/index.html` loads Swagger UI from the public `unpkg.com` CDN, so viewing the rendered page requires internet access. If Python is not installed, use any static file server and open the same `/docs/` path.

## Validation Checklist

- The repository contains an OpenAPI 3.0.3 JSON file.
- The API includes one GET endpoint and one POST endpoint.
- Both endpoints include descriptions, parameters, schemas, and examples.
- Swagger UI can render the OpenAPI specification from `docs/index.html`.

## Publishing Note

To satisfy an assignment requirement for a repository link, publish this folder to GitHub, GitLab, or another code hosting service and submit the hosted repository URL.
