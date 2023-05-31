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
            url: "https://maven.pkg.github.com/dariuszszlag/KBCore/com/darek/kbcore-multi/0.2.5/kbcore-multi-0.2.5.zip",
            checksum: "0180c4d8c50686e603cea3a9d5689cfae7fdac086c633fe82efb74e50b17111a"
        ),
    ]
)
