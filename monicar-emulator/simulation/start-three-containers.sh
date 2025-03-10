#!/bin/bash

set -e

echo "🛠️ Gradle 프로젝트 빌드 중..."
../../gradlew :monicar-emulator:build --project-dir ../.. -x test

echo "🗑️ 기존 컨테이너 중지 및 삭제..."
CONTAINERS=$(docker ps -aq --filter "name=tomato|banana|apple")

if [ -n "$CONTAINERS" ]; then
  docker rm -f $CONTAINERS
  echo "✅ 기존 컨테이너 삭제 완료!"
else
  echo "ℹ️ 삭제할 컨테이너 없음."
fi

echo "🐳 Docker Compose 빌드 및 컨테이너 실행..."
docker compose up --build