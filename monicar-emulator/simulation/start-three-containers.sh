#!/bin/bash

set -e

echo "🛠️ Gradle 프로젝트 빌드 중..."
../../gradlew :monicar-emulator:build --project-dir ../..

echo "🐳 Docker Compose 빌드 및 컨테이너 실행..."
docker compose up --build