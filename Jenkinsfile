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

        stage('Git Sync') {
            steps {
                script {
                    sh 'docker-compose up git-sync'
                 }
            }
        }

        stage('Run Cypress Tests') {
            steps {
                echo 'Ejecutando pruebas automatizadas en Cypress...'
                    script {
                        sh 'docker-compose --profile automation up'
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
        always {
    always {
        sh 'docker cp spring-petclinicpipeline-cypress-1:/app/allure-results "/var/jenkins_home/workspace/Spring-Petclinic Pipeline/results"'
        allure includeProperties: false, jdk: '', results: [[path: '/var/jenkins_home/workspace/Spring-Petclinic Pipeline/results']]
    }
                }
        success {
            echo 'El pipeline ha finalizado con éxito.'
        }
        failure {
            echo 'El pipeline ha fallado.'
            }
        }
}
