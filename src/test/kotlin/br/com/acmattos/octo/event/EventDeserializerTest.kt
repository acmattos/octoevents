package br.com.acmattos.octo.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.taroid.knit.should
import org.junit.Before
import org.junit.Test

/**
 * @author acmattos
 * @since 20/10/2018.
 */
class EventDeserializerTest {
    private val INVALID_PAYLOAD: String = "{}"
    private val VALID_PAYLOAD: String = "{\n" +
            "    \"action\": \"labeled\",\n" +
            "    \"issue\": {\n" +
            "        \"url\": \"https://api.github.com/repos/acmattos/octoevents/issues/1\",\n" +
            "        \"repository_url\": \"https://api.github.com/repos/acmattos/octoevents\",\n" +
            "        \"labels_url\": \"https://api.github.com/repos/acmattos/octoevents/issues/1/labels{/name}\",\n" +
            "        \"comments_url\": \"https://api.github.com/repos/acmattos/octoevents/issues/1/comments\",\n" +
            "        \"events_url\": \"https://api.github.com/repos/acmattos/octoevents/issues/1/events\",\n" +
            "        \"html_url\": \"https://github.com/acmattos/octoevents/issues/1\",\n" +
            "        \"id\": 372231455,\n" +
            "        \"node_id\": \"MDU6SXNzdWUzNzIyMzE0NTU=\",\n" +
            "        \"number\": 1,\n" +
            "        \"title\": \"Test 1\",\n" +
            "        \"user\": {\n" +
            "            \"login\": \"acmattos\",\n" +
            "            \"id\": 5035530,\n" +
            "            \"node_id\": \"MDQ6VXNlcjUwMzU1MzA=\",\n" +
            "            \"avatar_url\": \"https://avatars1.githubusercontent.com/u/5035530?v=4\",\n" +
            "            \"gravatar_id\": \"\",\n" +
            "            \"url\": \"https://api.github.com/users/acmattos\",\n" +
            "            \"html_url\": \"https://github.com/acmattos\",\n" +
            "            \"followers_url\": \"https://api.github.com/users/acmattos/followers\",\n" +
            "            \"following_url\": \"https://api.github.com/users/acmattos/following{/other_user}\",\n" +
            "            \"gists_url\": \"https://api.github.com/users/acmattos/gists{/gist_id}\",\n" +
            "            \"starred_url\": \"https://api.github.com/users/acmattos/starred{/owner}{/repo}\",\n" +
            "            \"subscriptions_url\": \"https://api.github.com/users/acmattos/subscriptions\",\n" +
            "            \"organizations_url\": \"https://api.github.com/users/acmattos/orgs\",\n" +
            "            \"repos_url\": \"https://api.github.com/users/acmattos/repos\",\n" +
            "            \"events_url\": \"https://api.github.com/users/acmattos/events{/privacy}\",\n" +
            "            \"received_events_url\": \"https://api.github.com/users/acmattos/received_events\",\n" +
            "            \"type\": \"User\",\n" +
            "            \"site_admin\": false\n" +
            "        },\n" +
            "        \"labels\": [\n" +
            "            {\n" +
            "                \"id\": 1098250310,\n" +
            "                \"node_id\": \"MDU6TGFiZWwxMDk4MjUwMzEw\",\n" +
            "                \"url\": \"https://api.github.com/repos/acmattos/octoevents/labels/bug\",\n" +
            "                \"name\": \"bug\",\n" +
            "                \"color\": \"d73a4a\",\n" +
            "                \"default\": true\n" +
            "            }\n" +
            "        ],\n" +
            "        \"state\": \"open\",\n" +
            "        \"locked\": false,\n" +
            "        \"assignee\": null,\n" +
            "        \"assignees\": [],\n" +
            "        \"milestone\": null,\n" +
            "        \"comments\": 2,\n" +
            "        \"created_at\": \"2018-10-20T16:59:47Z\",\n" +
            "        \"updated_at\": \"2018-10-20T17:03:12Z\",\n" +
            "        \"closed_at\": null,\n" +
            "        \"author_association\": \"OWNER\",\n" +
            "        \"body\": \"First push event\"\n" +
            "    },\n" +
            "    \"label\": {\n" +
            "        \"id\": 1098250310,\n" +
            "        \"node_id\": \"MDU6TGFiZWwxMDk4MjUwMzEw\",\n" +
            "        \"url\": \"https://api.github.com/repos/acmattos/octoevents/labels/bug\",\n" +
            "        \"name\": \"bug\",\n" +
            "        \"color\": \"d73a4a\",\n" +
            "        \"default\": true\n" +
            "    },\n" +
            "    \"repository\": {\n" +
            "        \"id\": 153927626,\n" +
            "        \"node_id\": \"MDEwOlJlcG9zaXRvcnkxNTM5Mjc2MjY=\",\n" +
            "        \"name\": \"octoevents\",\n" +
            "        \"full_name\": \"acmattos/octoevents\",\n" +
            "        \"private\": false,\n" +
            "        \"owner\": {\n" +
            "            \"login\": \"acmattos\",\n" +
            "            \"id\": 5035530,\n" +
            "            \"node_id\": \"MDQ6VXNlcjUwMzU1MzA=\",\n" +
            "            \"avatar_url\": \"https://avatars1.githubusercontent.com/u/5035530?v=4\",\n" +
            "            \"gravatar_id\": \"\",\n" +
            "            \"url\": \"https://api.github.com/users/acmattos\",\n" +
            "            \"html_url\": \"https://github.com/acmattos\",\n" +
            "            \"followers_url\": \"https://api.github.com/users/acmattos/followers\",\n" +
            "            \"following_url\": \"https://api.github.com/users/acmattos/following{/other_user}\",\n" +
            "            \"gists_url\": \"https://api.github.com/users/acmattos/gists{/gist_id}\",\n" +
            "            \"starred_url\": \"https://api.github.com/users/acmattos/starred{/owner}{/repo}\",\n" +
            "            \"subscriptions_url\": \"https://api.github.com/users/acmattos/subscriptions\",\n" +
            "            \"organizations_url\": \"https://api.github.com/users/acmattos/orgs\",\n" +
            "            \"repos_url\": \"https://api.github.com/users/acmattos/repos\",\n" +
            "            \"events_url\": \"https://api.github.com/users/acmattos/events{/privacy}\",\n" +
            "            \"received_events_url\": \"https://api.github.com/users/acmattos/received_events\",\n" +
            "            \"type\": \"User\",\n" +
            "            \"site_admin\": false\n" +
            "        },\n" +
            "        \"html_url\": \"https://github.com/acmattos/octoevents\",\n" +
            "        \"description\": \" Octo Events is an application that listens to Github Events via webhooks and expose by an api for later use.\",\n" +
            "        \"fork\": false,\n" +
            "        \"url\": \"https://api.github.com/repos/acmattos/octoevents\",\n" +
            "        \"forks_url\": \"https://api.github.com/repos/acmattos/octoevents/forks\",\n" +
            "        \"keys_url\": \"https://api.github.com/repos/acmattos/octoevents/keys{/key_id}\",\n" +
            "        \"collaborators_url\": \"https://api.github.com/repos/acmattos/octoevents/collaborators{/collaborator}\",\n" +
            "        \"teams_url\": \"https://api.github.com/repos/acmattos/octoevents/teams\",\n" +
            "        \"hooks_url\": \"https://api.github.com/repos/acmattos/octoevents/hooks\",\n" +
            "        \"issue_events_url\": \"https://api.github.com/repos/acmattos/octoevents/issues/events{/number}\",\n" +
            "        \"events_url\": \"https://api.github.com/repos/acmattos/octoevents/events\",\n" +
            "        \"assignees_url\": \"https://api.github.com/repos/acmattos/octoevents/assignees{/user}\",\n" +
            "        \"branches_url\": \"https://api.github.com/repos/acmattos/octoevents/branches{/branch}\",\n" +
            "        \"tags_url\": \"https://api.github.com/repos/acmattos/octoevents/tags\",\n" +
            "        \"blobs_url\": \"https://api.github.com/repos/acmattos/octoevents/git/blobs{/sha}\",\n" +
            "        \"git_tags_url\": \"https://api.github.com/repos/acmattos/octoevents/git/tags{/sha}\",\n" +
            "        \"git_refs_url\": \"https://api.github.com/repos/acmattos/octoevents/git/refs{/sha}\",\n" +
            "        \"trees_url\": \"https://api.github.com/repos/acmattos/octoevents/git/trees{/sha}\",\n" +
            "        \"statuses_url\": \"https://api.github.com/repos/acmattos/octoevents/statuses/{sha}\",\n" +
            "        \"languages_url\": \"https://api.github.com/repos/acmattos/octoevents/languages\",\n" +
            "        \"stargazers_url\": \"https://api.github.com/repos/acmattos/octoevents/stargazers\",\n" +
            "        \"contributors_url\": \"https://api.github.com/repos/acmattos/octoevents/contributors\",\n" +
            "        \"subscribers_url\": \"https://api.github.com/repos/acmattos/octoevents/subscribers\",\n" +
            "        \"subscription_url\": \"https://api.github.com/repos/acmattos/octoevents/subscription\",\n" +
            "        \"commits_url\": \"https://api.github.com/repos/acmattos/octoevents/commits{/sha}\",\n" +
            "        \"git_commits_url\": \"https://api.github.com/repos/acmattos/octoevents/git/commits{/sha}\",\n" +
            "        \"comments_url\": \"https://api.github.com/repos/acmattos/octoevents/comments{/number}\",\n" +
            "        \"issue_comment_url\": \"https://api.github.com/repos/acmattos/octoevents/issues/comments{/number}\",\n" +
            "        \"contents_url\": \"https://api.github.com/repos/acmattos/octoevents/contents/{+path}\",\n" +
            "        \"compare_url\": \"https://api.github.com/repos/acmattos/octoevents/compare/{base}...{head}\",\n" +
            "        \"merges_url\": \"https://api.github.com/repos/acmattos/octoevents/merges\",\n" +
            "        \"archive_url\": \"https://api.github.com/repos/acmattos/octoevents/{archive_format}{/ref}\",\n" +
            "        \"downloads_url\": \"https://api.github.com/repos/acmattos/octoevents/downloads\",\n" +
            "        \"issues_url\": \"https://api.github.com/repos/acmattos/octoevents/issues{/number}\",\n" +
            "        \"pulls_url\": \"https://api.github.com/repos/acmattos/octoevents/pulls{/number}\",\n" +
            "        \"milestones_url\": \"https://api.github.com/repos/acmattos/octoevents/milestones{/number}\",\n" +
            "        \"notifications_url\": \"https://api.github.com/repos/acmattos/octoevents/notifications{?since,all,participating}\",\n" +
            "        \"labels_url\": \"https://api.github.com/repos/acmattos/octoevents/labels{/name}\",\n" +
            "        \"releases_url\": \"https://api.github.com/repos/acmattos/octoevents/releases{/id}\",\n" +
            "        \"deployments_url\": \"https://api.github.com/repos/acmattos/octoevents/deployments\",\n" +
            "        \"created_at\": \"2018-10-20T16:54:31Z\",\n" +
            "        \"updated_at\": \"2018-10-20T16:54:33Z\",\n" +
            "        \"pushed_at\": \"2018-10-20T16:54:32Z\",\n" +
            "        \"git_url\": \"git://github.com/acmattos/octoevents.git\",\n" +
            "        \"ssh_url\": \"git@github.com:acmattos/octoevents.git\",\n" +
            "        \"clone_url\": \"https://github.com/acmattos/octoevents.git\",\n" +
            "        \"svn_url\": \"https://github.com/acmattos/octoevents\",\n" +
            "        \"homepage\": null,\n" +
            "        \"size\": 0,\n" +
            "        \"stargazers_count\": 0,\n" +
            "        \"watchers_count\": 0,\n" +
            "        \"language\": null,\n" +
            "        \"has_issues\": true,\n" +
            "        \"has_projects\": true,\n" +
            "        \"has_downloads\": true,\n" +
            "        \"has_wiki\": true,\n" +
            "        \"has_pages\": false,\n" +
            "        \"forks_count\": 0,\n" +
            "        \"mirror_url\": null,\n" +
            "        \"archived\": false,\n" +
            "        \"open_issues_count\": 1,\n" +
            "        \"license\": {\n" +
            "            \"key\": \"mit\",\n" +
            "            \"name\": \"MIT License\",\n" +
            "            \"spdx_id\": \"MIT\",\n" +
            "            \"url\": \"https://api.github.com/licenses/mit\",\n" +
            "            \"node_id\": \"MDc6TGljZW5zZTEz\"\n" +
            "        },\n" +
            "        \"forks\": 0,\n" +
            "        \"open_issues\": 1,\n" +
            "        \"watchers\": 0,\n" +
            "        \"default_branch\": \"master\"\n" +
            "    },\n" +
            "    \"sender\": {\n" +
            "        \"login\": \"acmattos\",\n" +
            "        \"id\": 5035530,\n" +
            "        \"node_id\": \"MDQ6VXNlcjUwMzU1MzA=\",\n" +
            "        \"avatar_url\": \"https://avatars1.githubusercontent.com/u/5035530?v=4\",\n" +
            "        \"gravatar_id\": \"\",\n" +
            "        \"url\": \"https://api.github.com/users/acmattos\",\n" +
            "        \"html_url\": \"https://github.com/acmattos\",\n" +
            "        \"followers_url\": \"https://api.github.com/users/acmattos/followers\",\n" +
            "        \"following_url\": \"https://api.github.com/users/acmattos/following{/other_user}\",\n" +
            "        \"gists_url\": \"https://api.github.com/users/acmattos/gists{/gist_id}\",\n" +
            "        \"starred_url\": \"https://api.github.com/users/acmattos/starred{/owner}{/repo}\",\n" +
            "        \"subscriptions_url\": \"https://api.github.com/users/acmattos/subscriptions\",\n" +
            "        \"organizations_url\": \"https://api.github.com/users/acmattos/orgs\",\n" +
            "        \"repos_url\": \"https://api.github.com/users/acmattos/repos\",\n" +
            "        \"events_url\": \"https://api.github.com/users/acmattos/events{/privacy}\",\n" +
            "        \"received_events_url\": \"https://api.github.com/users/acmattos/received_events\",\n" +
            "        \"type\": \"User\",\n" +
            "        \"site_admin\": false\n" +
            "    }\n" +
            "}"
    private lateinit var module: SimpleModule
    private lateinit var mapper: ObjectMapper

    @Before
    fun setUp() {
        module = SimpleModule()
        module.addDeserializer(Event::class.java, EventDeserializer(Event::class.java))
        mapper = ObjectMapper()
        mapper.registerModule(module)
    }

    @Test
    fun `Deserialize GitHub Payload to a Event`() {
        val event = mapper.readValue(VALID_PAYLOAD, Event::class.java)

        event.should notBe null
        event.action.should be "labeled"
        event.repositoryUrl.should be "https://api.github.com/repos/acmattos/octoevents"
        event.number.should be "1"
        event.login.should be "acmattos"
        event.state.should be "open"
        event.comments.should be "2"
        event.createdAt.should be "2018-10-20T16:59:47Z"
        event.updatedAt.should be "2018-10-20T17:03:12Z"
        event.closedAt.should be "null"
        event.body.should be "First push event"
    }

    @Test
    fun `Deserialize invalid Payload Causes a Null Event`() {
        val event = mapper.readValue(INVALID_PAYLOAD, Event::class.java)

        event.should be null
    }
}