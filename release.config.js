module.exports = {
  plugins: [
    "@semantic-release/commit-analyzer",
    "@semantic-release/release-notes-generator",
    "@semantic-release/changelog",
    [
      "@semantic-release/exec",
      {
        // Increment versionCode, update versionName, write changelog entry, and build signed APK
        prepareCmd: `
        sed -i -E "s/versionCode = [0-9]+/versionCode = $(( $(grep -oP 'versionCode = \\K[0-9]+' app/build.gradle.kts) + 1 ))/" app/build.gradle.kts
        sed -i -E 's/versionName = .*/versionName = "'"'"${nextRelease.version}"'"'"/' app/build.gradle.kts
        mkdir -p metadata/en-US/changelogs
        echo "\${nextRelease.notes}" > metadata/en-US/changelogs/$(grep -oP 'versionCode = \\K[0-9]+' app/build.gradle.kts).txt
        ./gradlew :app:assembleRelease \
          -Pandroid.injected.signing.store.file="${process.env.KEYSTORE_FILE}" \
          -Pandroid.injected.signing.store.password="${process.env.KEYSTORE_STORE_PASSWORD}" \
          -Pandroid.injected.signing.key.alias="${process.env.KEYSTORE_KEY_ALIAS}" \
          -Pandroid.injected.signing.key.password="${process.env.KEYSTORE_KEY_PASSWORD}"
      `.trim(),
      },
    ],
    [
      "@semantic-release/git",
      {
        assets: [
          "CHANGELOG.md",
          "app/build.gradle.kts",
          "metadata/en-US/changelogs/*.txt",
        ],
      },
    ],
    [
      "@semantic-release/github",
      {
        assets: [
          { path: "app/build/outputs/apk/release/*.apk", label: "Android APK" },
        ],
      },
    ],
  ],
};
