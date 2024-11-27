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
    if [ \$(docker ps -aq -f name=selenium-maven-arm-container) ]; then
        docker start selenium-maven-arm-container
        docker exec selenium-maven-arm-container bash -c 'cd "/tests/Spring-Petclinic Pipeline" && mvn clean test'
    else
        docker run --name selenium-maven-arm-container \
            --network bridge \
            -v "/var/jenkins_home/workspace/Spring-Petclinic Pipeline:/tests" \
            -d selenium-maven-arm bash
        docker exec selenium-maven-arm-container bash -c 'cd "/tests/Spring-Petclinic Pipeline" && mvn clean test'
    fi
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
