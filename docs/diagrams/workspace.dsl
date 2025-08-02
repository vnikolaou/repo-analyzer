workspace "Repo-Analyzer" "Automating the selection and sampling of GitHub repositories for further research" {

    !identifiers hierarchical

    model {
        u = person "Researcher" "Explores and curates open-source repositories to build datasets for empirical research"
        group "Personal Use [Environment]"  {
            ss1 = softwareSystem "Repo-Analyzer" "Supports discovery, sampling, and metadata enrichment of GitHub repositories for research use" {
                wa = container "Web Application"
                db = container "Database Schema" {
                    tags "Database"
                }
            }
        }

        ss2 = softwareSystem "GitHub Open Source Ecosystem" "Enables developers to share, maintain, and evolve open-source code collaboratively"

        u -> ss1.wa "Searches, samples, and enriches repo data"
        ss1.wa -> ss1.db "Reads from and writes to"

        ss1.wa -> ss2 "Searches open-source repositories via GitHub API"
    }

    views {
        systemContext ss1 "Diagram-1" {
            include *
            autolayout lr
        }

        container ss1 "Diagram-2" {
            include *
            autolayout lr
        }

        styles {
            element "Element" {
                color #ffffff
            }
            element "Person" {
                background #05527d
                shape person
            }
            element "Software System" {
                background #066296
            }
            element "Container" {
                background #0773af
            }
            element "Database" {
                shape cylinder
            }
        }
    }

    configuration {
        scope softwaresystem
    }

}