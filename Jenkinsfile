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
                            publishHTML([allowMissing: false,
                                         alwaysLinkToLastBuild: true,
                                         keepAll: true,
                                         reportDir: 'target/site/jacoco',
                                         reportFiles: 'index.html',
                                         reportName: 'JaCoCo Coverage Report'])
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
    }
    post {
            always {

                echo 'Pipeline finished.'
            }
            success {
                echo 'Pipeline finished successfully!'
            }
            failure {
                echo 'Pipeline failed. Check the console output lose.'
            }
        }
}
