pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "hsounasellami/student-management:1.0.0"
        REGISTRY_CREDENTIALS = 'dockertoken'
    }

    stages {

        stage('Checkout') {
            steps {
                git url: 'https://github.com/hsounaSellami/pipetest2.git', branch: 'main'
            }
        }

          stage('Build & Test') {
                     steps {
                         sh 'mvn clean test jacoco:report'
                     }
                     post {
                         always {
                             junit 'target/surefire-reports/*.xml'
                             publishHTML([
                                 allowMissing: false,
                                 alwaysLinkToLastBuild: true,
                                 keepAll: true,
                                 reportDir: 'target/site/jacoco',
                                 reportFiles: 'index.html',
                                 reportName: 'JaCoCo Coverage Report'
                             ])
                         }
                     }
                 }

                 stage('Package JAR') {
                     steps {
                         sh 'mvn package -DskipTests'
                         sh 'ls -l target'   // DEBUG: show JAR file
                     }
                 }
            stage('MVN SONARQUBE') {
            steps {
                withCredentials([string(credentialsId: 'sonarQ', variable: 'SONAR_TOKEN')]) {
                    sh """
                        mvn sonar:sonar \
                          -Dsonar.login=$SONAR_TOKEN \
                          -Dsonar.host.url=http://localhost:9000/
                    """
                }
            }
        }


        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Docker Hub Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: REGISTRY_CREDENTIALS, usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh "echo $PASS | docker login -u $USER --password-stdin"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                sh "docker push ${DOCKER_IMAGE}"
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                kubectl --insecure-skip-tls-verify=true apply -f k8s/namespace.yaml
                kubectl --insecure-skip-tls-verify=true apply -f k8s/mysql.yaml
                kubectl --insecure-skip-tls-verify=true apply -f k8s/spring.yaml
                '''
            }
        }

    }
    post {
            always {
                echo 'Pipeline finished.'
            }
        }
}
