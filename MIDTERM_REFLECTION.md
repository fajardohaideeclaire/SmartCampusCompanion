# Midterm Reflection

## Git Workflow Challenges
During development, we ensured that we pulled the latest updates from the develop branch before starting any new work. This helped minimize conflicts. However, we still encountered a merge conflict when multiple branches modified the same file. The conflict was resolved manually by reviewing the differences and carefully integrating both changes.

This experience improved our understanding of:
- Branch synchronization
- Pull Request review process
- Manual conflict resolution in Git

## Architecture and Technical Understanding
Through implementing MVVM architecture, we learned the importance of separation of concerns:
- The UI layer (Composable screens) handles presentation only.
- The ViewModel manages business logic and state.
- The Repository abstracts data operations.
- Room Database handles local data persistence.

Using StateFlow allowed us to observe real-time updates from the database and automatically reflect changes in the UI.

## Key Lessons Learned
- Always pull from develop before starting new work.
- Make small, logical commits to track progress clearly.
- Use feature branches to isolate development.
- Ensure proper integration of Room entities and DAOs to avoid runtime crashes.
- Test features thoroughly before creating a Pull Request.

Overall, this phase strengthened our understanding of structured Android app development using MVVM, Room, and Git-based collaboration.
