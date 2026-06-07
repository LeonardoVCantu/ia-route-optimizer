#!/bin/sh

echo "########## INICIANDO SETUP LOCALSTACK ##########"

# Variáveis locais para o script
K="test"
S="test"
R="us-east-1"


# 2. Cria a fila principal com a política de redirecionamento (Redrive Policy)
awslocal sqs create-queue --queue-name pedidos-rota --attributes '{
  "RedrivePolicy": "{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:pedidos-rota-dlq\",\"maxReceiveCount\":\"3\"}",
  "VisibilityTimeout": "30"
}'


echo "Criando filas..."
AWS_ACCESS_KEY_ID=$K AWS_SECRET_ACCESS_KEY=$S aws --endpoint-url=http://localhost:4566 sqs create-queue  --queue-name fila-pedidos-rota-dlq  --region $R

AWS_ACCESS_KEY_ID=$K AWS_SECRET_ACCESS_KEY=$S aws --endpoint-url=http://localhost:4566 sqs create-queue  --queue-name fila-pedidos-rota  --region $R \
  --attributes '{
    "RedrivePolicy": "{\"deadLetterTargetArn\":\"arn:aws:sqs:'$R':000000000000:fila-pedidos-rota-dlq\",\"maxReceiveCount\":\"3\"}",
    "VisibilityTimeout": "30"
  }'

AWS_ACCESS_KEY_ID=$K AWS_SECRET_ACCESS_KEY=$S aws --endpoint-url=http://localhost:4566 sqs create-queue  --queue-name fila-rotas-finalizadas  --region $R

echo "Criando bucket..."
AWS_ACCESS_KEY_ID=$K AWS_SECRET_ACCESS_KEY=$S aws --endpoint-url=http://localhost:4566 s3 mb s3://bucket-resultados-rotas --region $R

echo "Criando segredos..."
AWS_ACCESS_KEY_ID=$K AWS_SECRET_ACCESS_KEY=$S aws --endpoint-url=http://localhost:4566 secretsmanager create-secret \
    --name db-logistica-secret \
    --secret-string '{"db_user":"user","db_pass":"password"}' --region $R

echo "Configurando Parameter Store..."
AWS_ACCESS_KEY_ID=$K AWS_SECRET_ACCESS_KEY=$S aws --endpoint-url=http://localhost:4566 ssm put-parameter \
    --name "/logistica/db/url" \
    --value "jdbc:postgresql://localhost:5432/route_db" \
    --type "String" --region $R

echo "########## SETUP FINALIZADO ##########"