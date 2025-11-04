output "task_def_arn" {
    description = "The ARN of the ECS task definition"
    value       = aws_ecs_task_definition.hogwarts-app.arn
}