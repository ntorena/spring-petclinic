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

        stage('Run Automated Tests') {
            steps {
                echo 'Ejecutando pruebas automatizadas en un contenedor separado...'
                script {
                    // Crear contenedor de pruebas dinámicamente
                sh """
    # Verifica si existe un contenedor llamado 'selenium-test-1'
    if [ \$(docker ps -aq -f name=selenium-test-1) ]; then
        # Si el contenedor existe, verifica si está detenido
        if [ \$(docker ps -q -f name=selenium-test-1) ]; then
            echo "El contenedor 'selenium-test-1' ya está en ejecución."
        else
            echo "El contenedor 'selenium-test-1' existe pero está detenido. Iniciándolo..."
            docker start selenium-test-1
        fi
    else
        # Si el contenedor no existe, créalo
        echo "El contenedor 'selenium-test-1' no existe. Creándolo..."
        docker run --name selenium-test-1 \
            --network bridge \
            -v "/var/jenkins_home/workspace/Spring-Petclinic Pipeline:/tests" \
            -d selenium-maven-arm bash
    fi

    # Ejecuta los tests en el contenedor
    docker exec selenium-test-1 bash -c 'cd "/tests/Spring-Petclinic Pipeline" && mvn clean test'
"""
                }
            }
        }

    //     stage('Run Automated Tests') {
    //         steps {
    //             script {
    //                 git branch: 'main', url: 'https://github.com/ntorena/spring-clinic-test-automation.git'

    //                 echo "Ejecutando pruebas automatizadas con Maven..."
    //                     sh 'mvn clean test'

    //                 echo "Generando reportes de pruebas..."
    //                     sh 'mvn surefire-report:report'
    //         }
    //     }
    // }

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
