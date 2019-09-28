pipeline {
    //who will run this pipeline
    agent any
    //the parameters to be passed when running this pipeline
    parameters {
        string(name: 'greeting', defaultValue: 'Hello', description: 'How should I greet?')

    }
    //our stages
    stages {
        //first stage
        stage ("Hello") {
            //stage steps
            steps {
                echo "${params.greeting} world"
            }
        }

    }
}