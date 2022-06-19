pipeline {
  agent {
        kubernetes {
            yamlFile 'KubernetesPod.yaml'
            //workspaceVolume: persistentVolumeClaimWorkspaceVolume(claimName: 'workspace', readOnly: false)
        }
  }
    
  environment {
    IMAGE = readMavenPom().getArtifactId()
    VERSION = readMavenPom().getVersion()
    BUILD_RELEASE_VERSION = readMavenPom().getVersion().replace("-SNAPSHOT", "")
    IS_SNAPSHOT = readMavenPom().getVersion().endsWith("-SNAPSHOT")
    TARGET_REGISTRY = "ghcr.io/eve-online-tools"
  }

  stages {
    stage('Build') {
      steps {
        container('maven') {
  	      sh 'mvn --version'
  	        configFileProvider([configFile(fileId: 'maven-settings.xml', variable: 'MAVEN_SETTINGS')]) {
  	            sh 'mvn -s $MAVEN_SETTINGS -U -T 1C clean package'
  	        }
        }
     }
   }

     stage('create & push docker-image') {
        steps {
          container('dind') {
              sh "docker buildx create --use"
              script {
                  if (env.IS_SNAPSHOT) {
                    sh "docker buildx build --platform linux/amd64,linux/arm64/v8 -f `pwd`/Dockerfile -t $TARGET_REGISTRY/eve-sde:$BUILD_RELEASE_VERSION-${env.GIT_COMMIT} --push `pwd`"
                  } else {
                    sh "docker buildx build --platform linux/amd64,linux/arm64/v8 -f `pwd`/Dockerfile -t $TARGET_REGISTRY/eve-sde:$BUILD_RELEASE_VERSION --push `pwd`"
                  }
              }

          }
        }
      }

     stage('deploy') {
           steps {
             container('tools') {

                 withCredentials([string(credentialsId: 'k8s-server-url', variable: 'SERVER_URL')]) {
                     withKubeConfig([credentialsId: "k8s-credentials", serverUrl: "$SERVER_URL"]) {
                          script {
                                if (env.IS_SNAPSHOT) {
                                  sh 'helm -n $NAMESPACE upgrade -i eve-sde `pwd`/src/main/helm/eve-sde --set image.tag=$TARGET_REGISTRY/eve-sde:$BUILD_RELEASE_VERSION-${env.GIT_COMMIT} --wait'
                                } else {
                                  sh 'helm -n $NAMESPACE upgrade -i eve-sde `pwd`/src/main/helm/eve-sde --set image.tag=$TARGET_REGISTRY/eve-sde:$BUILD_RELEASE_VERSION --wait'
                                }
                            }

                     }
                 }
             }
           }
       }
  }
}
