pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Compilando la aplicación con Docker Compose...'
                sh 'docker-compose -f docker-compose.yml up --build'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Ejecutando pruebas dentro del contenedor...'
                sh 'docker exec -t <nombre_del_contenedor> mvn test'
            }
        }
        
        stage('Package') {
            steps {
                echo 'Empaquetando la aplicación...'
                sh 'docker exec -t <nombre_del_contenedor> mvn package'
            }
        }

        stage('Run Application') {
            steps {
                echo 'Desplegando la aplicación...'
                sh 'docker-compose up -d'
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
