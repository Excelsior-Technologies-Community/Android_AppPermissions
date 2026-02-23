## App PermissionManager
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green)](LICENSE)
[![API](https://img.shields.io/badge/API-24%2B-orange)](#)

A **modern, lightweight, and lifecycle-safe Android permission library** built with Kotlin.

Simplify runtime permission handling using a clean **DSL-style API** with full support for **Activity Result API**, **Activity & Fragment**, and smart permission handling.

---

### Features

*  Modern **Activity Result API** (No deprecated methods)
*  **No `onRequestPermissionsResult` required**
*  Clean and readable **DSL API**
*  Supports **Activity & Fragment**
*  Handles:
    * ✔ Granted
    * ✔ Denied
    * ✔ Permanently Denied
*  Lifecycle-safe (no crashes)
*  Supports multiple permissions
*  Optional rationale callback
*  Lightweight & easy to integrate

---

## 📦 Installation

### Step 1: Add JitPack (if publishing)

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add Dependency

```gradle
implementation 'com.github.yourusername:PermissionManager:1.0.0'
```

---

## Usage

### Basic Usage (Activity)

```kotlin
PermissionManager.with(this)
    .permissions(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_MEDIA_IMAGES
    )
    .onGranted {
        // All permissions granted
    }
    .onDenied { deniedList ->
        // Handle denied permissions
    }
    .onPermanentlyDenied { permanentlyDeniedList ->
        // Handle permanently denied permissions
    }
    .request()
```

---

### Usage in Fragment

```kotlin
PermissionManager.with(this)
    .permissions(Manifest.permission.CAMERA)
    .onGranted {
        // Success
    }
    .request()
```

---

### Callbacks

| Callback              | Description                        |
| --------------------- | ---------------------------------- |
| `onGranted`           | All permissions granted            |
| `onDenied`            | Some permissions denied            |
| `onPermanentlyDenied` | User selected "Don't ask again"    |
| `onRationaleNeeded`   | Show explanation before requesting |

---

### Rationale Example

```kotlin
PermissionManager.with(this)
    .permissions(Manifest.permission.CAMERA)
    .onRationaleNeeded { permissions ->
        // Show custom dialog before requesting permission
    }
    .request()
```

---

### Smart Behavior

* Automatically skips already granted permissions
* Requests only required permissions
* Separates denied vs permanently denied

---

### Supported Permissions

You can request **any Android runtime permission**, for example:

* Camera → `Manifest.permission.CAMERA`
* Storage → `Manifest.permission.READ_MEDIA_IMAGES`
* Location → `Manifest.permission.ACCESS_FINE_LOCATION`
* Microphone → `Manifest.permission.RECORD_AUDIO`
* Notifications → `Manifest.permission.POST_NOTIFICATIONS`

---

### Best Practices

* Request permissions **only when needed**
* Avoid requesting too many permissions at once
* Provide rationale before sensitive permissions

---

### Example

```kotlin
PermissionManager.with(this)
    .permissions(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )
    .onGranted {
        Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
    }
    .onDenied {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
    }
    .request()
```

---

### License

```
MIT License

Copyright (c) 2025 Excelsior Technologies 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
---

## ⭐ Support

If you like this library, please ⭐ the repository and share it with others!
