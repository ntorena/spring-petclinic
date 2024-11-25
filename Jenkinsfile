pipeline {
    agent any

    stages {

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
