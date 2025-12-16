pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "hsounasellami/student-management:${BUILD_NUMBER}"
        DOCKER_IMAGE_LATEST = "hsounasellami/student-management:latest"
        REGISTRY_CREDENTIALS = 'dockertoken'
        KUBECONFIG = '/var/lib/jenkins/.kube/config'
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
                sh 'ls -l target'
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
                sh "docker build -t ${DOCKER_IMAGE} -t ${DOCKER_IMAGE_LATEST} ."
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
                sh "docker push ${DOCKER_IMAGE_LATEST}"
            }
        }

        stage('Deploy MySQL to Kubernetes') {
            steps {
                script {
                    // Vérifier si MySQL existe déjà
                    def mysqlExists = sh(
                        script: 'kubectl get deployment mysql -n devops 2>/dev/null',
                        returnStatus: true
                    )

                    if (mysqlExists != 0) {
                        echo "MySQL n'existe pas, création..."
                        sh 'kubectl apply -f k8s/mysql-deployment.yaml'

                        // Attendre que MySQL soit prêt
                        echo "Attente du démarrage de MySQL..."
                        sh '''
                            kubectl wait --for=condition=ready pod \
                              -l app=mysql \
                              -n devops \
                              --timeout=300s
                        '''
                        echo "MySQL est prêt !"
                    } else {
                        echo "MySQL existe déjà, on passe cette étape"
                    }
                }
            }
        }

        stage('Deploy Spring Boot Config') {
            steps {
                sh '''
                    kubectl apply -f k8s/spring-configmap.yaml
                    kubectl apply -f k8s/spring-secret.yaml
                '''
            }
        }

        stage('Update Spring Boot Image') {
            steps {
                script {
                    // Mettre à jour l'image dans le deployment
                    sh """
                        sed -i 's|image: hsounasellami/student-management:.*|image: ${DOCKER_IMAGE}|g' k8s/spring-deployment.yaml
                    """
                }
            }
        }

        stage('Deploy Spring Boot to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/spring-deployment.yaml'

                // Attendre que le déploiement soit terminé
                sh '''
                    kubectl rollout status deployment/student-management -n devops --timeout=300s
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                sh '''
                    echo "=== Pods Status ==="
                    kubectl get pods -n devops

                    echo ""
                    echo "=== Services ==="
                    kubectl get svc -n devops

                    echo ""
                    echo "=== Application URL ==="
                    minikube service spring-service -n devops --url
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline terminé avec succès !'
            echo '🚀 Application déployée sur Kubernetes'
        }
        failure {
            echo '❌ Pipeline échoué'
        }
        always {
            echo '🧹 Nettoyage...'
            sh 'docker system prune -f'
        }
    }
}