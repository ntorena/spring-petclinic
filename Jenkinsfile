pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Compilando la aplicación con Docker Compose...'
                sh 'docker-compose up --build test-container'
            }
        }

        stage('Test') {
            steps {
                echo 'Ejecutando pruebas con Docker Compose...'
                sh 'docker-compose run --rm test-container'
            }
        }

        stage('Package and Deploy') {
            steps {
                echo 'Empaquetando y desplegando la aplicación...'
                sh '''
                    docker-compose up --build -d app-server
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completado con éxito.'
        }
        failure {
            echo 'El pipeline ha fallado.'
        }
    }
}
