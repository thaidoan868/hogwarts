# This terrafrom script updates the existing hogwarts task definition
# and it is used in the CD pipeline

terraform {
  required_version = ">= 1.5.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.region
}

# Read the current task def (to preserve env, ports, etc.)
data "aws_ecs_task_definition" "current" {
  task_definition = var.family_name
}

resource "aws_ecs_task_definition" "hogwarts-app" {
  family                   = data.aws_ecs_task_definition.current.family
  network_mode             = "awsvpc"
  requires_compatibilities = ["EC2"]
  container_definitions = jsonencode([{
    name         = var.container_name
    image        = var.docker_image
    cpu          = 256
    memory       = 512
    essential    = true
    portMappings = [{ containerPort = 8081, protocol = "tcp" }]
    environment = [
      {
        name  = "SPRING_PROFILES_ACTIVE",
        value = "staging"
      }
    ]
  }])
}
