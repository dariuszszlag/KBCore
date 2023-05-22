// swift-tools-version:5.7
import PackageDescription

let package = Package(
    name: "kbcore",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(
            name: "kbcore",
            targets: ["kbcore"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "kbcore",
            url: "https://maven.pkg.github.com/dariuszszlag/KBCore/kbcore-ios.zip",
            checksum: "bfd133b88685045104207e524fe6321a6d6a2e73327b5c3eaaef9a93da0ea150"
        ),
    ]
)
