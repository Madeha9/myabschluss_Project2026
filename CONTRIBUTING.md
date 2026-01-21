# Contributing Guidelines

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Git Workflow](#git-workflow)
- [Branching Strategy](#branching-strategy)
- [Commit Message Guidelines](#commit-message-guidelines)
- [Code Style](#code-style)
- [Pull Request Process](#pull-request-process)
- [Testing Requirements](#testing-requirements)

## Code of Conduct

- Be respectful and professional in all interactions
- Focus on constructive feedback
- Collaborate openly and transparently

## Git Workflow

We follow a feature branch workflow:

1. Create a feature branch from `main`
2. Make your changes
3. Commit with meaningful messages
4. Push your branch
5. Create a Pull Request
6. Address review feedback
7. Merge after approval

## Branching Strategy

### Branch Naming Convention

Use descriptive branch names following this pattern:

```
<type>/<short-description>
```

**Types:**
- `feature/` - New features
- `bugfix/` - Bug fixes
- `hotfix/` - Critical production fixes
- `docs/` - Documentation changes
- `refactor/` - Code refactoring
- `test/` - Test additions or modifications

**Examples:**
```
feature/user-authentication
bugfix/login-validation
docs/api-documentation
```

### Main Branches

- `main` - Production-ready code
- `develop` - Integration branch for features (if used)

## Commit Message Guidelines

### Format

```
<type>: <subject>

<body>

<footer>
```

### Types

- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, missing semicolons, etc.)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

### Examples

```
feat: add user login functionality

Implement JWT-based authentication for user login.
Added login endpoint and token validation.

Closes #123
```

```
fix: resolve null pointer exception in data processing

Check for null values before processing user data.

Fixes #456
```

### Best Practices

- Use the imperative mood ("add feature" not "added feature")
- Keep the subject line under 50 characters
- Capitalize the subject line
- Don't end the subject line with a period
- Use the body to explain what and why vs. how

## Code Style

### Java Code Style

- **Indentation:** 4 spaces (no tabs)
- **Line Length:** Maximum 120 characters
- **Naming Conventions:**
  - Classes: `PascalCase` (e.g., `UserService`)
  - Methods: `camelCase` (e.g., `getUserById`)
  - Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_RETRY_COUNT`)
  - Variables: `camelCase` (e.g., `userId`)
  
### Code Organization

- One class per file
- Order: fields, constructors, methods
- Group related methods together
- Use meaningful variable names
- Keep methods short and focused

### Comments

- Write self-documenting code
- Use Javadoc for public APIs
- Explain "why" not "what"
- Keep comments up-to-date

### Example

```java
/**
 * Service for managing user operations.
 */
public class UserService {
    
    private final UserRepository userRepository;
    
    /**
     * Constructs a new UserService.
     *
     * @param userRepository the repository for user data
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Retrieves a user by their ID.
     *
     * @param userId the user's unique identifier
     * @return the user if found
     * @throws UserNotFoundException if user doesn't exist
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
```

## Pull Request Process

1. **Before Creating a PR:**
   - Ensure all tests pass
   - Update documentation if needed
   - Rebase on latest `main` if necessary
   - Self-review your changes

2. **PR Title:**
   - Use the same format as commit messages
   - Be clear and descriptive

3. **PR Description:**
   - Describe what changes were made
   - Explain why the changes were necessary
   - Reference related issues
   - Include screenshots for UI changes

4. **Review Process:**
   - At least one approval required
   - Address all review comments
   - Keep discussions focused and constructive

5. **Merging:**
   - Use "Squash and merge" for feature branches
   - Delete branch after merging

## Testing Requirements

### Test Coverage

- Minimum 80% code coverage for new code
- Write unit tests for all new features
- Update existing tests if behavior changes

### Test Types

- **Unit Tests:** Test individual components
- **Integration Tests:** Test component interactions
- **End-to-End Tests:** Test complete workflows

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run with coverage
mvn test jacoco:report
```

### Test Naming

```java
@Test
public void getUserById_whenUserExists_returnsUser() {
    // Arrange
    // Act
    // Assert
}

@Test
public void getUserById_whenUserNotFound_throwsException() {
    // Test implementation
}
```

## Development Setup

See [docs/setup.md](docs/setup.md) for detailed development environment setup instructions.

## Questions?

If you have questions about contributing, please:
- Check existing documentation
- Review closed PRs and issues
- Ask in project discussions
- Contact the project maintainers

Thank you for contributing!
