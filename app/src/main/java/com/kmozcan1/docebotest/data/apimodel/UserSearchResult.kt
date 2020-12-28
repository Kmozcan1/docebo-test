package com.kmozcan1.docebotest.data.apimodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Kadir Mert Özcan on 28-Dec-20.
 */
@JsonClass(generateAdapter = true)
data class UserSearchResult(
    @field:Json(name = "login") var login: String,
    @field:Json(name = "id") var id: Int,
    @field:Json(name = "node_id") var nodeId: String,
    @field:Json(name = "avatar_url") var avatarUrl: String,
    @field:Json(name = "gravatar_id") var gravatarId: String? = null,
    @field:Json(name = "url") var url: String,
    @field:Json(name = "html_url") var htmlUrl: String,
    @field:Json(name = "followers_url") var followersUrl: String,
    @field:Json(name = "following_url") var followingUrl: String,
    @field:Json(name = "gists_url") var gistsUrl: String,
    @field:Json(name = "starred_url") var starredUrl: String,
    @field:Json(name = "subscriptions_url") var subscriptionsUrl: String,
    @field:Json(name = "organizations_url") var organizationsUrl: String,
    @field:Json(name = "repos_url") var reposUrl: String,
    @field:Json(name = "events_url") var eventsUrl: String,
    @field:Json(name = "received_events_url") var receivedEventsUrl: String,
    @field:Json(name = "type") var type: String,
    @field:Json(name = "site_admin") var siteAdmin: String,
    @field:Json(name = "score") var score: Int
)
/*properties:
        login:
          type: string
        id:
          type: integer
        node_id:
          type: string
        avatar_url:
          type: string
          format: uri
        gravatar_id:
          type: string
          nullable: true
        url:
          type: string
          format: uri
        html_url:
          type: string
          format: uri
        followers_url:
          type: string
          format: uri
        subscriptions_url:
          type: string
          format: uri
        organizations_url:
          type: string
          format: uri
        repos_url:
          type: string
          format: uri
        received_events_url:
          type: string
          format: uri
        type:
          type: string
        score:
          type: integer
        following_url:
          type: string
        gists_url:
          type: string
        starred_url:
          type: string
        events_url:
          type: string
        public_repos:
          type: integer
        public_gists:
          type: integer
        followers:
          type: integer
        following:
          type: integer
        created_at:
          type: string
          format: date-time
        updated_at:
          type: string
          format: date-time
        name:
          type: string
          nullable: true
        bio:
          type: string
          nullable: true
        email:
          type: string
          format: email
          nullable: true
        location:
          type: string
          nullable: true
        site_admin:
          type: boolean
        hireable:
          type: boolean
          nullable: true
        text_matches:
          "$ref": "#/components/schemas/search-result-text-matches"
        blog:
          type: string
          nullable: true
        company:
          type: string
          nullable: true
        suspended_at:
          type: string
          format: date-time
          nullable: true
      required:
      - avatar_url
      - events_url
      - followers_url
      - following_url
      - gists_url
      - gravatar_id
      - html_url
      - id
      - node_id
      - login
      - organizations_url
      - received_events_url
      - repos_url
      - site_admin
      - starred_url
      - subscriptions_url
      - type
      - url
      - score*/