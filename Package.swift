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
            url: "https://maven.pkg.github.com/dariuszszlag/KBCore/com/darek/kbcore-multi/0.1.5/kbcore-multi-0.1.5.zip",
            checksum: "c7366c4b1c44ebde9e513e026f6558b7e016327d745ab4d896b348a6cee41917"
        ),
    ]
)
