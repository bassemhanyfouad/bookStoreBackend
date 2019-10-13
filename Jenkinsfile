pipeline {
    //who will run this pipeline
    agent {
        //uses the docker plugin to create a maven contain to build the project inside it.. and
        //save the downloaded artifacts in my local repository
        docker {
            image 'quay.io/testcontainers/dind-drone-plugin'

        }
    }
    //our stages
    stages {
        //first stage
        stage('Build') {
            steps {
                //build the project inside the maven container
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            steps {
                //run the unit tests
                sh 'mvn test'
            }
            post {
                //always publish the test result in the target/surefire-reports folder
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}