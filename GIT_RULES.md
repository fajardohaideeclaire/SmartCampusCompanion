# Git Rules and Workflow
Smart Campus Companion Project

This document defines the Git workflow and commit rules used by the development team.

---

## Branch Structure

The project uses a structured branching strategy.

master  
Stable release branch.

develop  
Integration branch where completed features are merged before final release.

teamleader-fajardo  
Architecture setup and system integration.

feature/task-manager  
Task Manager feature development.

feature/announcements  
Campus Announcements feature development.

feature-dacillo  
Feature development tasks handled by Dacillo.

ui/ux-fernandez  
User interface and design improvements.

qa-delmundo  
Documentation, testing, and quality assurance.

git-deLeon  
Repository configuration and Git management tasks.

---

## General Workflow

1. Always sync the latest code before starting work.

git checkout develop  
git pull origin develop

2. Switch to your assigned branch.

git checkout your-branch

3. Work only in your assigned branch.

4. Make small and meaningful commits.

5. Push changes regularly.

git add .  
git commit -m "clear commit message"  
git push origin your-branch

6. Create a Pull Request when the feature is complete.

---

## Commit Requirements

Each team member must contribute **at least 8 commits** during the midterm development phase.

Commits should represent real progress such as:

- Implementing features
- Fixing bugs
- Improving UI
- Updating documentation
- Refactoring code
- Writing tests

Avoid committing empty or unnecessary changes.

---

## Commit Message Format

Use clear and descriptive commit messages.

Recommended format:

type: short description

Examples:

feat: implement task creation feature  
fix: resolve login validation bug  
docs: update README documentation  
style: improve UI layout for dashboard  
refactor: reorganize repository structure

Commit types:

feat  
New feature implementation

fix  
Bug fixes

docs  
Documentation updates

style  
UI or formatting improvements

refactor  
Code restructuring without changing behavior

test  
Testing related changes

---

## Pull Request Rules

Before opening a Pull Request:

- Ensure the project builds successfully
- Test the feature locally
- Resolve merge conflicts if any

Pull Request flow:

working branch → develop

Final integration:

develop → master

---

## Team Responsibilities

Fajardo  
Project architecture, system integration, and repository coordination.

Dacillo  
Feature implementation and functionality updates.

Fernandez  
User interface and layout improvements.

De Leon  
Git repository management and workflow enforcement.

Del Mundo  
Testing, documentation, and quality assurance.

---

## Important Notes

- Never commit directly to the master branch.
- Always work in your assigned branch.
- Keep commits small and frequent.
- Write meaningful commit messages.
- Push changes regularly to keep branches updated.
