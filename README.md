# SSI Centralized Ledger

This is an implementation of a basic SSI Centralized Ledger. It can be run with `aries-cloudagent-python` and it has been tested and used with the `indy` wallet.

## Table of contents
- [Dependencies](#dependencies)
- [How to build libindy](#how-to-build-libindy)
- [How to build](#how-to-build)
  - [Build on Linux](#build-on-linux)
  - [Build on macOS](#build-on-macos)
- [How to run](#how-to-run)
  - [Run on Linux](#run-on-linux)
  - [Run on macOS](#run-on-macos)
- [macOS troubleshooting](#macos-troubleshooting)
  - [OpenSSL error](#openssl-error)
  - [libindy not found](#libindy-not-found)

## Dependencies

All the project dependencies are automatically handled with `maven` except for the `libindy` native library, which you must compile on your machine in order to let this ledger run correctly. 

If you have not compiled this library on your machine, please refer to the [**How to build libindy**](#how-to-build-libindy) section.

## How to build libindy

As first step, we need to compile the `libindy` library. To do it, you must clone the [indy-sdk](https://github.com/hyperledger/indy-sdk) repository on your machine. Once you have cloned the repository, install the sdk referring to the [How to build Indy SDK from source](https://github.com/hyperledger/indy-sdk/blob/master/README.md#how-to-build-indy-sdk-from-source) section.

In the next sections we refer the location where you have cloned the indy-sdk repository as `<indy-sdk-folder>`.

## How to build

### Build on linux

To build this project, run from the root of this project the command:

```bash
$ LD_LIBRARY_PATH=<indy-sdk-folder>/libindy/target/debug mvn clean install
```

### Build on macOS

To build this project, run from the root of this project the command:

```bash
$ DYLD_LIBRARY_PATH=<indy-sdk-folder>/libindy/target/debug mvn clean install
```

## How to run

If the build was successful, you should have a .jar archive built under the `./target` folder.

### Run on linux

To start the centralized ledger, run the command:

```bash
$ LD_LIBRARY_PATH=<indy-sdk-folder>/libindy/target/debug java -jar centralizedledger-0.0.1-SNAPSHOT.jar
```

### Run on macOS

To start the centralized ledger, run the command:

```bash
$ DYLD_LIBRARY_PATH=<indy-sdk-folder>/libindy/target/debug java -jar centralizedledger-0.0.1-SNAPSHOT.jar
```

## macOS troubleshooting

Here a quick guide to solve some errors you could encounter when following the guide above.

### OpenSSL error

If you get an error regarding the **OpenSSL** package when you build the library `libindy` with the `cargo build` command, this may be related to the wrong definition of the `OPENSSL_DIR` environment variable. At the time of writing, `libindy` is compatible with `openssl@1.1`, but not with `openssl@3`. Make sure that `OPENSSL_DIR` points to the former version:

```bash
$ export OPENSSL_DIR=/usr/local/Cellar/openssl@1.1/1.1.1o
```

### libindy not found

When we build and run this ledger, we must be able to manipulate the `DYLD_LIBRARY_PATH` environment variable on macOS in order to allow java to find and use the libindy library. On recent macOS versions the manipulation of `DYLD_LIBRARY_PATH` could be prevented by the *System Integrity Protection* (aka SIP) feature, which prevents users from changing a bunch of environment variables for all the binaries located in some specific filesystem locations (e.g. all the binaries under the folder `/usr`). If SIP is enabled on your machine and your `mvn` and `java` binaries are located under one of the protected folders, you cannot change the value of `DYLD_LIBRARY_PATH`.

To check if SIP is enabled on your machine, run:

```bash
$ csrutil status
```

To check the location of your java binary run the command:

```bash
$ which java
```

If SIP is enabled on your machine and your `mvn` and `java` binaries are located under one of the protected folders, you cannot change the value of `DYLD_LIBRARY_PATH`. To solve this problem you either need to disable SIP on your macOS (see [here](https://developer.apple.com/documentation/security/disabling_and_enabling_system_integrity_protection)) or use `java` and `mvn` binaries located outside the `/usr` folder.
