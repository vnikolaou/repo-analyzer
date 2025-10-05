workspace "Repo-Analyzer" "Automating the selection and sampling of GitHub repositories for further research" {

    !identifiers hierarchical

    model {

        archetypes {
            webapp = container {
                technology "Java 17 / SpringBoot 3.4.7"
                tag "API"
            }

            webui = container {
                technology "Angular 18 / Typescript 5.4.5"
                tag "UI"
            }

            database = container {
                technology "PostgreSQL v15.11"
                tag "Database"
            }
          
        }

        u = person "Researcher" "Explores and curates open-source repositories to build datasets for empirical research"
        
        group "Personal Use [Environment]"  {
            ss1 = softwareSystem "Repo-Analyzer" "Supports discovery, sampling, and metadata enrichment of GitHub repositories for research use" {
                ui = webui "User Inteface" "Enables researchers to perform core research tasks"
                wa = webapp "Web Application" "Provides a REST API, delivering the system’s core functionality"
                db = database "Database" "Stores project metadata including repository information, sampling data, and test coverage metrics"
                dbc = database "Database1" "Stores project metadata including repository information, sampling data, and test coverage metrics" {
                    tag "d-rel"
				}              
            }
        }

        ss2 = softwareSystem "GitHub Open Source Ecosystem" "Enables developers to share, maintain, and evolve open-source code collaboratively"
        
        u -> ss1.ui "Searches, samples, and enriches repo data" {
            tag "ss-rel"
        }
        u -> ss1.ui "Searches, samples, and enriches repo data on browser using" "HTTPS" {
            tag "c-rel1"
        }

        ss1.ui -> ss1.wa "Makes synchronous REST API calls to" "JSON/HTTPS" {
            tag "c-rel1"
        }
        
        ss1.wa -> ss1.db "Reads from and writes to" "JDBC" {
            tag "c-rel1"
        }
        ss1.wa -> ss1.dbc "Reads from and writes to" "JDBC:5432" {
            tag "d-rel"
        }
        
        ss1.wa -> ss2 "Searches open-source repositories via GitHub API" {
            tag "ss-rel"
        }

        ss1.wa -> ss2 "Searches for repositories making synchronous REST API calls to" "JSON/HTTPS" {
            tag "c-rel1"
        }   
        
    	ss3 = softwareSystem "Web Client" "User’s browser for interacting with Repo-Analyzer" {
    	    tag "d-rel"
			br = container "Single Page Application" "Provides all the functionality to researchers via their web browser" "Javascript / Angular"
        }
        
        local = deploymentEnvironment "Running Locally" {
            deploymentNode "Personal Laptop" {
                technology "Windows / Macos / Linux"
                deploymentNode "Web Browser" {
        		    technology "Chrome / Firefox / Safari / Edge"
			        containerInstance ss3.br
			        ss1.ui -> ss3.br "Delivers to the researcher's web browser" {
                        tag "d-rel"
                    }
			        ss3.br -> ss1.wa "Makes synchronous REST API calls to" "JSON/HTTPS:8080"{
			            tag "d-rel"
			        }
                }
                
                deploymentNode "Java Virtual Machine" {
                    technology "Spring Boot Runtime"
	                deploymentNode "Repo-Analyzer Executable" {
	                   technology "JAR"
	                   containerInstance ss1.ui
	                   containerInstance ss1.wa
	                }
                }
                
                deploymentNode "Database Server" {
                    technology "PostgreSQL"
                    containerInstance ss1.dbc
                }
            }
        }    
    }

    views {
        systemContext ss1 "Diagram-1" {
            include *
            exclude "relationship.tag==c-rel1" "relationship.tag==d-rel" "element.tag==d-rel"
            autolayout lr
        }

        container ss1 "Diagram-2" {
            include *
            exclude "relationship.tag==ss-rel" "relationship.tag==d-rel" "element.tag==d-rel"
            autolayout lr
        }

        deployment * local {
            include *
            exclude "relationship.tag==c-rel1"
            autoLayout lr
        }
        
        
        styles {
		    element "Element" {
		        color #000000   
		    }
		    element "Person" {
		        background #05527d
		        shape person
		        color #ffffff   
		    }
		    element "Software System" {
		        background #066296
		        color #ffffff
		    }
		    element "Container" {
		        background #0773af
		        color #ffffff
		        
		    }
		    element "Database" {
		        shape cylinder
		        background #0773af
		        color #ffffff
		    }
		    element "Deployment Node" {
		        background #f5f5f5
		        color #000000
		    }
		    element "Infrastructure Node" {
		        background #dddddd
		        color #000000
		    }
        }
    }


}