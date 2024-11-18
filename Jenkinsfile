pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Compilando la aplicación...'
                sh 'mvn clean install'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Ejecutando pruebas...'
                sh 'mvn test'
            }
        }
        
        stage('Package') {
            steps {
                echo 'Empaquetando la aplicación...'
                sh 'mvn package'
            }
        }

        stage('Run Application') {
            steps {
                echo 'Desplegando la aplicación...'
                sh 'java -jar target/*.jar'
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
