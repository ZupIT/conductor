@Library('ZupSharedLibs@v_orchestrator') _

node {

  try {

    buildWithCompose {
      composeFileName = "docker_zupme/docker-compose-ci.yml"
      composeService = "zupme-conductor"
      composeProjectName = "zupme-conductor"
    }

    buildDockerContainer {
      dockerRepositoryName = "zupme-conductor_server"
      dockerFileLocation = "./server.Dockerfile"
      team = "gateway"
      dockerRegistryGroup = "ZUPME"
    }

    buildDockerContainer {
      dockerRepositoryName = "zupme-conductor_ui"
      dockerFileLocation = "./ui.Dockerfile"
      team = "gateway"
      dockerRegistryGroup = "ZUPME"
    }

    deployDockerService {
      dockerRepositoryName = "zupme-conductor_server"
      dockerSwarmStack = "zupme-conductor"
      dockerService = "server"
      team = "gateway"
      dockerRegistryGroup = "ZUPME"
      dockerSwarmGroup = "ZUPME_QA"
    }

    deployDockerService {
      dockerRepositoryName = "zupme-conductor_ui"
      dockerSwarmStack = "zupme-conductor"
      dockerService = "ui"
      team = "gateway"
      dockerRegistryGroup = "ZUPME"
      dockerSwarmGroup = "ZUPME_QA"
    }

    deployDockerServiceK8s {
      microservice = "zupme-conductor_server"
      dockerk8sGroup = "ZUPME"
    }

    deployDockerServiceK8s {
      microservice = "zupme-conductor_ui"
      dockerk8sGroup = "ZUPME"
    }

    deleteReleaseBranch {}

  } catch (e) {

    notifyBuildStatus {
      buildStatus = "FAILED"
    }
    throw e

  }

}