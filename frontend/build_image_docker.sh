#!/bin/bash

USER=$1
PASS=$2

echo "Validando usuários................................................."
if [ "x$USER" = "x" ]; then
    echo "Informe o usuário do docker"
    exit
fi

if [ "x$PASS" = "x" ]; then
    echo "Informe a senha do docker"
    exit
fi

echo "Executando npm run build..........................................."
npm run build-prod

echo "Gerando imagem docker.............................................."
docker build -t $USER/desafio-tecnico-softplan-frontend .

echo "Logando no docker.................................................."
docker login -u $USER -p $PASS

echo "Enviando imagem...................................................."
docker push $USER/desafio-tecnico-softplan-frontend
