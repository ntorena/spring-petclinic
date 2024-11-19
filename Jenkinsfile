pipeline {
    agent any
    environment {
        DOCKER_COMPOSE = '/usr/local/bin/docker-compose'  // Ruta al docker-compose del host
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'Clonando el repositorio...'
                checkout scm  // Clona el repositorio donde está tu código
            }
        }
        
        stage('Build') {
            steps {
                echo 'Construyendo la aplicación con Docker Compose...'
                script {
                    // Aquí ejecutas docker-compose en el host para levantar los contenedores
                    sh "${DOCKER_COMPOSE} -f docker-compose.yml up --build -d"
                }
            }
        }
        
        stage('Test') {
            steps {
                echo 'Ejecutando pruebas dentro del contenedor...'
                script {
                    // Ejecuta pruebas dentro del contenedor de la aplicación
                    sh "${DOCKER_COMPOSE} exec <nombre_del_contenedor> mvn test"
                }
            }
        }

        stage('Package') {
            steps {
                echo 'Empaquetando la aplicación dentro del contenedor...'
                script {
                    // Empaque la aplicación dentro del contenedor de la aplicación
                    sh "${DOCKER_COMPOSE} exec <nombre_del_contenedor> mvn package"
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Desplegando la aplicación en contenedor...'
                script {
                    // Levanta la aplicación en el contenedor
                    sh "${DOCKER_COMPOSE} up -d"
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
