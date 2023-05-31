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
            url: "https://maven.pkg.github.com/dariuszszlag/KBCore/com/darek/kbcore-multi/0.3.0/kbcore-multi-0.3.0.zip",
            checksum: "5987fc027b2b6ff349388cd03bfd6a2709400877ceb8fb33a5b84b676ae6c8ed"
        ),
    ]
)
