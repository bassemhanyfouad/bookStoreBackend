pipeline {
    environment {
        registry = "bassemhanyfouad/i-bold"
        //configured credential on jenkins.. this credential contains my username and password on dockerhub
        registryCredential = 'dockerhub'
    }

    //who will run this pipeline
    agent any
    //our stages
    stages {
        //first stage
        stage('Build') {
            agent {
                //uses the docker plugin to create a maven contain to build the project inside it.. and
                //save the downloaded artifacts in my local repository
                //this agent is only needed for the build
                docker {
                    image 'maven:3-alpine'
                    args '-v /root/.m2:/root/.m2'

                }
            }
            steps {
                //build the project inside the maven container
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            agent {
                //uses the docker plugin to create a maven contain to build the project inside it.. and
                //save the downloaded artifacts in my local repository
                docker {
                    image 'maven:3-alpine'
                    args '-v /root/.m2:/root/.m2'

                }
            }
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

        stage('Building image') {
            steps{
                sh 'printenv'
                script {
                    dockerImage = docker.build registry + ':30'
                }
            }
        }
        stage('Deploy Image') {
            steps{
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
    }
}