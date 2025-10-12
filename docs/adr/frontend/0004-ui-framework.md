# 4. UI Framework

Date: 2025-10-12

## Status

Accepted

## Context

The frontend needs to be implemented quickly to support core functionality and validation of research workflows. Using a full-featured UI framework (e.g., Angular Material, PrimeNG) would add unnecessary setup and maintenance overhead at this stage.

## Decision

Use plain HTML and minimal custom CSS for the UI, avoiding major UI frameworks to maximize development speed and simplicity.

## Consequences

✅ Enables rapid UI development and easy customization

✅ Reduces build complexity and dependencies

⚠️ Limits UI consistency, accessibility, and scalability

⚠️ May require refactoring when transitioning to a framework later
