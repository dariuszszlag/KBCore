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
            url: "https://maven.pkg.github.com/dariuszszlag/KBCore/com/darek/kbcore-multi/0.1.2/kbcore-multi-0.1.2.zip",
            checksum: "fc762e68eebeffd8aac2dfb8ff93e8a9ee02754e445f5c6082c078bb3c6a2ad4"
        ),
    ]
)
