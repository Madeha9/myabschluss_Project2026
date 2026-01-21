# Architecture Decision Records (ADRs)

This directory contains links to the Architecture Decision Records maintained in the main documentation folder.

> **Important:** The actual ADR files are located in the repository root at `/documentation/07_decisions/`, not within the `/docs/` directory. This separation maintains the project's convention of keeping development process documentation separate from user-facing technical documentation.

## What are ADRs?

Architecture Decision Records (ADRs) are documents that capture important architectural decisions made during the development of the project. Each ADR describes:

- **Context:** The issue or problem being addressed
- **Decision:** The architectural decision made
- **Consequences:** The resulting context after the decision is applied
- **Alternatives:** Other options that were considered

## ADR List

The actual ADR documents are maintained in the `/documentation/07_decisions/` directory (relative to repository root):

### Available ADRs

1. [ADR 001: Architecture Decision](../../../documentation/07_decisions/adr_001_architecture_decision.md)
   - Initial architecture approach and design patterns

2. [ADR 002: Technology Selection](../../../documentation/07_decisions/adr_002_technology_selection.md)
   - Selection of technologies and frameworks

## Browse All ADRs

For a complete list of all architectural decisions, browse the directory:

**Repository Path:** `/documentation/07_decisions/`

**From this location:** `../../../documentation/07_decisions/`

## Creating New ADRs

When making significant architectural decisions, document them by:

1. Creating a new ADR in `/documentation/07_decisions/`
2. Following the naming convention: `adr_XXX_brief_title.md`
3. Using the ADR template structure
4. Updating this README with a link to the new ADR

## ADR Template

```markdown
# ADR XXX: [Title]

## Status
[Proposed | Accepted | Deprecated | Superseded]

## Context
[Describe the issue or problem]

## Decision
[Describe the decision made]

## Consequences
### Positive
- [Benefit 1]
- [Benefit 2]

### Negative
- [Trade-off 1]
- [Trade-off 2]

## Alternatives Considered
1. [Alternative 1]
   - Pros: ...
   - Cons: ...
2. [Alternative 2]
   - Pros: ...
   - Cons: ...
```

## References

- [Architecture Decision Records (ADR) Pattern](https://adr.github.io/)
- [Documentation Architecture Guide](https://docs.arc42.org/)

---

**Note:** This is a reference directory. The actual ADR files are located in `/documentation/07_decisions/`
