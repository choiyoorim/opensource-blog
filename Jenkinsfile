pipeline {
    agent none
    options { skipDefaultCheckout(true) }
    environment {
            PROJECT_ID = 'opensource-team'
            CLUSTER_NAME = 'k8s'
            LOCATION = 'asia-northeast3-a'
            CREDENTIALS_ID = '1ae4e1a9-f5e1-4ec8-907d-aef2248d041e'
    }


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
                script {
                    myapp = docker.build("choiyoorim/blog:${env.BUILD_ID}")
                }
            }
        }
        stage('Docker push image') {
            agent any
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                            myapp.push("latest")
                            myapp.push("${env.BUILD_ID}")
                        }
                    }
            }
        }
        stage('Docker run') {
            agent any
            when {
            		branch 'main'
            }
            steps{
                sh "sed -i 's/blog:latest/blog:${env.BUILD_ID}/g' Deployment.yaml"
                step([$class: 'KubernetesEngineBuilder', projectId: env.PROJECT_ID, clusterName: env.CLUSTER_NAME, location: env.LOCATION, manifestPattern: 'Deployment.yaml', credentialsId: env.CREDENTIALS_ID, verifyDeployments: true])
            }
        }

        stage('Test Docker run') {
            agent any
            when {
                    branch 'develop'
            }
            steps{
                sh "sed -i 's/testblog:latest/testblog:${env.BUILD_ID}/g' TestDeployment.yaml"
                step([$class: 'KubernetesEngineBuilder', projectId: env.PROJECT_ID, clusterName: env.CLUSTER_NAME, location: env.LOCATION, manifestPattern: 'TestDeployment.yaml', credentialsId: env.CREDENTIALS_ID, verifyDeployments: true])
            }
        }
    }
}