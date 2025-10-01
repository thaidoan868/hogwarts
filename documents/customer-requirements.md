# User Stories
Headmasters at Hogwarts School want an application to manage their artifacts.

## Artifact Management
- A artifact has images, and owner history
- As a guest I can find a artifact by id
- As a guest I can view all artifacts
- As a guest I can filter artifacts by their characteristics and most visited
- As a contributor, I can propose a new artifact
- As a contributor, I can propose updating a artifact
- As a contributor, I can propose deleting a artifact

## Wizard Management
- A wizard has images, and information, current-owned or ex-owned artifacts
- As a guest I can find a wizard by id
- As a guest I can view all wizards
- As a guest I can filter wizards by their characteristics and most visited
- As a contributor, I can propose a new wizard
- As a contributor, I can propose updating a wizard
- As a contributor, I can propose deleting a wizard

## Comments
- As a contributor I can comment on posts, articles, wizards
- As a contributor I can report a comment
- As a reviewer, I can remove a comment

## Posts/questions
- As a contributor, I can share a post
- As a contributor, I can ask a question
- As a contributor I can report a post
- As a reviewer, I can remove a post

## Articles
- As a contributor, I can write an article
- As a contributor I can report a article
- As a reviewer, I can remove a article

## Chatting
- As a contributor, I can chat via text or image with other contributors
- As a contributor, I can block contributors I don't like

## Contributor
- As a guest, I can register a new account with email verification
- As a guest, I can see a contributor profile, his posts, questions, articles
- As a contributor, I can reset, change my password
- As a user, I can upload an avatar; the system generates thumbnail/medium/original variants.
- As a contributor, I can update my profile, set each property public or friend-only
- As a contributor, I can make friend with other contributors
- As a contributor, I can list friend requests
- As a contributor, I can accept a friend request
- As a contributor, I can receive notifications about comments, messages, approved requests, removed posts or comments
- As a contributor, I can report another contributor

## Reviewer
## Admin
- As an admin, I can disable a contributor and force him to log out

## new Feed
- As a guest, I can see new posts, top contributors

## Search
synonyms, autocomplete, and spelling/typo handling in OpenSearch/Elasticsearch

# System Requirements
- audit, logging
- As a platform, I rate-limit (per IP, per token) and throttle costly endpoints (search, upload).
- backup
- redis caching
- RFC7807 Error response
