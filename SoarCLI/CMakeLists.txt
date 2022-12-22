
include_directories(.)
include_directories("../IDE\ Projects/include")

file(GLOB SRC CONFIGURE_DEPENDS 
    soar_cli.cpp
)
message(STATUS ${SRC})

add_executable(soar-cli ${SRC} )
target_link_libraries(soar-cli Soar pthread dl)
