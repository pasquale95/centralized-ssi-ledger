# SSI Centralized Ledger

This is an implementation of a basic SSI Centralized Ledger. It can be run with `aries-cloudagent-python` and it has been tested and used with the `indy` wallet.

## Table of contents
- [Dependencies](#dependencies)
- [How to build](#how-to-build)
- [How to build libindy](#how-to-build-libindy)
  - [macOS troubleshooting](#macos-troubleshooting)
- [How to run](#how-to-run)
  - [macOS troubleshooting](#macos-troubleshooting)

## Dependencies

All the project dependencies are automatically handled with `maven` except for the `libindy` native library, which you must compile on your machine in order to let this ledger run correctly. 

If you have not compiled this library on your machine, please refer to the [**How to compile libindy**](#how-to-compile-libindy) section.

## How to build

To build this project, run from the root of this project the command:

```bash
$ mvn clean install
```

The .jar is built under the `./target` folder.

## How to build libindy

To compile `libindy` you must clone the [indy-sdk](https://github.com/hyperledger/indy-sdk) repository on your machine. Once you have cloned the repository, install the sdk referring to the [How to build Indy SDK from source](https://github.com/hyperledger/indy-sdk/blob/master/README.md#how-to-build-indy-sdk-from-source) section.

### macOS troubleshooting

If you get an error regarding the **OpenSSL** package when you build the library with `cargo build`, this may be related to the wrong definition of the `OPENSSL_DIR` environment variable. At the time of writing, `libindy` is compatible with `openssl@1.1`, but not with `openssl@3`. Make sure that `OPENSSL_DIR` points to the former version:

```bash
$ export OPENSSL_DIR=/usr/local/Cellar/openssl@1.1/1.1.1o
```

## How to run

To start the centralized ledger, run the command:

```bash
$ DYLD_LIBRARY_PATH=<indy-sdk-folder>/libindy/target/debug java -jar centralizedledger-0.0.1-SNAPSHOT.jar
```

where `<indy-sdk-folder>` must be replaced with the path to the location where you cloned the `indy-sdk` repository.

### macOS troubleshooting

On macOS you can have an issue passing the env variable `DYLD_LIBRARY_PATH` to the java binary if the *System Integrity Protection* (aka SIP) is enabled. This feature prevents users from changing a bunch of environment variables for all the binaries located in specific locations, one of which is `/usr/bin` where the `java` binary could be located.

To check the location of your java binary run the command:

```bash
$ which java
```

To check if SIP is enabled on your machine, run:

```bash
$ csrutil status
```

If you have SIP enabled and your java binary is located under `/usr/bin`, you cannot change `DYLD_LIBRARY_PATH`, resulting in an error at the ledger startup since it would not be able to find the `libindy.dylib` library. To solve this problem you either need to disable SIP or use a java binary located outside `/usr/bin`.
