#!/bin/bash

SONAR_URL="http://localhost:9000"
OLD_PASS="admin"
NEW_PASS="Password123!"
TOKEN_NAME="local-token"

until curl -s -u admin:$OLD_PASS "$SONAR_URL/api/system/status" | grep -q '"status":"UP"'; do
  sleep 5
done

curl -s -u admin:$OLD_PASS \
  -X POST "$SONAR_URL/api/users/change_password" \
  -d "login=admin&previousPassword=$OLD_PASS&password=$NEW_PASS" > /dev/null

TOKEN=$(curl -s -u admin:$NEW_PASS \
  -X POST "$SONAR_URL/api/user_tokens/generate" \
  -d "name=$TOKEN_NAME" | grep -o '"token":"[^"]*"' | sed 's/.*"token":"\([^"]*\)".*/\1/')

if [ -n "$TOKEN" ] && [ "$TOKEN" != "null" ]; then
  echo "Token '$TOKEN' creado."
else
  echo "No se pudo crear el token."
  exit 1
fi
