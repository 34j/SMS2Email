# SMS2Email

An Android application that forwards incoming SMS messages to a specified email address.

This app is useful for users who need to access their SMS messages remotely.

## Features

- **SMS to Email Forwarding**: Automatically forwards received SMS to your email via SMTP
- **SMTP Configuration**: Support for various SMTP servers with multiple encryption modes (STARTTLS, SMTPS, None)
- **PGP Encryption**: Optional end-to-end encryption using OpenKeychain app for secure message transmission
- **Background Operation**: Works even when the app is closed

## PGP Encryption Support

SMS2Email now supports PGP encryption via the [OpenKeychain](https://github.com/open-keychain/open-keychain) app. This feature provides end-to-end encryption for your SMS messages:

- Enable PGP encryption in the app settings
- Select recipient public key(s) from OpenKeychain
- SMS messages are encrypted before being sent via email
- Email subject remains unencrypted (contains sender number)
- Email body contains the PGP-encrypted message

**Requirements:**
- OpenKeychain app must be installed (available on F-Droid)
- At least one public key must be available in OpenKeychain

<p align="center"><a href="https://f-droid.org/packages/io.github.sms2email.sms2email"><img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png" alt="Get it on F-Droid"  width="207" /></a>
<a href="https://apt.izzysoft.de/fdroid/index/apk/io.github.sms2email.sms2email"><img src="https://raw.githubusercontent.com/InfinityLoop1308/PipePipe/refs/tags/v5.0.0-beta4/assets/IzzyOnDroid.png" alt="Get it on IzzyOnDroid" width="207" /></a></p>

| 🌙                                                                        | ☀️                                                                         |
| ------------------------------------------------------------------------- | -------------------------------------------------------------------------- |
| ![Screenshot](metadata/en-US/images/phoneScreenshots/screenshot_dark.png) | ![Screenshot](metadata/en-US/images/phoneScreenshots/screenshot_light.png) |

![IzzyOnDroid Badge](https://img.shields.io/endpoint?url=https://apt.izzysoft.de/fdroid/api/v1/shield/io.github.sms2email.sms2email&label=IzzyOnDroid&style=flat-square)
[![IzzyOnDroid Yearly Downloads](https://img.shields.io/badge/dynamic/json?url=https://dlstats.izzyondroid.org/iod-stats-collector/stats/basic/yearly/rolling.json&query=$.['io.github.sms2email.sms2email']&label=IzzyOnDroid%20yearly%20downloads&style=flat-square)](https://apt.izzysoft.de/packages/io.github.sms2email.sms2email)
[![RB Status](https://shields.rbtlog.dev/simple/io.github.sms2email.sms2email?style=flat-square)](https://shields.rbtlog.dev/io.github.sms2email.sms2email)
