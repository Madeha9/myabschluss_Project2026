# Definition of Done

A work item is considered done only when all the following conditions are met:

## Functional Criteria

- Feature is implemented according to the defined requirements
- Acceptance criteria are fulfilled and manually tested
- The feature works correctly with the live Quarkus backend
  and is visible in the Angular UI
- Edge cases and invalid inputs are handled gracefully
  (e.g. wrong file format, missing invoice data)

## Technical Criteria

- Code is committed and pushed to the GitHub repository
- The Quarkus backend builds successfully with `mvn package`
- The Angular frontend compiles without errors with `ng build`
- No critical errors appear in the browser console or Quarkus terminal
- Sensitive data (AWS credentials, API keys) are not committed to Git

## Quality Criteria

- Code is readable and follows consistent naming conventions
- No unnecessary commented-out code left in the file
- API endpoints return appropriate HTTP status codes
  (200, 400, 404, 500)

## Documentation Criteria

- README is updated if a new feature or endpoint was added
- API endpoint is documented with its method, path, and purpose
- Any known limitations of the feature are noted