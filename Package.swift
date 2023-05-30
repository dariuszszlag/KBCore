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
            url: "https://maven.pkg.github.com/dariuszszlag/KBCore/com/darek/kbcore-multi/0.2.2/kbcore-multi-0.2.2.zip",
            checksum: "31c41b75cf16981d1bcb9f616719583fe8611acac5bac0a0dfa17a940a136a32"
        ),
    ]
)
