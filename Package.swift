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
            url: "https://maven.pkg.github.com/dariuszszlag/KBCore/com/darek/kbcore-multi/0.3.1/kbcore-multi-0.3.1.zip",
            checksum: "735913110194f9df765b5472037881b702fed3e122d43ed160442840045ef12a"
        ),
    ]
)
