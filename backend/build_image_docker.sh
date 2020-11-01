#!/bin/bash

USER=$1
PASS=$2

echo "Validando usuários................................................."
if [ "x$USER" = "x" ]; then
    echo "Informe o usuário do docker hub"
    exit
fi

if [ "x$PASS" = "x" ]; then
    echo "Informe a senha do docker hub"
    exit
fi

echo "Executando mvn clean install......................................."
mvn clean install -DskipTests

echo "Gerando imagem....................................................."
docker build -t $USER/desafio-tecnico-softplan --build-arg JAR_FILE=target/*.jar .

echo "Logando no docker hub.............................................."
docker login -u $USER -p $PASS

echo "Enviando imagem...................................................."
docker push $USER/desafio-tecnico-softplan