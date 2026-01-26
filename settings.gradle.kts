pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            name = "GitLab"
            val gitlabHost = providers.gradleProperty("gitlabHost").get()
            val groupId = providers.gradleProperty("gitlabGroupId").get()
            url = uri("$gitlabHost/api/v4/groups/$groupId/-/packages/maven")
            credentials {
                username = "Private-Token"
                password = providers.gradleProperty("gitlabToken").get()
            }
        }
    }
}

rootProject.name = "GitLabPublishSample"
include(":app")
include(":lib")
 