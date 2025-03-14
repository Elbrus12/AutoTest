pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Elbrus12/AutoTest.git', branch: 'master'
            }
        }
        stage('Build and Test') {
            steps {
                withMaven(maven: 'Maven 3') {
                    sh 'mvn clean test' // Запуск тестов с помощью Maven
                }
            }
        }
        stage('Publish Results') {
            steps {
                junit testResults: 'target/surefire-reports/*.xml' // Публикация результатов тестов в формате JUnit XML
            }
        }
    }
}