pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'github-credentials',
                    url: 'https://github.com/devbox4u/donationapp.git'
            }
        }
        stage('Build and Test') {
            steps {
                sh './mvnw clean package'
            }
        }
        stage('Code Quality Check') {
            steps {
                sh './mvnw checkstyle:check'
            }
        }
    }
}