#!/bin/bash

echo "Executando mvn clean install......................................."
mvn clean install -DskipTests

echo "Gerando e enviando imagem....................................................."
mvn dockerfile:build dockerfile:push
