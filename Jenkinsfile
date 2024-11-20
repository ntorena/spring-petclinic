pipeline {
    agent any
        stage('Build') {
            steps {
                echo 'Construyendo la aplicación con Docker Compose...'
                script {
                    sh "docker-compose -f docker-compose.yml up --profile application --build -d"
                }
            }
        }
        
        stage('Test') {
            steps {
                echo 'Ejecutando pruebas dentro del contenedor...'
                script {
                    sh "docker-compose -f docker-compose.yml up --profile testing --build -d"
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

