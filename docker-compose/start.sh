#!/bin/bash
echo "Updating imagens..................................................."
docker-compose pull
echo "Initializing containers............................................"
docker-compose up -d
echo "Containers!"
docker-compose ps
echo "..................................................................."
