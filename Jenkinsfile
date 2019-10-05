pipeline {
    //who will run this pipeline
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    //the parameters to be passed when running this pipeline
    parameters {
        string(name: 'greeting', defaultValue: 'Hello', description: 'How should I greet?')

    }
    //our stages
    stages {
        //first stage
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}