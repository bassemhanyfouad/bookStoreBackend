pipeline {
    //who will run this pipeline
    agent {
        //uses the docker plugin to create a maven contain to build the project inside it.. and
        //save the downloaded artifacts in my local repository
        docker {
            image 'maven:3-alpine'
            args '-v /root:/root -w /root -v /root/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock -p 5432:5432'

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