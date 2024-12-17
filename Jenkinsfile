pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/rncosta29/api-bank-microservice.git'
        SERVER_PATH = 'server'
        GATEWAY_PATH = 'gateway'
        MICROSERVICES_PATH = 'microservices'
    }

    stages {
        stage('Preparação do Ambiente') {
            steps {
                echo "Clonando ou atualizando repositório..."
                git url: "${REPO_URL}", branch: 'main', credentialsId: 'github-creds'
            }
        }

        stage('Build do Server') {
            steps {
                echo "Iniciando build da aplicação Server..."
                sh '''
                cd ${SERVER_PATH}
                mvn clean install -DskipTests
                java -jar target/server.jar &
                '''
            }
        }

        stage('Build do Gateway') {
            steps {
                echo "Iniciando build da aplicação Gateway..."
                sh '''
                cd ${GATEWAY_PATH}
                mvn clean install -DskipTests
                java -jar target/gateway.jar &
                '''
            }
        }

        stage('Build dos Microserviços') {
            steps {
                echo "Iniciando build dos Microserviços..."
                script {
                    def services = sh(script: "ls ${MICROSERVICES_PATH}", returnStdout: true).trim().split("\n")
                    services.each { service ->
                        echo "Iniciando build do microserviço: ${service}"
                        sh """
                        cd ${MICROSERVICES_PATH}/${service}
                        mvn clean install -DskipTests
                        java -jar target/${service}.jar &
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline finalizado com sucesso!"
        }
        failure {
            echo "Falha durante a execução do Pipeline!"
        }
    }
}
