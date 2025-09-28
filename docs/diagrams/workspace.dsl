workspace "Repo-Analyzer" "Automating the selection and sampling of GitHub repositories for further research" {

    !identifiers hierarchical

    model {

        archetypes {
            webapp = container {
                technology "Java 17 / SpringBoot 3.4.7"
                tag "api"
            }

            webui = container {
                technology "Angular 18 / Typescript 5.4.5"
                tag "ui"
            }

            database = container {
                technology "PostgreSQL v15.11"
                tag "db"
            }
        }

        u = person "Researcher" "Explores and curates open-source repositories to build datasets for empirical research"
        
        group "Personal Use [Environment]"  {
            ss1 = softwareSystem "Repo-Analyzer" "Supports discovery, sampling, and metadata enrichment of GitHub repositories for research use" {
                ui = webui "User Inteface" "Enables researchers to perform core research tasks"
                wa = webapp "Web Application" "Provides a REST API, delivering the system’s core functionality"
                db = database "Database" "Stores project metadata including repository information, sampling data, and test coverage metrics"
            }
        }

        ss2 = softwareSystem "GitHub Open Source Ecosystem" "Enables developers to share, maintain, and evolve open-source code collaboratively"
        
        u -> ss1.ui "Searches, samples, and enriches repo data" {
            tag "ss-rel"
        }
        u -> ss1.ui "Searches, samples, and enriches repo data on browser using" "HTML/HTTPS:8080" {
            tag "c-rel"
        }

        ss1.ui -> ss1.wa "Makes synchronous REST API calls to" "JSON/HTTPS:8080"
        ss1.wa -> ss1.db "Reads from and writes to" "JDBC:5432" 

        ss1.wa -> ss2 "Searches open-source repositories via GitHub API" {
            tag "ss-rel"
        }

        ss1.wa -> ss2 "Searches for repositories making synchronous REST API calls to" "JSON/HTTPS" {
            tag "c-rel"
        }        
    }

    views {
        systemContext ss1 "Diagram-1" {
            include *
            exclude "relationship.tag==c-rel"
            autolayout lr
        }

        container ss1 "Diagram-2" {
            include *
            exclude "relationship.tag==ss-rel"
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