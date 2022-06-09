pipeline {
  agent {
        kubernetes {
yaml """
apiVersion: v1
kind: Pod
metadata:
  name: jenkins-slave
spec:
  containers:
  - name: jnlp
    image: jenkins/inbound-agent:4.10-3-jdk11
    imagePullPolicy: IfNotPresent
    tty: true
  - name: maven
    image: maven:3.8.5-eclipse-temurin-17
    imagePullPolicy: IfNotPresent
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /root/.m2/repository
      name: m2
  - name: tools
    image: ghcr.io/eve-online-tools/jenkins-tools:0.2
    imagePullPolicy: IfNotPresent
    command:
    - cat
    tty: true
  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    imagePullPolicy: IfNotPresent
    command:
      - /busybox/sh
      - "-c"
    args:
      - /busybox/cat
    tty: true
    volumeMounts:
      - name: jenkins-docker-cfg
        mountPath: /kaniko/.docker
  volumes:
    - name: m2
      persistentVolumeClaim:
        claimName: cache
    - name: jenkins-docker-cfg
      projected:
        sources:
        - secret:
            name: externalregistry
            items:
              - key: .dockerconfigjson
                path: config.json
  restartPolicy: Never
  nodeSelector:
    kubernetes.io/arch: amd64
  imagePullSecrets:
    - name: externalregistry
"""
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
          container('kaniko') {
              sh "/kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination $TARGET_REGISTRY/eve-sde:$VERSION --cleanup"
          }
        }
      }

      stage('create & push sde-database-docker-image') {
          steps {
            container('kaniko') {
                sh "/kaniko/executor --dockerfile `pwd`/Dockerfile-SDEDatabase --context `pwd` --destination $TARGET_REGISTRY/eve-sde-mariadb:$VERSION --cleanup"
            }
          }
        }
  }
}
