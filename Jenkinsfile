pipeline {
//    environment {
//        registry = "bassemhanyfouad/i-bold"
//        registryCredential = 'd06ea51d-68f8-4256-8b43-93d09e626020'
//    }

    //who will run this pipeline
    agent {
        //uses the docker plugin to create a maven contain to build the project inside it.. and
        //save the downloaded artifacts in my local repository
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'

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
                sh 'mvn verify'
            }
            post {
                //always publish the test result in the target/surefire-reports folder
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

//        stage('Building image') {
//            steps{
//                script {
//                    dockerImage = docker.build registry + ':$BUILD_NUMBER'
//                }
//            }
//        }
//        stage('Deploy Image') {
//            steps{
//                script {
//                    docker.withRegistry( '', registryCredential ) {
//                        dockerImage.push()
//                    }
//                }
//            }
//        }
    }
}