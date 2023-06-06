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
            url: "https://maven.pkg.github.com/dariuszszlag/KBCore/com/darek/kbcore-multi/0.3.3/kbcore-multi-0.3.3.zip",
            checksum: "9fdf085a3b949db2704b071a1dd450197472e69c6bd78cb7e1509c84c386ee2b"
        ),
    ]
)
