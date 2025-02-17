#!/bin/bash

set -e

echo "🛠️ Gradle 프로젝트 빌드 중..."
../../gradlew :monicar-emulator:build --project-dir ../.. -x test

# .yml 확인하여 원하는 profile 이름으로 -f 뒤 값 수정 예) tomato -> banana
docker rm -f tomato 2>/dev/null || true

# 선택한 profile로 -t 뒤 값 수정 예) tomato-image -> banana-image
docker build -t tomato-image -f Dockerfile ../

# 선택한 profile로 --name 뒤 값과 마지막 xx-image 값 수정하기
docker run -d --name tomato \
            -p 8091:8091 \
            -e SPRING_PROFILES_ACTIVE=tomato \
            tomato-image