# Google Play Developer API - AUTO APK UPLOAD - TeamCity
Java Software that deals with the Google Play Developer API via Google API Client Library **_`JAVA`_**.

#### GOALS

- Quick **`Authentication`** via google authentication library - Oauth 2.0 or ClientLogin: requirements ?
(p12 key, client sercret json, SERVICE_ACCOUNT_EMAIL)
- **`Batching`** batching multiple HTTP req/res
- **`APK UPLOADS`** upload automatically a new apk to the PlayStore (Ideally via Build Servers like **`TeamCity`** 
from JetBrains)

- **`others`**: updating listing... and list uploaded apks of a specific app through a unique packagename

## Installation

1. Download this android-play-publisher-api into your project files (@ROOT): 
https://github.com/dff-solutions/android-play-publisher-api

## Getting started

_**Note:**_ To use the Google Play Developer API you need to create or reuse an existing API project in the
            Google Api console, https://console.developers.google.com/. You can either use the API with a client
            ID for Native Application (Installed Application) or create a Service Account.

1. create a new **`json`** file with the name : **`android-play-publisher.json`** | try   ``` $anahas: touch android-play-publisher.json```

2. **`android-play-publisher.json`** should provide the following keys. Otherwise the code will be broken

+ `SRC_RESOURCES_KEY_P12`: Path to the private key file (only used for Service Account auth) i.e. `_C:\\..\\..\\key.p12_`
+ `APPLICATION_NAME`: Specify the name of your application. If the application name is., i,e. _ArriView LIGHT_
+ `PACKAGE_NAME`: Specify the package name of the app, i.e. ``_`de.dff.arriviewlight`_``
+ `SERVICE_ACCOUNT_EMAIL`: Used for Authentication purposes, i.e. `_username@api-xyz-abc.iam.gserviceaccount.com_`
+ `APK_FILE_PATH`: Specify the apk file path of the apk to upload, i.e. `_C:\\..\\..\\your_apk.apk_`


Here is an example of android-play-publisher.json

```json
{
    "SRC_RESOURCES_KEY_P12" : "C:\\..\\..\\key.p12",
    "APPLICATION_NAME"  : "Your Application Name",
    "PACKAGE_NAME" : "com.my.package",
    "SERVICE_ACCOUNT_EMAIL" : "username@api-xyz-abc.iam.gserviceaccount.com",
    "APK_FILE_PATH" : "C:\\..\\..\\myapp.apk"
}
```

> 
ENJOY :-)

