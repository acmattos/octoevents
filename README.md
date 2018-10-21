# Octo Events REST Microservice

Octo Events is a microservice application, built on top of
[Kotlin](https://kotlinlang.org) and [Javalin](https://javalin.io) framework and
supported by [MongoDB NoSQL](https://www.mongodb.com/) database.

The pourpose of this microservice is to listen to Github Events via webhooks
and expose them through a REST API for later use.

## Specification

This microservice captures GitHub's Issues Events (Issue opened, edited, closed,
reopened, assigned, unassigned, labeled, unlabeled, milestoned, or demilestoned)
through an appropiated endpoint. These events are processed and stored inside a
NoSQL database. Any client can consume GitHub's Issues Events through another
endpoint just informing the issue's number along with the URL.

### Webhook Endpoint

This endpoint will receive GitHub's Issues Events, process and store them on
database, to be consumed later.

**Endpoint:** *POST* http://localhost:7000/webhooks

**Format:**

This is a sample of a valid Issue's Event JSON object sent on request's body by 
GitHub:

```
{
   "action": "labeled",
   "issue": {
      "url": "https://api.github.com/repos/acmattos/octoevents/issues/1",
      "repository_url": "https://api.github.com/repos/acmattos/octoevents",
      "labels_url": "https://api.github.com/repos/acmattos/octoevents/issues/1/labels{/name}",
      "comments_url": "https://api.github.com/repos/acmattos/octoevents/issues/1/comments",
      "events_url": "https://api.github.com/repos/acmattos/octoevents/issues/1/events",
      "html_url": "https://github.com/acmattos/octoevents/issues/1",
      "id": 372231455,
      "node_id": "MDU6SXNzdWUzNzIyMzE0NTU=",
      "number": 1,
      "title": "Test 1",
      "user": {
         "login": "acmattos",
         "id": 5035530,
         "node_id": "MDQ6VXNlcjUwMzU1MzA=",
         "avatar_url": "https://avatars1.githubusercontent.com/u/5035530?v=4",
         "gravatar_id": "",
         "url": "https://api.github.com/users/acmattos",
         "html_url": "https://github.com/acmattos",
         "followers_url": "https://api.github.com/users/acmattos/followers",
         "following_url": "https://api.github.com/users/acmattos/following{/other_user}",
         "gists_url": "https://api.github.com/users/acmattos/gists{/gist_id}",
         "starred_url": "https://api.github.com/users/acmattos/starred{/owner}{/repo}",
         "subscriptions_url": "https://api.github.com/users/acmattos/subscriptions",
         "organizations_url": "https://api.github.com/users/acmattos/orgs",
         "repos_url": "https://api.github.com/users/acmattos/repos",
         "events_url": "https://api.github.com/users/acmattos/events{/privacy}",
         "received_events_url": "https://api.github.com/users/acmattos/received_events",
         "type": "User",
         "site_admin": false
      },
      "labels": [
         {
            "id": 1098250310,
            "node_id": "MDU6TGFiZWwxMDk4MjUwMzEw",
            "url": "https://api.github.com/repos/acmattos/octoevents/labels/bug",
            "name": "bug",
            "color": "d73a4a",
            "default": true
         }
      ],
      "state": "open",
      "locked": false,
      "assignee": null,
      "assignees": [],
      "milestone": null,
      "comments": 2,
      "created_at": "2018-10-20T16:59:47Z",
      "updated_at": "2018-10-20T17:03:12Z",
      "closed_at": null,
      "author_association": "OWNER",
      "body": "First push event"
   },
   "label": {
      "id": 1098250310,
      "node_id": "MDU6TGFiZWwxMDk4MjUwMzEw",
      "url": "https://api.github.com/repos/acmattos/octoevents/labels/bug",
      "name": "bug",
      "color": "d73a4a",
      "default": true
   },
   "repository": {
      "id": 153927626,
      "node_id": "MDEwOlJlcG9zaXRvcnkxNTM5Mjc2MjY=",
      "name": "octoevents",
      "full_name": "acmattos/octoevents",
      "private": false,
      "owner": {
         "login": "acmattos",
         "id": 5035530,
         "node_id": "MDQ6VXNlcjUwMzU1MzA=",
         "avatar_url": "https://avatars1.githubusercontent.com/u/5035530?v=4",
         "gravatar_id": "",
         "url": "https://api.github.com/users/acmattos",
         "html_url": "https://github.com/acmattos",
         "followers_url": "https://api.github.com/users/acmattos/followers",
         "following_url": "https://api.github.com/users/acmattos/following{/other_user}",
         "gists_url": "https://api.github.com/users/acmattos/gists{/gist_id}",
         "starred_url": "https://api.github.com/users/acmattos/starred{/owner}{/repo}",
         "subscriptions_url": "https://api.github.com/users/acmattos/subscriptions",
         "organizations_url": "https://api.github.com/users/acmattos/orgs",
         "repos_url": "https://api.github.com/users/acmattos/repos",
         "events_url": "https://api.github.com/users/acmattos/events{/privacy}",
         "received_events_url": "https://api.github.com/users/acmattos/received_events",
         "type": "User",
         "site_admin": false
      },
      "html_url": "https://github.com/acmattos/octoevents",
      "description": " Octo Events is an application that listens to Github Events via webhooks and expose by an api for later use.",
      "fork": false,
      "url": "https://api.github.com/repos/acmattos/octoevents",
      "forks_url": "https://api.github.com/repos/acmattos/octoevents/forks",
      "keys_url": "https://api.github.com/repos/acmattos/octoevents/keys{/key_id}",
      "collaborators_url": "https://api.github.com/repos/acmattos/octoevents/collaborators{/collaborator}",
      "teams_url": "https://api.github.com/repos/acmattos/octoevents/teams",
      "hooks_url": "https://api.github.com/repos/acmattos/octoevents/hooks",
      "issue_events_url": "https://api.github.com/repos/acmattos/octoevents/issues/events{/number}",
      "events_url": "https://api.github.com/repos/acmattos/octoevents/events",
      "assignees_url": "https://api.github.com/repos/acmattos/octoevents/assignees{/user}",
      "branches_url": "https://api.github.com/repos/acmattos/octoevents/branches{/branch}",
      "tags_url": "https://api.github.com/repos/acmattos/octoevents/tags",
      "blobs_url": "https://api.github.com/repos/acmattos/octoevents/git/blobs{/sha}",
      "git_tags_url": "https://api.github.com/repos/acmattos/octoevents/git/tags{/sha}",
      "git_refs_url": "https://api.github.com/repos/acmattos/octoevents/git/refs{/sha}",
      "trees_url": "https://api.github.com/repos/acmattos/octoevents/git/trees{/sha}",
      "statuses_url": "https://api.github.com/repos/acmattos/octoevents/statuses/{sha}",
      "languages_url": "https://api.github.com/repos/acmattos/octoevents/languages",
      "stargazers_url": "https://api.github.com/repos/acmattos/octoevents/stargazers",
      "contributors_url": "https://api.github.com/repos/acmattos/octoevents/contributors",
      "subscribers_url": "https://api.github.com/repos/acmattos/octoevents/subscribers",
      "subscription_url": "https://api.github.com/repos/acmattos/octoevents/subscription",
      "commits_url": "https://api.github.com/repos/acmattos/octoevents/commits{/sha}",
      "git_commits_url": "https://api.github.com/repos/acmattos/octoevents/git/commits{/sha}",
      "comments_url": "https://api.github.com/repos/acmattos/octoevents/comments{/number}",
      "issue_comment_url": "https://api.github.com/repos/acmattos/octoevents/issues/comments{/number}",
      "contents_url": "https://api.github.com/repos/acmattos/octoevents/contents/{+path}",
      "compare_url": "https://api.github.com/repos/acmattos/octoevents/compare/{base}...{head}",
      "merges_url": "https://api.github.com/repos/acmattos/octoevents/merges",
      "archive_url": "https://api.github.com/repos/acmattos/octoevents/{archive_format}{/ref}",
      "downloads_url": "https://api.github.com/repos/acmattos/octoevents/downloads",
      "issues_url": "https://api.github.com/repos/acmattos/octoevents/issues{/number}",
      "pulls_url": "https://api.github.com/repos/acmattos/octoevents/pulls{/number}",
      "milestones_url": "https://api.github.com/repos/acmattos/octoevents/milestones{/number}",
      "notifications_url": "https://api.github.com/repos/acmattos/octoevents/notifications{?since,all,participating}",
      "labels_url": "https://api.github.com/repos/acmattos/octoevents/labels{/name}",
      "releases_url": "https://api.github.com/repos/acmattos/octoevents/releases{/id}",
      "deployments_url": "https://api.github.com/repos/acmattos/octoevents/deployments",
      "created_at": "2018-10-20T16:54:31Z",
      "updated_at": "2018-10-20T16:54:33Z",
      "pushed_at": "2018-10-20T16:54:32Z",
      "git_url": "git://github.com/acmattos/octoevents.git",
      "ssh_url": "git@github.com:acmattos/octoevents.git",
      "clone_url": "https://github.com/acmattos/octoevents.git",
      "svn_url": "https://github.com/acmattos/octoevents",
      "homepage": null,
      "size": 0,
      "stargazers_count": 0,
      "watchers_count": 0,
      "language": null,
      "has_issues": true,
      "has_projects": true,
      "has_downloads": true,
      "has_wiki": true,
      "has_pages": false,
      "forks_count": 0,
      "mirror_url": null,
      "archived": false,
      "open_issues_count": 1,
      "license": {
         "key": "mit",
         "name": "MIT License",
         "spdx_id": "MIT",
         "url": "https://api.github.com/licenses/mit",
         "node_id": "MDc6TGljZW5zZTEz"
      },
      "forks": 0,
      "open_issues": 1,
      "watchers": 0,
      "default_branch": "master"
   },
   "sender": {
      "login": "acmattos",
      "id": 5035530,
      "node_id": "MDQ6VXNlcjUwMzU1MzA=",
      "avatar_url": "https://avatars1.githubusercontent.com/u/5035530?v=4",
      "gravatar_id": "",
      "url": "https://api.github.com/users/acmattos",
      "html_url": "https://github.com/acmattos",
      "followers_url": "https://api.github.com/users/acmattos/followers",
      "following_url": "https://api.github.com/users/acmattos/following{/other_user}",
      "gists_url": "https://api.github.com/users/acmattos/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/acmattos/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/acmattos/subscriptions",
      "organizations_url": "https://api.github.com/users/acmattos/orgs",
      "repos_url": "https://api.github.com/users/acmattos/repos",
      "events_url": "https://api.github.com/users/acmattos/events{/privacy}",
      "received_events_url": "https://api.github.com/users/acmattos/received_events",
      "type": "User",
      "site_admin": false
   }
}
```
**Response Messages:**

| code  | Consequence                    |
| :---: | ------------------------------ |
| 201   | Event processed and persisted. |
| 400   | Invalid event. Not processed.  |
| 500   | Internal server error.         |

### Event Endpoint

This endpoint will receive GitHub's Issues Events, process and store them on
database, to be consumed later.

**Endpoint:** *GET* http://localhost:7000/issues/<NUMBER>/events

**Format:**

This is a sample of a response (code 200) from this endpoint:

```
[{
   "action":"opened",
   "repositoryUrl":"https://api.github.com/repos/acmattos/octoevents",
   "number":"4",
   "title":"Test 4",
   "login":"acmattos",
   "state":"open",
   "comments":"0",
   "createdAt":"2018-10-21T01:05:24Z",
   "updatedAt":"2018-10-21T01:05:24Z",
   "closedAt":"null",
   "body":"Number 4"
}]
```
**Response Messages:**

| code  | Consequence            |
| :---: | ---------------------- |
| 200   | Events found.          |
| 404   | Events not found.      |
| 500   | Internal server error. |

## Development Environment

### Software Requirements

- GIT 2.13.1.windows.2 or later
- JDK 1.8 or later
- Maven 3 or later
- MongoBD 3.6.5 or later
- [ngrok](https://ngrok.com/) 2.2.8 or later

### Software Libraries

#### Build Dependencies
- javalin
- kodein-di-generic-jvm
- jackson-module-kotlin
- kmongo

#### Test Dependencies
- knit
- kotlin-test-junit
- de.flapdoodle.embed.mongo
- spring-web

#### Runtime Dependencies
- slf4j-simple

### Getting Started

1. Ensure that GIT, JDK, Maven, MongoDB and ngrok are installed and working
   properly.
2. Ensure that MongoBD is up and running.
3. Open a command console and change directory to one of your preference.
4. Get a version of this project by typing:

   > git clone https://github.com/acmattos/octoevents.git

5. After clone action is done, change to `octoevents` directory.
6. Build a version of `Octo Events` by typing:

   > mvn clean compile install

7. After `mvn` command is done, change to `target` directory.
8. Run application by typing:

   > java -jar oe-1.0.0.jar

   - This application will listen to 7000 port, so ensure that this port is
     free, otherwise the application will not start.
TODO   - If the port is **busy**, you will be able to change it. Just open
     `<PROJECT_ROOT_PATH>\src\main\resources\application.properties`
     and change the following property:

     > server.port=<TYPE_NEW_PORT_HERE>

   - **OBS**: Keep in mind that this action requires a new artifact
     construction (change directory to `<PROJECT_ROOT_PATH>` and follow *step
     10* instructions).
9. Open a command console and type:

   > ngrok http 7000

10. After the program starts, look up for your external DNS (log line should
    looks like the following sample):

   > Forwarding                    http://38ea4caf.ngrok.io -> localhost:7000

11. Open GitHub web site and choose a repository of yours (the example is this
    repository), then click *settings* -> *Whebhooks*:

    > https://github.com/acmattos/octoevents/settings/hooks

12. Click `add webhook` button. Put the URL retrieved from step 10 on
    `Payload URL` text field. Select `application\json` on `Content type` field.
    Click on `Let me select individual events.` option. Now click on `Issues`
    and save the webhook.

13. Open a browser window and type:

   > http://localhost:7000/issues/1/events

14. This action allows you to retrieve all issue's events stored by this
    microservice. Keep in mind that it will only show results for GitHub's
    processed events. 404 HTTP response code means that you need to follow the
    next step.

15. Create a issue on GitHub. If everything was ok, you'll receive a POST from
    GitHub, notifying Octo Service running locally. If no error happened, you
    can repeat step 14 again. Now you'll get the result processed by this
    microservice.

 ### Architecture

The application was built on top of Javalin Framework and Kotling language.
Designed as a REST Microservice, Javalin allows an easy HTTP server creation
and Kotlin cuts all verbose aspects of Java classes, but mantaining the same
well known runtime evnironment: the Java's JVM.

The database chosen to persist `Octo Events` data was `MongoDB`: a perfect
choice to accomodate documents that comes back and forth as JSON objects.

All classes that requires dependencies are using Kodein to deal with dependency
injection. This lib is writen in Kotlin and supports the runtive environment
very well.

Tests are using
BankSlip API is fully tested. JUnit automates both Unit and Integration tests.
 Mockito helps you to mock complex object's behaviour, allowing you to cover many
 flows of your code design. Hamcrest provides matchers that can be combined to
 create flexible expressions of intent. [Embedded MongoDB](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo
 "Organization Flapdoodle OSS") provides a platform neutral way for running a
 MongoDB instance during Integration Tests.

 BankSlip API is heavily tested using unit tests because they are lighter than
 integration tests (IT). IT's are left to:
 - Test UUID assignment, on bank slip's document creation;
 - Test endpoint's point-to-point (P2P) communication (request received from
   client, request processed, database manipulation, response produced to
   client);
 - Test application's state during P2P communication.

### API Packages

This is a brief description of Octo Events packages.

#### package br.com.acmattos.octo

Application's root package.

`Main`: bootstraps Octo Events.

#### package br.com.acmattos.octo.endpoint

Contains both Issue and Webhook endpoints.

`IssueEndpoint`: is responsible for dealing with events stored on database.
`WebhookEndpoint`: is responsible for processing events sent from GitHub.

#### package br.com.acmattos.octo.event

Contains artifacts that manipulates event's data.

`Event`: Holds even's data processed and persisted on database.
`EventDeserializer`: Converts GitHub's event data to Octo Events data.
`EventService`: Makes the interface work with MongoDB, storing and retrieving
                data from andd to it.

#### package br.com.acmattos.octo.server

Creates a HTTP Server that listen to REST requests.

`HttpServer`: Exposes both Issue and Webhook endpoints to the world.
