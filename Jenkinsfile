pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Construyendo la aplicación con Docker Compose...'
                script {
                    sh "docker-compose --profile application -f docker-compose.yml up --build -d"
                }
            }
        }
        
        stage('Run Unit Tests') {
            steps {
                echo 'Ejecutando pruebas dentro del contenedor...'
               script {
                    sh "mvn test"
            
                }
            }
        }

        stage('Clone Test Repository') {
            steps {
                echo 'Clonando el repositorio de pruebas automatizadas...'
                git branch: 'main', url: 'https://github.com/ntorena/spring-clinic-automation-cypress.git'
            }
        }

        stage('Run Cypress Tests') {
            steps {
                echo 'Ejecutando pruebas automatizadas en Cypress...'
                    script {
                        sh 'docker run --rm -v .:/app -w /app cypress/included'
                }
            }
        }


        stage('Cleanup') {
            steps {
                echo 'Limpiando recursos antiguos...'
                script {
                    sh 'docker-compose down'
                }
            }
        }
    }
    post {
        success {
            echo 'El pipeline ha finalizado con éxito.'
        }
        failure {
            echo 'El pipeline ha fallado.'
            }
        }
}
