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
                git branch: 'main', url: 'https://github.com/ntorena/spring-clinic-test-automation.git'
            }
        }

        stage('Setup Selenium Standalone') {
            steps {
                echo 'Levantando contenedor Selenium Standalone...'
                script {
                    sh '''
                    docker run -d --rm --network bridge --name selenium-chrome -p 4444:4444 seleniarm/standalone-chromium:latest
                    '''
                }
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Ejecutando pruebas automatizadas...'
                script {
                    sh '''
                    mvn clean test
                    '''
                }
            }
        }

        stage('Teardown Selenium') {
            steps {
                echo 'Eliminando contenedor de Selenium...'
                script {
                    sh '''
                    docker stop selenium-chrome
                    docker rm selenium-chrome
                    '''
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
