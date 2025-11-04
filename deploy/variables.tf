# Variables without deafault values
variable "docker_image"           {
  type = string
}

# Variables with default values
variable "region" {
  description = "AWS region to target"
  type        = string
  default     = "ap-southeast-1"
}

variable "family_name"     {
  type = string
  default = "hogwarts-app-task"
}
variable "container_name"  {
  type = string
  default = "hogwarts-app"
}