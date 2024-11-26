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
                    sh "docker exec spring-petclinicpipeline-app-server-1 mvn test"
            
                }
            }
        }

        stage('Run Automated Tests') {
            steps {
                script {
                    echo "Clonando el repositorio de pruebas automatizadas..."
                    git branch: 'main', url: 'https://github.com/ntorena/spring-clinic-test-automation.git'

                    echo "Ejecutando pruebas automatizadas con Maven..."
                    dir('spring-clinic-test-automation') {
                        sh 'mvn clean test'
            }

                    echo "Generando reportes de pruebas..."
                    dir('spring-clinic-test-automation') {
                        sh 'mvn surefire-report:report'
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
