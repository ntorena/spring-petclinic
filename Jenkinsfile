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
            sh "docker-compose --profile testing -f docker-compose.yml up --build -d"
            
            sh "docker-compose logs test-container"
            
            }
        }
        }

        stage('Verify Deployment') {
            steps {
                echo 'Verificando el despliegue...'
                script {
                    sh 'docker ps'
                    sh 'curl -I http://localhost:8080'
                }
            }
        }

        stage('Checkout') {
            steps {
                // Clonar el repositorio del proyecto
                git branch: 'main', url: 'https://github.com/ntorena/spring-clinic-test-automation.git'
            }
        }

        stage('Run Automated Tests') {
            steps {
                script {
                    // Ejecutar pruebas en el contenedor
                    docker.image('spring-petclinic-main-test-runner').inside {
                        sh 'mvn clean test'
                    }
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
}
