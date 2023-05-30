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
            url: "https://maven.pkg.github.com/dariuszszlag/KBCore/com/darek/kbcore-multi/0.2.4/kbcore-multi-0.2.4.zip",
            checksum: "cb9288c896d01324b454876a3c2a02e314a06009deac418eccc08677799c1f15"
        ),
    ]
)
