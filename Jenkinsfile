pipeline {
    agent none
    options { skipDefaultCheckout(true) }
    stages {
        stage('Checkout repository') {
            agent any
            steps {
                checkout scm
            }
        }
        stage('Build and test') {
            agent {
                docker {
                    image 'gradle:7.6.3-jdk17-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'gradle clean build -x test'
            }
        }
        stage('Docker build') {
            agent any
            steps {
                sh 'docker build -t blog:latest .'
            }
        }
        stage('Docker run') {
            agent any
            steps {
                sh 'docker ps -f name=blog -q | xargs --no-run-if-empty docker container stop'
                sh 'docker container ls -a -fname=blog -q | xargs -r docker container rm'
                sh 'docker image prune -a -f'
                sh 'docker run -d --name blog -p 5001:8080 blog:latest'
            }
        }
    }
}