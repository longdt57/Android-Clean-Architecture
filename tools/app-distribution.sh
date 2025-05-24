#!/bin/sh

# Prompt user for the flavor
echo "Enter the build flavor (Staging, Prod): "
read FLAVOR

if [ -z "$FLAVOR" ]; then
  echo "Error: No flavor entered. Exiting."
  exit 1
fi

export GOOGLE_APPLICATION_CREDENTIALS=tools/google-credentials-firebase-distribution.json

## Create Version Code
start_time=$(date -j -f "%d/%m/%Y %H:%M:%S" "21/06/2021 00:00:00" "+%s")
current_time=$(date "+%s")
diff_seconds=$((current_time - start_time))
newVersionCode=$((diff_seconds / 60))

## Current Version Name and Code
BUILD_FILE="app/build.gradle.kts"
versionCode=$(sed -n 's/.*versionCode *= *\([0-9]*\).*/\1/p' "$BUILD_FILE")
versionName=$(sed -n 's/.*versionName *= *"\(.*\)".*/\1/p' "$BUILD_FILE")
echo "versionCode: $newVersionCode"
echo "versionName: $versionName"

## Replace version code
sed -i '' -E "s/(versionCode *= *)[0-9]+/\1$newVersionCode/" "$BUILD_FILE"

# Read release notes from file (make sure file exists)
FILE_NAME="release_notes.txt"
touch "$FILE_NAME"
git log -2 --no-merges --pretty=format:"%B%n" | sed '/^$/d' | awk '/\[Build\]/ {getline; next} 1' > "$FILE_NAME"
echo "$FILE_NAME is created"

if [ -f $FILE_NAME ]; then
  release_notes=$(<release_notes.txt)
else
  release_notes="(No release notes found)"
  sed -i '' -E "s/(versionCode *= *)[0-9]+/\1$versionCode/" "$BUILD_FILE"
  exit 1
fi

echo "Build and Publish to Firebase..."
gradle_output=$(./gradlew assemble${FLAVOR}Release appDistributionUpload${FLAVOR}Release)
echo "$gradle_output"
FIREBASE_URL=$(echo "$gradle_output" | grep -oE 'https://firebaseappdistribution\.googleapis\.com[^ ]+app\.apk[^ ]*' | head -n 1)

if [ -z "$FIREBASE_URL" ]; then
  echo "Error: Failed to upload the app to Firebase App Distribution. Please try again!"
  rm release_notes.txt
  sed -i '' -E "s/(versionCode *= *)[0-9]+/\1$versionCode/" "$BUILD_FILE"
  exit 1
fi

## Send Slack message
WEBHOOK_URL=""
JIRA_URL="https://jira.atlassian.net/browse/"
release_notes_with_links=$(echo "$release_notes" | sed -E "s/\[([A-Z]+-[0-9]+)\]/[<${JIRA_URL}\1|\1>]/g")

echo $release_notes_with_links

MESSAGE="✅ Build Android App Successfully! <${FIREBASE_URL}|$versionName ($newVersionCode)>\n\nRelease Notes:\n$release_notes_with_links"
payload=$(printf '{"text": "%s"}' "$MESSAGE")

# Send the notification using curl
# curl -X POST -H 'Content-type: application/json' --data "$payload" "$WEBHOOK_URL"
echo $MESSAGE

## Remove note file
rm release_notes.txt
## Revert version code
sed -i '' -E "s/(versionCode *= *)[0-9]+/\1$versionCode/" "$BUILD_FILE"
