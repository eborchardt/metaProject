// Project.kt

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.VcsRoot
import jetbrains.buildServer.configs.kotlin.v2019_2.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.Maven
import jetbrains.buildServer.configs.kotlin.v2019_2.Trigger
import jetbrains.buildServer.configs.kotlin.v2019_2.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.SnapshotDependency

// Define the project
project {
    // Project ID
    id("MyProject")

    // Project name
    name("My Project")

    // Project description
    description("This is my project")

    // Define the VCS root
    vcsRoot(GitVcsRoot({
        // VCS root ID
        id("MyGitVcsRoot")

        // VCS root name
        name("My Git VCS Root")

        // Git repository URL
        url("https://github.com/myuser/myrepo.git")
    }))

    // Define the build configurations
    buildType {
        // Build configuration ID
        id("Build")

        // Build configuration name
        name("Build")

        // Build configuration description
        description("Build the project")

        // Define the build steps
        steps {
            // Maven build step
            buildStep {
                type = BuildStep.Type.MAVEN
                name = "Maven Build"
                Maven(
                    // Maven executable path
                    workingDir = "%teamcity.build.workingDir%",

                    // Maven command line
                    command = "clean package"
                )
            }
        }
    }

    buildType {
        // Build configuration ID
        id("Test")

        // Build configuration name
        name("Test")

        // Build configuration description
        description("Run tests")

        // Define the build steps
        steps {
            // Maven test step
            buildStep {
                type = BuildStep.Type.MAVEN
                name = "Maven Test"
                Maven(
                    // Maven executable path
                    workingDir = "%teamcity.build.workingDir%",

                    // Maven command line
                    command = "test"
                )
            }
        }

        // Define the snapshot dependency
        snapshotDependency("Build")
    }

    buildType {
        // Build configuration ID
        id("Deploy")

        // Build configuration name
        name("Deploy")

        // Build configuration description
        description("Deploy the project")

        // Define the build steps
        steps {
            // Maven deploy step
            buildStep {
                type = BuildStep.Type.MAVEN
                name = "Maven Deploy"
                Maven(
                    // Maven executable path
                    workingDir = "%teamcity.build.workingDir%",

                    // Maven command line
                    command = "deploy"
                )
            }
        }

        // Define the snapshot dependency
        snapshotDependency("Test")

        // Define the triggers
        triggers {
            // VCS trigger
            vcsTrigger {
                // Trigger ID
                id("MyVcsTrigger")

                // Trigger name
                name("My VCS Trigger")

                // VCS trigger properties
                properties {
                    // Branch specification
                    property("branch", "*")

                    // Trigger on changes
                    property("triggerOnChanges", "true")
                }
            }
        }
    }
}
