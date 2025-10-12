# 2. Monolithic Architecture

Date: 2025-10-11

## Status

Accepted

## Context

We need a manageable, cohesive system for both backend and frontend without premature complexity.

## Decision

Use a monolithic architecture, combining a Spring Boot backend and an Angular frontend served from the same project.

## Consequences

✅ Simplifies development, deployment, and CI/CD

✅ Easier local setup for researchers

⚠️ Harder to scale specific components independently later
