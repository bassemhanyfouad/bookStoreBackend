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
        stage('Init') {
            steps {
                sh 'printenv'
                script {
                    // we need the real commit id from our branch
                    // since jenkins merges the target branch into our branch the result might be a new commit
                    // the following code evaluates the git history and
                    // From https://github.com/vdice/controller-sdk-go/blob/bc4e539147d9a430a5db2b708fce0d4823a36128/Jenkinsfile
                    def mergeCommitParents = sh(returnStdout: true, script: "git rev-parse HEAD | git log --pretty=%P -n 1 --date-order").trim()
                    def gitCommitIdLength = 40
                    if (mergeCommitParents.length() > gitCommitIdLength) {
                        echo 'More than one merge commit parent signifies that the merge commit is not the PR commit'
                        ORIGINAL_GIT_COMMIT = mergeCommitParents.take(40)
                        echo "ORIGINAL_GIT_COMMIT is '${ORIGINAL_GIT_COMMIT}' instead of merge commit'${GIT_COMMIT}'"
                    } else {
                        echo 'Only one merge commit parent signifies that the merge commit is also the PR commit'
                        echo "ORIGINAL_GIT_COMMIT is the same as GIT_COMMIT '${GIT_COMMIT}'"
                        ORIGINAL_GIT_COMMIT = "${GIT_COMMIT}"
                    }
                }

            }
        }

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
                    BRANCH_TAG = "${BRANCH_NAME}".replace('/',"-")
                    dockerImage = docker.build registry + ':'+BRANCH_TAG
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