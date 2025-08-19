#!/bin/sh

if ! command -v fastlane &> /dev/null; then
  echo "🚀 Fastlane not found. Installing with Homebrew..."
  brew install fastlane
else
  echo "✅ Fastlane is already installed: $(fastlane --version)"
fi

if ! command -v fastlane &> /dev/null; then
  echo "🚀 Fastlane not found. Exit."
  exit 1
fi

# Check the current username
export GOOGLE_APPLICATION_CREDENTIALS=./tools/google-credentials-firebase-distribution.json
export WEBHOOK_URL="https://hooks.slack.com/services/..."
BUILD_FILE="app/build.gradle.kts"

# Create Version Code
start_time=$(date -j -f "%d/%m/%Y %H:%M:%S" "15/06/2021 00:00:00" "+%s")
current_time=$(date "+%s")
diff_seconds=$((current_time - start_time))
newVersionCode=$((diff_seconds / 60))

# Current Version Name and Code
versionCode=$(sed -n 's/.*versionCode *= *\([0-9]*\).*/\1/p' "$BUILD_FILE")
versionName=$(sed -n 's/.*versionName *= *"\(.*\)".*/\1/p' "$BUILD_FILE")
echo "versionCode: $newVersionCode"
echo "versionName: $versionName"

## Replace version code
sed -i '' -E "s/(versionCode *= *)[0-9]+/\1$newVersionCode/" "$BUILD_FILE"

fastlane staging_to_firebase

# Revert Version
sed -i '' -E "s/(versionCode *= *)[0-9]+/\1$versionCode/" "$BUILD_FILE"