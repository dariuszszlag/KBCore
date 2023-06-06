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
            url: "https://maven.pkg.github.com/dariuszszlag/KBCore/com/darek/kbcore-multi/0.3.5/kbcore-multi-0.3.5.zip",
            checksum: "b5b8e7ba35eb83476ea401a22407cbdde94f5175a7f5ed25b44ade29517e1763"
        ),
    ]
)
