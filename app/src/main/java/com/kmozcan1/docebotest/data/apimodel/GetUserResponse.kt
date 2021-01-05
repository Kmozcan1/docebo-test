package com.kmozcan1.docebotest.data.apimodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 *
 * Response data class for [com.kmozcan1.docebotest.data.api.UsersApi.getUser]
 */
@JsonClass(generateAdapter = true)
data class GetUserResponse(
    @Json(name = "avatar_url") @field:Json(name = "avatar_url") var avatarUrl: String,
    @Json(name = "events_url") @field:Json(name = "events_url") var eventsUrl: String,
    @Json(name = "followers_url") @field:Json(name = "followers_url") var followersUrl: String,
    @Json(name = "following_url") @field:Json(name = "following_url") var followingUrl: String,
    @Json(name = "gists_url") @field:Json(name = "gists_url") var gists_url: String,
    @Json(name = "gravatar_id") @field:Json(name = "gravatar_id") var gravatar_id: String? = "",
    @Json(name = "html_url") @field:Json(name = "html_url") var htmlUrl: String,
    @Json(name = "id") @field:Json(name = "id") var id: Int,
    @Json(name = "node_id") @field:Json(name = "node_id") var nodeId: String,
    @Json(name = "login") @field:Json(name = "login") var login: String,
    @Json(name = "organizations_url") @field:Json(name = "organizations_url") var orgUrl: String,
    @Json(name = "received_events_url") @field:Json(name = "received_events_url") var receivedEventsUrl: String,
    @Json(name = "repos_url") @field:Json(name = "repos_url") var reposUrl: String,
    @Json(name = "site_admin") @field:Json(name = "site_admin") var siteAdmin: Boolean,
    @Json(name = "starred_url") @field:Json(name = "starred_url") var starredUrl: String,
    @Json(name = "subscriptions_url") @field:Json(name = "subscriptions_url") var subscriptionsUrl: String,
    @Json(name = "type") @field:Json(name = "type") var type: String,
    @Json(name = "url") @field:Json(name = "url") var url: String,
    @Json(name = "bio") @field:Json(name = "bio") var bio: String? = "",
    @Json(name = "blog") @field:Json(name = "blog") var blog: String? = "",
    @Json(name = "company") @field:Json(name = "company") var company: String? = "",
    @Json(name = "email") @field:Json(name = "email") var email: String? = "",
    @Json(name = "followers") @field:Json(name = "followers") var followers: Int,
    @Json(name = "following") @field:Json(name = "following") var following: Int,
    @Json(name = "hireable") @field:Json(name = "hireable") var hireable: Boolean? = false,
    @Json(name = "location") @field:Json(name = "location") var location: String? = "",
    @Json(name = "name") @field:Json(name = "name") var name: String? = "",
    @Json(name = "public_gists") @field:Json(name = "public_gists") var publicGists: Int,
    @Json(name = "public_repos") @field:Json(name = "public_repos") var publicRepos: Int,
    @Json(name = "created_at") @field:Json(name = "created_at") var created_at: String,
    @Json(name = "updated_at") @field:Json(name = "updated_at") var updated_at: String)

/*
* public-user:
      title: Public User
      description: Public User
      type: object
      properties:
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
        following_url:
          type: string
        gists_url:
          type: string
        starred_url:
          type: string
        subscriptions_url:
          type: string
          format: uri
        organizations_url:
          type: string
          format: uri
        repos_url:
          type: string
          format: uri
        events_url:
          type: string
        received_events_url:
          type: string
          format: uri
        type:
          type: string
        site_admin:
          type: boolean
        name:
          type: string
          nullable: true
        company:
          type: string
          nullable: true
        blog:
          type: string
          nullable: true
        location:
          type: string
          nullable: true
        email:
          type: string
          format: email
          nullable: true
        hireable:
          type: boolean
          nullable: true
        bio:
          type: string
          nullable: true
        twitter_username:
          type: string
          nullable: true
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
        plan:
          type: object
          properties:
            collaborators:
              type: integer
            name:
              type: string
            space:
              type: integer
            private_repos:
              type: integer
          required:
          collaborators
          name
          space
          private_repos
        suspended_at:
          type: string
          format: date-time
          nullable: true
        private_gists:
          type: integer
          example: '0'
        total_private_repos:
          type: integer
          example: '0'
        owned_private_repos:
          type: integer
          example: '0'
        disk_usage:
          type: integer
          example: '0'
        collaborators:
          type: integer
          example: '0'
      required:
      avatar_url
      events_url
      followers_url
      following_url
      gists_url
      gravatar_id
      html_url
      id
      node_id
      login
      organizations_url
      received_events_url
      repos_url
      site_admin
      starred_url
      subscriptions_url
      type
      url
      bio
      blog
      company
      email
      followers
      following
      hireable
      location
      name
      public_gists
      public_repos
      created_at
      updated_at
      additionalProperties: false*/