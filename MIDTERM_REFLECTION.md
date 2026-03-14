# Midterm Reflection

## Git Workflow Challenges
During development, the team followed a feature branch workflow using GitHub. Each member worked on their assigned branch and regularly pulled the latest updates from the develop branch before starting new work to minimize conflicts.

However, a merge conflict still occurred when multiple branches modified the same file, specifically AppNav.kt, where navigation routes were being updated. Because different branches edited the same section of the file, Git could not automatically merge the changes.

The conflict was resolved manually by reviewing the differences and carefully integrating both navigation routes so that all features continued to function correctly.

This experience improved our understanding of:
- Branch synchronization using the develop branch
- Pull Request review and integration
- Manual conflict resolution in Git
- Coordinating code changes across multiple feature branches

## Architecture and Technical Understanding
The application follows the MVVM (Model–View–ViewModel) architecture, which separates the UI, business logic, and data management layers.

Through implementing MVVM, we learned the importance of separation of concerns:
- The UI layer (Jetpack Compose screens) handles presentation and user interaction.
- The ViewModel layer manages application state and business logic using StateFlow.
- The Repository layer abstracts data operations and provides a single source of truth for the ViewModels.
- The Room Database layer handles local data persistence through entities and DAOs.

Using StateFlow allows the UI to observe state changes from the ViewModel and automatically update when the underlying data changes.

## Key Lessons Learned
- Always pull from develop before starting new work to avoid outdated code.
- Use feature branches to isolate development tasks.
- Make small, clear commits to track progress and changes.
- Ensure proper implementation of ViewModels to prevent state loss during recomposition.
- Verify the integration of Room entities, DAOs, and repositories to maintain a stable data flow.
- Test features before submitting Pull Requests for integration.

Overall, this phase strengthened our understanding of structured Android application development using MVVM architecture, Room database integration, and Git-based team collaboration.