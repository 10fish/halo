pipepline {
    agent {
        label 'master'
    }

    stages {
        stage('preparation') {
            steps {
                echo 'preparation...'
            }
        }
        stage('build') {
            steps {
                'gradle clean build'
            }
        }
    }

    post {
        success {
            echo 'success'
        }

        failure {
            echo 'failure'
        }
    }
}